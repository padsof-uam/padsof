package padsof.services;

public class Flight extends Service
{
	String localizer;

	/**
	 * @return the localizer
	 */
	public String getLocalizer()
	{
		return localizer;
	}

	/**
	 * @param localizer
	 *            the localizer to set
	 */
	public void setLocalizer(String localizer)
	{
		this.localizer = localizer;
	}

	public Flight(String localizer)
	{
		this.localizer = localizer;
	}
}
