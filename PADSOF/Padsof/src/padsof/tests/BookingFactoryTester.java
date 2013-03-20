/**
 * 
 */
package padsof.tests;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.*;

import org.junit.*;

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
	private Vendor vendor;

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

		vendor = new Vendor("Victor", "vicdejuan", "qwerty");
		Calendar calendar = new GregorianCalendar();

		testStart = calendar.getTime();
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		testEnd = calendar.getTime();

		factory = new BookingFactory(vendor);
	}

	class UnsupportedService extends Service
	{

	}

	@Test(expected = UnsupportedOperationException.class)
	public void testUnsupportedService() throws IllegalArgumentException,
			IllegalAccessException, SQLException
	{
		factory.book(new UnsupportedService(), testClient, testStart, testEnd);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidDates() throws IllegalArgumentException,
			IllegalAccessException, SQLException
	{
		factory.book(new Hotel(), testClient, testEnd, testStart);
	}

	@Test
	public void testFlightBooking() throws IllegalArgumentException,
			IllegalAccessException, SQLException
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
	public void testHotelBooking() throws IllegalArgumentException,
			IllegalAccessException, SQLException
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
	public void testTravelBooking() throws IllegalArgumentException,
			IllegalAccessException, SQLException
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
	public void testImsersoTravelBooking() throws IllegalArgumentException,
			IllegalAccessException, SQLException
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
			throws IllegalArgumentException, IllegalAccessException,
			SQLException
	{
		factory.book(new ImsersoTravel(), testClient, testStart, testEnd);
	}
}
