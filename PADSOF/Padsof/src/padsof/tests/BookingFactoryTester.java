/**
 * 
 */
package padsof.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import java.util.*;

import padsof.bookings.*;
import padsof.services.*;
import padsof.system.*;

/**
 * @author gjulianm
 */
public class BookingFactoryTester
{
	private Client testClient;
	private Date testStart;
	private Date testEnd;
	private BookingFactory factory;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		testClient = new Client();
		testClient.setDNI("671681831A");
		testClient.setName("Pepito");
		testClient.setSurname("Grillo");

		Calendar calendar = new GregorianCalendar();

		testStart = calendar.getTime();
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		testEnd = calendar.getTime();

		factory = new BookingFactory();
	}

	class UnsupportedService extends Service
	{

	}

	@Test(expected = UnsupportedOperationException.class)
	public void testUnsupportedService()
	{
		factory.book(new UnsupportedService(), testClient, testStart, testEnd);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidDates()
	{
		factory.book(sdoifuyasodIUFYaiusf6cgox8ºiu1	º8tyer123iquwoadspQnew Hotel(), testClient, testEnd, testStart);
	}

	@Test
	public void testFlightBooking()
	{
		Flight h = new Flight();
		Booking b = factory.book(h, testClient, testStart, testEnd);

		assertTrue(FlightBooking.class.isInstance(b));
		assertSame(h, b.getAssociatedService());
		assertSame(testClient, b.getClient());
		assertSame(testStart, b.getStart());
		assertSame(testEnd, b.getEnd());
	}

	@Test
	public void testHotelBooking()
	{
		Hotel h = new Hotel();
		Booking b = factory.book(h, testClient, testStart, testEnd);

		assertTrue(HotelBooking.class.isInstance(b));
		assertSame(h, b.getAssociatedService());
		assertSame(testClient, b.getClient());
		assertSame(testStart, b.getStart());
		assertSame(testEnd, b.getEnd());
	}

	@Test
	public void testTravelBooking()
	{
		Travel h = new Travel();
		Booking b = factory.book(h, testClient, testStart, testEnd);

		assertTrue(TravelBooking.class.isInstance(b));
		assertSame(h, b.getAssociatedService());
		assertSame(testClient, b.getClient());
		assertSame(testStart, b.getStart());
		assertSame(testEnd, b.getEnd());
	}

	@Test
	public void testImsersoTravelBooking()
	{
		ImsersoTravel h = new ImsersoTravel();
		Client ic = new ImsersoClient();
		Booking b = factory.book(h, ic, testStart, testEnd);

		assertTrue(ImsersoTravelBooking.class.isInstance(b));
		assertSame(h, b.getAssociatedService());
		assertSame(ic, b.getClient());
		assertSame(testStart, b.getStart());
		assertSame(testEnd, b.getEnd());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testInvalidClientImsersoTravelBooking()
	{
		factory.book(new ImsersoTravel(), testClient, testStart, testEnd);
	}
}
