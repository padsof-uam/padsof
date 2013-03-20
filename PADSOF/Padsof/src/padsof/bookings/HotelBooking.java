/**
 * 
 */
package padsof.bookings;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import padsof.services.Hotel;
import padsof.system.Client;
import padsof.system.Vendor;

/**
 * @author gjulianm
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
		this.associatedService = service;
	}
	
	public HotelBooking() {}

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
