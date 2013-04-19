package padsof.gui;

import java.awt.event.*;

import javax.swing.JButton;

import padsof.gui.views.View;

public class NavigateButton extends JButton implements ActionListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7792338481799971653L;

	private Class<? extends View> navigationView;
	
	public NavigateButton(String title, Class<? extends View> destination)
	{
		super(title);
		setNavigationView(destination);
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Application.getInstance().navigate(navigationView);
	}

	public Class<? extends View> getNavigationView()
	{
		return navigationView;
	}

	public void setNavigationView(Class<? extends View> navigationView)
	{
		this.navigationView = navigationView;
	}

}
