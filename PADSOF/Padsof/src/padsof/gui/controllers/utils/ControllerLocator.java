package padsof.gui.controllers.utils;

import java.util.*;

import padsof.gui.controllers.Controller;
import padsof.gui.views.View;

/**
 * ControllerLocator.
 * @author Víctor de Juan Sanz - Guillermo Julián Moreno
 *
 */
public class ControllerLocator
{
	private static Map<Class<? extends View>, Class<? extends Controller<?>>> controllers = new HashMap<Class<? extends View>, Class<? extends Controller<?>>>();
	
	/**
	 * Register a controller with its corresponding view.
	 * @param view View.
	 * @param controller Controller.
	 */
	public static <V extends View> void registerController(Class<V> view, Class<? extends Controller<V>> controller)
	{
		controllers.put(view, controller);
	}
	
	/**
	 * Gets and instatiates the controller for the corresponding view.
	 * @param view View.
	 * @return Controller or null if it's found.
	 */
	@SuppressWarnings("unchecked")
	public static <V extends View> Controller<V> getController(Class<? extends V> view)
	{
		Class<? extends Controller<V>> controllerClass =  (Class<? extends Controller<V>>) controllers.get(view);
		
		if(controllerClass == null)
			return null;
		
		try
		{
			return controllerClass.newInstance();
		}
		catch (Exception e)
		{
			throw new RuntimeException("Couldn't instantiate controller " + controllerClass.getName());
		}
	}
}
