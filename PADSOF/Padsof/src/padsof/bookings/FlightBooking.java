package padsof.bookings;

import java.util.*;
import padsof.services.Flight;

/**
 * @author gjulianm
 */
public class FlightBooking extends Booking
{
	private ArrayList<Passenger> passengers = new ArrayList<Passenger>();
	
	/**
	 * @return the passengers
	 */
	public List<Passenger> getPassengers()
	{
		return passengers;
	}

	private Flight associatedFlight;

	/**
	 * Gets the associated service.
	 * @see padsof.bookings.Booking#getAssociatedService()
	 */
	@Override
	public Flight getAssociatedService()
	{
		return associatedFlight;
	}

	/**
	 * Sets the associated service
	 * 
	 * @param service
	 *            the service
	 */
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
