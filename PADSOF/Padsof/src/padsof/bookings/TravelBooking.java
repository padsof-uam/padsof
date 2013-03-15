/**
 * 
 */
package padsof.bookings;

import java.util.Date;

import padsof.services.Travel;
import padsof.system.Client;

/**
 * @author gjulianm
 *
 */
public class TravelBooking extends Booking
{
	private Travel associatedTravel;
	

	public TravelBooking(Travel service, Client client, Date start, Date end)
	{
		super(client,start, end);
		this.associatedTravel = service;
	}

	/* (non-Javadoc)
	 * @see padsof.bookings.Booking#getAssociatedService()
	 */
	@Override
	public Travel getAssociatedService()
	{
		return associatedTravel;
	}

	public void setAssociatedService(Travel service)
	{
		associatedTravel = service;
	}
	
}
