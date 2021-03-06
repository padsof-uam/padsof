package padsof.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import padsof.gui.controllers.*;
import padsof.gui.controllers.utils.ControllerLocator;
import padsof.gui.views.*;
import padsof.system.*;

/**
 * Application class. Singleton.
 * 
 * @author Víctor de Juan Sanz - Guillermo Julián Moreno
 */
public class Application implements NavigationService
{
	private static Application instance = null;

	private JFrame frame;
	private JPanel topPanel;
	private View currentView;
	private Packet packet;
	private Client cliente;


	private boolean started = false;
	private Stack<Class<? extends View>> navigationStack;

	private Vendor vendor;

	private JButton backButton;

	public void setVendor(Vendor vendor)
	{
		this.vendor = vendor;
	}

	private Application()
	{
		navigationStack = new Stack<Class<? extends View>>();
		frame = new JFrame("PADSOF");

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(750, 450);
		frame.setMinimumSize(new Dimension(750, 450));
		frame.setResizable(false);
		
		registerControllers();
	}

	private void registerControllers()
	{
		ControllerLocator.registerController(FindImsersoTravelView.class, FindImsersoTravelController.class);
		ControllerLocator.registerController(FindFlightView.class, FindFlightController.class);
		ControllerLocator.registerController(FindTravelView.class, FindTravelController.class);
		ControllerLocator.registerController(FindHotelView.class, FindHotelController.class);
		ControllerLocator.registerController(SelectClientView.class, SelectClientController.class);
		ControllerLocator.registerController(LoginView.class,
				LoginController.class);
		ControllerLocator.registerController(AdminView.class,
				AdminController.class);
		ControllerLocator.registerController(StatsView.class,
				StatsController.class);
		ControllerLocator.registerController(SelectPacketView.class,
				SelectPacketController.class);
		ControllerLocator.registerController(RegisterClientView.class,
				RegisterClientController.class);
		ControllerLocator.registerController(BookingView.class, BookingController.class);
	}

	public JFrame getFrame()
	{
		return frame;
	}
	
	/**
	 * Sets the default button for the interface.
	 * 
	 * @param button
	 */
	public void setDefaultButton(JButton button)
	{
		frame.getRootPane().setDefaultButton(button);
	}

	/**
	 * Starts the application if it's not started.
	 */
	public void start()
	{
		if (started)
			return;

		showLoginDialog();
		frame.setVisible(true);
		center();

		started = true;
	}

	private void showLoginDialog()
	{
		currentView = new LoginView();
		setControllerForView(currentView);
		frame.setContentPane(currentView);
		resetTitle();
		center();
	}

	private void showUserInterface()
	{
		JPanel container = new JPanel();

		topPanel = new JPanel();
		topPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)),
				BorderFactory.createEmptyBorder(8, 8, 8, 8)));
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

		backButton = new JButton("Atrás");
		backButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				goBack();
			}
		});

		topPanel.add(backButton);
		topPanel.add(Box.createHorizontalGlue());
		topPanel.add(new JLabel("Vendedor: " + vendor.getName()));

		container.setLayout(new BorderLayout());
		container.add(topPanel, BorderLayout.NORTH);
		container.add(currentView, BorderLayout.CENTER);
		container.add(Box.createVerticalStrut(10), BorderLayout.SOUTH);

		frame.setContentPane(container);
	}

	/**
	 * Get the current instance of the Application of creates one if there isn't
	 * any.
	 * 
	 * @return Application instance.
	 */
	public static Application getInstance()
	{
		if (instance == null)
			instance = new Application();

		return instance;
	}

	private boolean hasComponent(Container container, Component component)
	{
		for (Component c : container.getComponents())
			if (c == component)
				return true;

		return false;
	}
	
	public void resetTitle()
	{
		frame.setTitle("PADSOF - " + currentView.getTitle());
	}

	private void replaceMainView(View view)
	{
		if (currentView != null
				&& hasComponent(frame.getContentPane(), currentView))
			frame.getContentPane().remove(currentView);

		frame.getContentPane().add(view, BorderLayout.CENTER);
		currentView = view;
	}

	private <V extends View> void setView(Class<V> view)
	{
		if (view.isAssignableFrom(LoginView.class))
		{
			showLoginDialog();
			return;
		}
		else if (LoginView.class.isInstance(currentView))
			showUserInterface();

		V newView;

		try
		{
			newView = view.newInstance();
		}
		catch (Exception e)
		{
			throw new RuntimeException("Can't instantiate view of type "
					+ view.getName() + ": " + e.toString());
		}

		replaceMainView(newView);
		setControllerForView(newView);
		resetTitle();
		frame.revalidate();
	}

	private void center()
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		frame.pack();

		int x = (int) (screenSize.getWidth() - frame.getWidth()) / 2;
		int y = (int) (screenSize.getHeight() - frame.getHeight()) / 2;

		frame.setLocation(x, y);
	}

	@SuppressWarnings("unchecked")
	private <V extends View> void setControllerForView(V view)
	{
		Controller<V> controller = ControllerLocator
				.getController((Class<V>) view.getClass());

		if (controller == null)
		{
			System.out.println("Warning: no controller found for view " + view.getClass().getSimpleName());
			return;
		}

		view.setController(controller);
		controller.setNavigator(this);
		controller.setView(view);
	}

	/**
	 * Navigate to a given page.
	 */
	@Override
	public <V extends View> void navigate(Class<V> to)
	{
		navigationStack.push(currentView.getClass());
		setView(to);
	}

	@Override
	public void goBack()
	{
		if (navigationStack.isEmpty())
			return;

		setView(navigationStack.pop());
	}

	public Client getClient()
	{
		return cliente;
	}

	public void setClient(Client cliente)
	{
		this.cliente = cliente;
	}

	public Vendor getVendor(){
		return this.vendor;
	}
	
	public Packet getPacket()
	{
		return packet;
	}
	
	public void setPacket(Packet actualPacket)
	{
		this.packet = actualPacket;
	}

}
