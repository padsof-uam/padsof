package padsof.gui.controllers;

import java.awt.event.*;
import java.lang.reflect.*;

import padsof.gui.views.View;

public class Controller<V extends View> implements ActionListener
{
	protected V view;

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
				if (command.equals(annotation.command()))
					return method;
			}
		}

		return null;
	}
}
