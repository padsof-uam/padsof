package padsof.gui.controllers;

import java.awt.event.*;
import java.lang.reflect.*;

import javax.swing.JOptionPane;
import javax.swing.event.*;

import padsof.gui.NavigationService;
import padsof.gui.controllers.utils.Listener;
import padsof.gui.views.View;

/**
 * Controller base class.
 * This class allows to set up easily listeners to events. Just annotate the
 * method with Listener and, optionally, the command to which that method
 * responds to. Don't forget to set the action command in the performer.
 * For example, to listen to a button press, set this method on the controller:
 * 
 * <pre>
 * <code>
 * {@literal @}Listener("method")
 * public void listener(ActionEvent e)
 * {
 * 		// ...
 * }
 * </code>
 * </pre>
 * 
 * and this on the View, setting the button:
 * 
 * <pre>
 * {@code
 * button.setActionCommand("method");
 * button.addActionListener(controller);
 * }
 * </pre>
 * 
 * The ActionEvent argument is optional on the listener, you don't have to
 * include it.
 * 
 * @author Víctor de Juan Sanz - Guillermo Julián Moreno
 * @param <V>
 *            View controlled by the controller.
 */
public abstract class Controller<V extends View> implements ActionListener,
		DocumentListener
{
	protected V view;
	protected NavigationService navigator;

	/**
	 * @param navigator
	 *            the navigation service.
	 */
	public void setNavigator(NavigationService navigator)
	{
		this.navigator = navigator;
	}

	/**
	 * Link the view to the controller.
	 * 
	 * @param view
	 */
	public void setView(V view)
	{
		this.view = view;
	}

	/**
	 * Listens to action performed and calls the corresponding listener method.
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Method listener = findListenerFor(e.getActionCommand());

		try
		{
			if (listener != null)
			{
				if (methodReceivesArgs(listener))
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
				if (!hasListenerSignature(method))
				{
					System.out.println("Warning: method " + method.getName()
							+ " doesn't have listener signature.");
					continue;
				}

				Listener annotation = method.getAnnotation(Listener.class);
				if (command.equals(annotation.value())
						|| annotation.value().equals("__SINGLE__"))
					return method;
			}
		}

		return null;
	}

	private boolean hasListenerSignature(Method method)
	{
		Class<?>[] params = method.getParameterTypes();

		return params.length == 0
				|| (params.length == 1 && params[0]
						.isAssignableFrom(ActionEvent.class));
	}

	protected void showError(String message)
	{
		JOptionPane.showMessageDialog(view, message, "Error",
				JOptionPane.ERROR_MESSAGE);
	}

	protected void showWarning(String message)
	{
		JOptionPane.showMessageDialog(view, message, "Advertencia",
				JOptionPane.WARNING_MESSAGE);
	}

	protected void showMessage(String message)
	{
		JOptionPane.showMessageDialog(view, message, "Mensaje",
				JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public void insertUpdate(DocumentEvent e)
	{
	}

	@Override
	public void removeUpdate(DocumentEvent e)
	{
	}

	@Override
	public void changedUpdate(DocumentEvent e)
	{
		Method listener = findListenerFor("DocChange");

		try
		{
			if (listener != null)
				listener.invoke(this);

		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
	}
}
