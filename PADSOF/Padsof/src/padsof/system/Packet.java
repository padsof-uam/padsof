package padsof.system;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import padsof.bookings.Booking;
import padsof.bookings.FlightBooking;
import padsof.bookings.HotelBooking;
import padsof.bookings.ImsersoTravelBooking;
import padsof.bookings.TravelBooking;
import padsof.db.DBObject;
import padsof.db.DBWrapper;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Packet extends DBObject
{
	@DatabaseField(foreign = true, foreignAutoRefresh = true,
			foreignAutoCreate = true)
	private Client client;

	private boolean isClose = false;

	/**
	 * @param book
	 *            to add to the packet
	 * @throws SQLException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
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
		{
			if (min.compareTo(aux.getStart()) >= 0)
			{
				min = aux.getStart();
			}
		}
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
		{
			if (max.compareTo(aux.getEnd()) <= 0)
				max = aux.getEnd();
		}
		return max;
	}

	/**
	 * checks if all the bookings of the packet are payed.
	 * 
	 * @return true if all bookings are payed, false if not.
	 * @throws SQLException 
	 */
	public boolean checkIfAllPayed() throws SQLException
	{

		for (Booking aux : getBookings())
		{
			if (!aux.isPayed())
				return false;
		}
		return true;
	}

	/**
	 * close package automatically if possible
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
			/*
			 * Guridi dice:
			 * Date dTemp = new Date();
			 * Date d = new Date(dTemp.getYear(), dTemp.getMonth(),
			 * dTemp.getDay()-1)
			 */
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
		return client;
	}

	/**
	 * @param client
	 *            the client to set
	 */
	public void setClient(Client client)
	{
		this.client = client;
	}

	/**
	 * @return the bookings. This is a copy, objects added here WILL NOT be
	 *         saved.
	 * @throws SQLException
	 */
	List<Booking> bookings;
	
	public void refreshBookings() throws SQLException
	{
		bookings = new ArrayList<Booking>();
		bookings.addAll(DBWrapper.getInstance().get(FlightBooking.class, "pertainingPacket", this));
		bookings.addAll(DBWrapper.getInstance().get(HotelBooking.class, "pertainingPacket", this));
		bookings.addAll(DBWrapper.getInstance().get(TravelBooking.class, "pertainingPacket", this));
		bookings.addAll(DBWrapper.getInstance().get(ImsersoTravelBooking.class, "pertainingPacket", this));
	}
	
	public List<Booking> getBookings() throws SQLException
	{
		if(bookings == null)
			refreshBookings();
		
		return bookings;
	}
}
