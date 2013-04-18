package padsof.gui.views;

import java.awt.event.ActionListener;

import javax.swing.*;

import padsof.gui.NavigationService;

public abstract class View extends JPanel
{
	private String title;
	private NavigationService navigator;
	
	/**
	 * @return the title
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1084405314738335773L;
	
	public View(String title)
	{
		super();
		this.title = title;
	}
	
	public abstract void setController(ActionListener c);
	
	protected void navigate(Class<? extends View> to)
	{
		navigator.navigate(to);
	}
	
	public void setNavigator(NavigationService navigator)
	{
		this.navigator = navigator;
	}
}
