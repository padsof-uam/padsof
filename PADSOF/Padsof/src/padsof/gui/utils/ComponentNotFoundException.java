package padsof.gui.utils;

/**
 * A component wasn't found.
 * 
 * @author Víctor de Juan Sanz - Guillermo Julián Moreno
 */
public class ComponentNotFoundException extends RuntimeException
{
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
	 * @return the component name that wasn't found
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

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "ComponentNotFoundException [message=" + message
				+ ", component=" + component + "]";
	}
}
