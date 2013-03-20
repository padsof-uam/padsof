package padsof.services;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Flight extends Service
{
	@DatabaseField
	private String localizer;

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

	public Flight()
	{
	}
}
