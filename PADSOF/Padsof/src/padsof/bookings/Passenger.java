package padsof.bookings;

import padsof.db.DBObject;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Passenger extends DBObject
{
	@DatabaseField
	private String name;

	@DatabaseField
	private String surname;

	@DatabaseField
	private String DNI;

	@DatabaseField(foreign = true)
	private FlightBooking flight;

	FlightBooking getFlight()
	{
		return flight;
	}

	void setFlight(FlightBooking flightBooking)
	{
		flight = flightBooking;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the surname
	 */
	public String getSurname()
	{
		return surname;
	}

	/**
	 * @param surname
	 *            the surname to set
	 */
	public void setSurname(String surname)
	{
		this.surname = surname;
	}

	/**
	 * @return the dNI
	 */
	public String getDNI()
	{
		return DNI;
	}

	/**
	 * @param dNI
	 *            the dNI to set
	 */
	public void setDNI(String dNI)
	{
		DNI = dNI;
	}
}
