package padsof.gui;

import java.rmi.NoSuchObjectException;

import javax.swing.*;

import padsof.gui.views.*;

public class Application implements NavigationService
{
	private static Application instance = null;
	
	private JFrame frame;
	private JPanel topPanel;
	private View currentView;
	
	private boolean started = false;
	private boolean loggedIn = false;
	
	private Application()
	{
		frame = new JFrame();
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
		
	}

	public static Application getInstance()
	{
		if(instance == null)
			instance = new Application();
		
		return instance;
	}

	@Override
	public void navigate(Class<? extends View> to)
	{
		if(to.isAssignableFrom(LoginView.class))
			loggedIn = false;
		else
			loggedIn = true; // De momento no hay más páginas que se puedan acceder sin login así que esto vale.
		
		if(loggedIn)
			replaceView(to);
		else
			showLoginDialog();
		
		
	}

	private void replaceView(Class<? extends View> to)
	{
		// TODO Auto-generated method stub
		
	}	
}
