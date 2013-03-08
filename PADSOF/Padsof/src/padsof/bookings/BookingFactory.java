package padsof.bookings;

import java.util.*;

import padsof.bookings.*;
import padsof.services.*;
import padsof.system.*;

public class BookingFactory
{
	public Booking book(Service service, Client client, Date start, Date end)
	{
		if(Flight.class.isInstance(service))
			return createFlightBooking((Flight)service, client, start, end);
		else if(Hotel.class.isInstance(service))
			return createHotelBooking((Hotel)service, client, start, end);
		else if(ImsersoTravel.class.isInstance(service))
			return createImsersoTravelBooking((ImsersoTravel)service, client, start, end);
		else if(Travel.class.isInstance(service))
			return createTravelBooking((Travel)service, client, start, end);
		else
			throw new UnsupportedOperationException("Class " + service.getClass().getName() + " doesn't have a booking constructor.");
	}
	
	private Booking createTravelBooking(Travel service, Client client,
			Date start, Date end)
	{
		return new TravelBooking(service, client, start, end);
	}

	private Booking createFlightBooking(Flight service, Client client, Date start, Date end){
		FlightBooking FB = new FlightBooking(service,client,start,end);
		return FB;				
	}

	private Booking createHotelBooking(Hotel service, Client client, Date start, Date end)
	{
		return new HotelBooking(service, client, start, end);		
	}
	
	private Booking createImsersoTravelBooking(ImsersoTravel service, Client client, Date start, Date end)
	{
		if(ImsersoClient.class.isInstance(client))
			throw new UnsupportedOperationException("Client must be ImsersoClient");
		return new ImsersoTravelBooking(service, client, start, end);
	}
}
