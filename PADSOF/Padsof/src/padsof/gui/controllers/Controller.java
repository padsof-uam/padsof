package padsof.gui.controllers;

import java.awt.event.*;
import java.lang.reflect.*;

import padsof.gui.NavigationService;
import padsof.gui.views.View;

public class Controller<V extends View> implements ActionListener
{
	protected V view;
	protected NavigationService navigator;
	
	/**
	 * @param navigator the navigator to set
	 */
	public void setNavigator(NavigationService navigator)
	{
		this.navigator = navigator;
	}

	public void setView(V view)
	{
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Method listener = findListenerFor(e.getActionCommand());

		try
		{
			if (listener != null)
				listener.invoke(this, e);
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
	}

	private Method findListenerFor(String command)
	{
		Method[] methods = this.getClass().getMethods();

		for (Method method : methods)
		{
			if (method.isAnnotationPresent(Listener.class))
			{
				Listener annotation = method.getAnnotation(Listener.class);
				if (command.equals(annotation.command()) || annotation.command().equals("__SINGLE__"))
					return method;
			}
		}

		return null;
	}
}