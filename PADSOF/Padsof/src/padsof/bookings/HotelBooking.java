/**
 * 
 */
package padsof.bookings;

import java.util.Date;

import padsof.services.Hotel;
import padsof.system.*;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 
 * @author Víctor de Juan Sanz - Guillermo Julián Moreno
 *
 */
@DatabaseTable
public class HotelBooking extends Booking
{
	@DatabaseField(foreign = true, foreignAutoCreate = true,
			foreignAutoRefresh = true)
	private Hotel associatedService;

	public HotelBooking(Hotel service, Client client, Date start, Date end,
			Vendor vendor)
	{
		super(client, start, end, vendor);
		associatedService = service;
	}

	public HotelBooking()
	{
	}

	/*
	 * (non-Javadoc)
	 * @see padsof.bookings.Booking#getAssociatedService()
	 */
	@Override
	public Hotel getAssociatedService()
	{
		return associatedService;
	}

	/**
	 * Sets the associated service.
	 * 
	 * @param service
	 *            Service.
	 */
	public void setAssociatedService(Hotel service)
	{
		associatedService = service;
	}

}
