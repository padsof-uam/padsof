/**
 * 
 */
package padsof.bookings;

import padsof.services.Flight;

/**
 * @author gjulianm
 */
public class FlightBooking extends Booking
{

	Flight associatedFlight;

	@Override
	public Flight getAssociatedService()
	{
		return associatedFlight;
	}

	public void setAssociatedService(Flight service)
	{
		associatedFlight = service;
	}

	/*
	 * (non-Javadoc)
	 * @see padsof.bookings.Booking#book()
	 */
	@Override
	public int book()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * @see padsof.bookings.Booking#confirm()
	 */
	@Override
	public int confirm()
	{
		// TODO Auto-generated method stub
		return 0;
	}
}
