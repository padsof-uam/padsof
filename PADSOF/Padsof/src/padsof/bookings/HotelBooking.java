/**
 * 
 */
package padsof.bookings;

import java.util.Date;

import padsof.services.Hotel;
import padsof.system.Client;

/**
 * @author gjulianm
 *
 */
public class HotelBooking extends Booking
{
	private Hotel associatedService;

	public HotelBooking(Hotel service, Client client, Date start, Date end)
	{
		super(client, start, end);
		this.associatedService = service;
	}

	/* (non-Javadoc)
	 * @see padsof.bookings.Booking#getAssociatedService()
	 */
	@Override
	public Hotel getAssociatedService()
	{
		return associatedService;
	}
	
	/**
	 * Sets the associated service.
	 * @param service Service.
	 */
	public void setAssociatedService(Hotel service)
	{
		associatedService = service;
	}


}
