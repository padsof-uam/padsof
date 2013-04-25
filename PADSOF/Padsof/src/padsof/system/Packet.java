package padsof.system;

import java.sql.SQLException;
import java.util.*;

import padsof.bookings.*;
import padsof.db.*;
import padsof.services.*;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author Víctor de Juan Sanz - Guillermo Julián Moreno
 */
@DatabaseTable
public class Packet extends DBObject
{
	@DatabaseField(foreign = true, foreignAutoRefresh = true,
			foreignAutoCreate = true)
	private Client client;
	
	@DatabaseField(foreign = true, foreignAutoRefresh = true,
			foreignAutoCreate = true)
	private ImsersoClient iclient;
	

	private boolean isClose = false;

	/**
	 * @param book
	 *            to add to the packet
	 * @throws SQLException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */

	/**
	 * Add a booking to the packet and saves it into the database.
	 * 
	 * @param book
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws SQLException
	 */
	public void add(Booking book) throws IllegalArgumentException,
			IllegalAccessException, SQLException
	{
		if (!IsClose())
		{
			book.setPertainingPacket(this);
			DBWrapper.getInstance().save(this);
			DBWrapper.getInstance().save(book);
		}
	}

	/**
	 * get thes start day of the packet
	 * 
	 * @return the date
	 * @throws SQLException
	 */
	public Date getStartDay() throws SQLException
	{
		Date min = new Date(Long.MAX_VALUE);

		for (Booking aux : getBookings())
			if (min.compareTo(aux.getStart()) >= 0)
				min = aux.getStart();
		return min;
	}

	/**
	 * get the last day of the packet
	 * 
	 * @return the date
	 * @throws SQLException
	 */
	public Date getEndDay() throws SQLException
	{
		Date max = new Date(Long.MIN_VALUE);

		for (Booking aux : getBookings())
			if (max.compareTo(aux.getEnd()) <= 0)
				max = aux.getEnd();
		return max;
	}

	/**
	 * Checks if all the bookings of the packet are payed.
	 * 
	 * @return true if all bookings are payed, false if not.
	 * @throws SQLException
	 */
	public boolean checkIfAllPayed() throws SQLException
	{

		for (Booking aux : getBookings())
			if (!aux.isPayed())
				return false;
		return true;
	}

	/**
	 * Close package automatically if possible
	 * 
	 * @return true if the package was closed or false if is still open
	 * @throws SQLException
	 */
	public boolean closeAutomatically() throws SQLException
	{
		if (!IsClose())
		{
			GregorianCalendar c = new GregorianCalendar();
			c.add(Calendar.DAY_OF_MONTH, 1);
			Date d = c.getTime();
			if (getStartDay().before(d))
			{
				closePacket();
				return true;
			}
			return false;
		}
		else
			return true;
	}

	// Setters y getters

	/**
	 * @return if it's closed or not
	 */
	public boolean IsClose()
	{
		return isClose;
	}

	/**
	 * Close the packet
	 */
	public void closePacket()
	{
		isClose = true;
	}

	/**
	 * @return the client
	 */
	public Client getClient()
	{
		if(client == null)
			return iclient;
		else
			return client;
	}

	/**
	 * @param client
	 *            the client to set
	 */
	public void setClient(Client client)
	{
		if(client instanceof ImsersoClient)
			iclient = (ImsersoClient) client;
		else
			this.client = client;
	}

	List<Booking> bookings;

	/**
	 * Load in memory the bookings from the database.
	 * 
	 * @return the bookings. This is a copy, objects added here WILL NOT be
	 *         saved.
	 * @throws SQLException
	 */

	public void refreshBookings() throws SQLException
	{
		bookings = new ArrayList<Booking>();
		bookings.addAll(DBWrapper.getInstance().get(FlightBooking.class,
				"pertainingPacket", this));
		bookings.addAll(DBWrapper.getInstance().get(HotelBooking.class,
				"pertainingPacket", this));
		bookings.addAll(DBWrapper.getInstance().get(TravelBooking.class,
				"pertainingPacket", this));
		bookings.addAll(DBWrapper.getInstance().get(ImsersoTravelBooking.class,
				"pertainingPacket", this));
	}

	/**
	 * Get the bookings of the packet checking the database.
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<Booking> getBookings() throws SQLException
	{
		if (bookings == null)
			refreshBookings();

		return bookings;
	}

	public String toString()
	{
		GregorianCalendar today = new GregorianCalendar();
		today.add(Calendar.DAY_OF_YEAR, 1);
		String state = new String();
		Date packetStart = null;
		try
		{
			packetStart = this.getStartDay();
		}
		catch (SQLException e1)
		{
		}
		
		if (packetStart.before(today.getTime())){
			state+="! ";	
		} else 
			state += "o ";
		if (this.IsClose())
			state += "x ";
		
		List<Booking> bookings;
		try
		{
			bookings = getBookings();
		}
		catch (SQLException e)
		{
			return("No se han podido cargar las reservas del paquete");
		}
		
		String retval = new String("Paquete - ");
		int hotel = 0, flight = 0, travel = 0;
		for (int i = 0; i < bookings.size(); ++i)
		{
			if (bookings.get(i).getAssociatedService().getClass() == Hotel.class)
			{
				hotel++;
			}
			else if (bookings.get(i).getAssociatedService().getClass() == Flight.class)
			{
				flight++;
			}
			else if (bookings.get(i).getAssociatedService().getClass() == Travel.class
					|| (bookings.get(i).getAssociatedService().getClass() == ImsersoTravel.class))
			{
				travel++;
			}
		}

		String strTrip = ", Viajes organizados: " + travel;
		String strFlight = ", Vuelos: " + flight;
		String strHotel = "Hoteles: " + hotel;

		return state + retval + strHotel + strTrip + strFlight;

	}
}
