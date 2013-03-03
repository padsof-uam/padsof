/**
 * 
 */
package padsof.bookings;

import padsof.services.Hotel;

/**
 * @author gjulianm
 *
 */
public class HotelBooking extends Booking
{
	private Hotel associatedService;

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
