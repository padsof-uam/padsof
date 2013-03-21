package padsof.bookings;

import java.sql.SQLException;
import java.util.Date;

import padsof.services.*;
import padsof.system.*;

public class BookingFactory
{
	private Vendor vendor;

	public BookingFactory(Vendor vendor)
	{
		this.vendor = vendor;
	}

	/**
	 * 
	 * @param service
	 * @param client
	 * @param start
	 * @param end
	 * 
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws SQLException
	 */
	public Booking book(Service service, Client client, Date start, Date end)
			throws IllegalArgumentException, IllegalAccessException,
			SQLException
	{
		if (start.compareTo(end) > 0)
			throw new IllegalArgumentException("Start must be prior to end");

		Booking booking;

		if (Flight.class.isInstance(service))
			booking = createFlightBooking((Flight) service, client, start, end);
		else if (Hotel.class.isInstance(service))
			booking = createHotelBooking((Hotel) service, client, start, end);
		else if (ImsersoTravel.class.isInstance(service))
			booking = createImsersoTravelBooking((ImsersoTravel) service,
					client, start, end);
		else if (Travel.class.isInstance(service))
			booking = createTravelBooking((Travel) service, client, start, end);
		else
			throw new UnsupportedOperationException("Class "
					+ service.getClass().getName()
					+ " doesn't have a booking constructor.");

		return booking;
	}

	/**
	 * Create a TravelBooking with the param. recieved.
	 * 
	 * @param service
	 * @param client
	 * @param start
	 * @param end
	 * @return
	 */
	private Booking createTravelBooking(Travel service, Client client,
			Date start, Date end)
	{
		return new TravelBooking(service, client, start, end, vendor);
	}

	/**
	 * Create a FlightBooking with the param. received.
	 * 
	 * @param service
	 * @param client
	 * @param start
	 * @param end
	 */
	private Booking createFlightBooking(Flight service, Client client,
			Date start, Date end)
	{
		FlightBooking FB = new FlightBooking(service, client, start, end,
				vendor);
		return FB;
	}

	/**
	 * Create an HotelBooking with the param. recieved.
	 * @param service
	 * @param client
	 * @param start
	 * @param end
	 * @return
	 */
	private Booking createHotelBooking(Hotel service, Client client,
			Date start, Date end)
	{
		return new HotelBooking(service, client, start, end, vendor);
	}

	/**
	 * Create an ImsersoTravelBooking with the param. recieved.
	 * @param service
	 * @param client
	 * @param start
	 * @param end
	 * @return
	 */
	private Booking createImsersoTravelBooking(ImsersoTravel service,
			Client client, Date start, Date end)
	{
		if (!ImsersoClient.class.isInstance(client))
			throw new UnsupportedOperationException(
					"Client must be ImsersoClient");
		return new ImsersoTravelBooking(service, (ImsersoClient) client, start,
				end, vendor);
	}
}
