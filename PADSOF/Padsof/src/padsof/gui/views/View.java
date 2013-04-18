package padsof.gui.views;

import java.awt.event.ActionListener;

import javax.swing.*;

public abstract class View extends JPanel
{
	private String title;
	
	
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
		this.title= title;
	}
	
	public abstract void setController(ActionListener c);
}
