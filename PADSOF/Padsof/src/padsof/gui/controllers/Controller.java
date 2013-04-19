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
			{
				if(methodReceivesArgs(listener))
					listener.invoke(this, e);
				else
					listener.invoke(this);
			}
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
	}

	private boolean methodReceivesArgs(Method listener)
	{
		return listener.getParameterTypes().length > 0;
	}

	private Method findListenerFor(String command)
	{
		Method[] methods = this.getClass().getMethods();

		for (Method method : methods)
		{
			if (method.isAnnotationPresent(Listener.class))
			{
				if(!hasListenerSignature(method))
				{
					System.out.println("Warning: method " + method.getName() + " doesn't have listener signature.");
					continue;
				}
				
				Listener annotation = method.getAnnotation(Listener.class);
				if (command.equals(annotation.value()) || annotation.value().equals("__SINGLE__"))
					return method;
			}
		}

		return null;
	}

	private boolean hasListenerSignature(Method method)
	{
		Class<?>[] params = method.getParameterTypes();
		
		return params.length == 0 || (params.length == 1 && params[0].isAssignableFrom(ActionEvent.class));
	}
}
