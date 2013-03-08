package padsof.bookings;

import java.util.*;
import padsof.services.*;
import padsof.system.*;

public class BookingFactory
{
	public Booking book(Service service, Client client, Date start, Date end)
	{
		// TODO: Mirar bien cómo hacerlo.
		return null;
	}
	private Booking createFligthBooking(Flight service, Client client, Date start, Date end){
		FlightBooking FB = new FlightBooking(service,client,start,end);
		return FB;				
	}
}
