package padsof.gui.controllers;

import java.util.*;

import padsof.gui.views.View;

public class ControllerLocator
{
	private static Map<Class<? extends View>, Class<? extends Controller<?>>> controllers = new HashMap<Class<? extends View>, Class<? extends Controller<?>>>();
	
	public static <V extends View> void registerController(Class<V> view, Class<? extends Controller<V>> controller)
	{
		controllers.put(view, controller);
	}
	
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
