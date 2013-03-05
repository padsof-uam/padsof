/**
 * 
 */
package padsof.bookings;

import padsof.services.Travel;

/**
 * @author gjulianm
 *
 */
public class TravelBooking extends Booking
{
	private Travel associatedTravel;
	

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
	
	/* (non-Javadoc)
	 * @see padsof.bookings.Booking#book()
	 */
	@Override
	public int book()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see padsof.bookings.Booking#confirm()
	 */
	@Override
	public int confirm()
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
