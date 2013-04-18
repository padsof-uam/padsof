package padsof.gui;

import java.awt.*;
import java.rmi.NoSuchObjectException;

import javax.swing.*;

import padsof.gui.controllers.*;
import padsof.gui.views.*;
import padsof.system.Vendor;

public class Application implements NavigationService
{
	private static Application instance = null;
	
	private JFrame frame;
	private JPanel topPanel;
	private View currentView;
	
	private Vendor vendor;
	
	private boolean started = false;
	
	/**
	 * @param vendor the vendor to set
	 */
	public void setVendor(Vendor vendor)
	{
		this.vendor = vendor;
	}
	
	private Application()
	{
		frame = new JFrame();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		registerControllers();
	}
	
	private void registerControllers()
	{
		ControllerLocator.registerController(LoginView.class, LoginController.class);
	}
	
	public void setDefaultButton(JButton button)
	{
		frame.getRootPane().setDefaultButton(button);
	}

	public void start() throws NoSuchObjectException
	{
		if(started)
			return;
		
		showLoginDialog();
		frame.setVisible(true);

		started = true;
	}

	private void showLoginDialog() 
	{
		currentView = new LoginView();
		setControllerForView(currentView);
		frame.setContentPane(currentView);
		center();
	}
	
	private void showUserInterface()
	{
		JPanel container = new JPanel();
		
		topPanel = new JPanel();
		topPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)), 
				BorderFactory.createEmptyBorder(8, 8, 8, 8)));
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
		
		topPanel.add(Box.createHorizontalGlue());
		topPanel.add(new JLabel("Vendedor: " + vendor.getName()));
		
		
		
		container.setLayout(new BorderLayout());
		container.add(topPanel, BorderLayout.NORTH);
		container.add(currentView, BorderLayout.CENTER);
		container.add(Box.createVerticalStrut(10), BorderLayout.SOUTH);
		
		frame.setContentPane(container);
	}

	public static Application getInstance()
	{
		if(instance == null)
			instance = new Application();
		
		return instance;
	}

	@Override
	public <V extends View> void navigate(Class<V> to)
	{
		setView(to);
	}
	
	private boolean hasComponent(Container container, Component component)
	{
		for(Component c : container.getComponents())
			if(c == component)
				return true;
		
		return false;
	}
	
	private void replaceMainView(View view)
	{
		if(currentView != null && hasComponent(frame.getContentPane(), currentView))
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
		else if(LoginView.class.isInstance(currentView))
			showUserInterface();
		
		V newView;
		
		try
		{
			newView = view.newInstance();
		}
		catch(Exception e)
		{
			throw new RuntimeException("Can't instantiate view of type " + view.getName() + ": " + e.toString());
		}
		
		replaceMainView(newView);	
		setControllerForView(newView);
		frame.pack();
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
		Controller<V> controller = ControllerLocator.getController((Class<V>) view.getClass());
		
		if(controller == null)
			return;
		
		view.setController(controller);
		controller.setNavigator(this);
		controller.setView(view);
	}	
}
