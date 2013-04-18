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
	private JLabel vendorLabel;
	
	private boolean started = false;
	private boolean loggedIn = false;
	
	private Application()
	{
		frame = new JFrame();
		
		registerControllers();
	}
	
	private void registerControllers()
	{
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
		currentView.setController(null);
		frame.setContentPane(currentView);
	}
	
	private void showUserInterface()
	{
		topPanel = new JPanel();
		topPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
		topPanel.add(Box.createHorizontalGlue());
		vendorLabel = new JLabel("Vendedor: " + vendor.getName());
		topPanel.add(vendorLabel);
		
		Container container = frame.getContentPane();
		container.setLayout(new BorderLayout());
		
		container.add(topPanel, BorderLayout.NORTH);
		container.add(currentView, BorderLayout.CENTER);
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
		if(to.isAssignableFrom(LoginView.class))
			loggedIn = false;
		else
			loggedIn = true; // De momento no hay más páginas que se puedan acceder sin login así que esto vale.
		
		setView(to);
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
		
		if(currentView != null)
			frame.getContentPane().remove(currentView);
		
		try
		{
			currentView = view.newInstance();
			setControllerForView(currentView);
		}
		catch(Exception e)
		{
			throw new RuntimeException("Can't instantiate view of type " + view.getName() + ": " + e.toString());
		}
	}

	private <V extends View> void setControllerForView(V view)
	{
		Controller<V> controller = ControllerLocator.getController(view);
		view.setController(controller);
		controller.setView(view);
	}	
}
