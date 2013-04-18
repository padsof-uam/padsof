package padsof.gui.utils;

public class ComponentNotFoundException extends RuntimeException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 352821494462979210L;
	private String message;
	/**
	 * @return the message
	 */
	public String getMessage()
	{
		return message;
	}


	/**
	 * @return the component
	 */
	public String getComponent()
	{
		return component;
	}


	private String component;
	
	
	public ComponentNotFoundException(String message, String component)
	{
		this.message = message;
		this.component = component;
	}
}	
