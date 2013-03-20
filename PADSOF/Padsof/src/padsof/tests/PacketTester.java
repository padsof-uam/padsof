/**
 * 
 */
package padsof.tests;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.*;

import org.junit.*;

import padsof.bookings.*;
import padsof.db.DBWrapper;
import padsof.services.*;
import padsof.system.*;

/**
 * @author vicdejuan
 */
public class PacketTester
{
	private BookingFactory factory;
	private Client testClient;
	private Date testStart_f;
	private Date testEnd_f;
	private Date testStart_h;
	private Date testEnd_h;
	private Date testStart_IT;
	private Date testEnd_IT;
	private ArrayList<Booking> testBookings = new ArrayList<Booking>();
	private Packet testPacket = new Packet();
	private Vendor vendor;
	private static DBWrapper db;

	@BeforeClass
	public static void setUpClass() throws SQLException
	{
		db = new DBWrapper("testPacketDB");
	}

	@AfterClass
	public static void tearDown() throws SQLException
	{
		db.close();
		db.delete();
	}

	@SuppressWarnings("deprecation")
	public boolean compare(Date one, Date other)
	{
		if (one.getDay() == other.getDay()
				&& one.getMonth() == other.getMonth()
				&& one.getYear() == other.getYear())
			return true;
		else
			return false;

	}

	/*
	 * Si lanza una excepcion tenemos que definir (expected = ExpectedException)
	 * y despues comprobamos.
	 */
	@Before
	public void setUp() throws Exception
	{

		testClient = new Client();
		testClient.setDNI("671681831A");
		testClient.setName("Pepito");
		testClient.setSurname("Grillo");

		vendor = new Vendor("Victor", "vicdejuan", "qwerty");

		factory = new BookingFactory(vendor);

		Calendar calendar = new GregorianCalendar();

		/*
		 * Dates for the flight
		 */
		calendar.add(Calendar.DAY_OF_MONTH, 2);
		testStart_f = calendar.getTime();
		calendar.add(Calendar.DAY_OF_MONTH, 5);
		testEnd_f = calendar.getTime();

		calendar.add(Calendar.DAY_OF_MONTH, -3);

		/*
		 * Dates for the hotel
		 */
		testStart_h = calendar.getTime();
		calendar.add(Calendar.DAY_OF_MONTH, 20);
		testEnd_h = calendar.getTime();

		calendar.add(Calendar.MONTH, 1);

		/*
		 * Dates for the ImsersoTravel
		 */
		testStart_IT = calendar.getTime();
		calendar.add(Calendar.DAY_OF_MONTH, 2);
		testEnd_IT = calendar.getTime();

		Booking hotel = factory.book(new Hotel(), testClient, testStart_h,
				testEnd_h);
		Booking travel = factory.book(new ImsersoTravel(), new ImsersoClient(),
				testStart_IT, testEnd_IT);
		Booking flight = factory.book(new Flight(), testClient, testStart_f,
				testEnd_f);

		flight.setState(PaymentState.Booked);
		hotel.setState(PaymentState.Payed);
		travel.setState(PaymentState.Payed);

		testBookings.add(flight);
		testBookings.add(hotel);
		testBookings.add(travel);

		/*
		 * We supose setters and getters methods cant fail.
		 */
		testPacket.setClient(testClient);
		testPacket.add(travel);
		testPacket.add(hotel);
		testPacket.add(flight);
	}

	@Test
	public void TestCheckDates() throws SQLException
	{

		assertFalse(testPacket.checkIfAllPayed());

		// The lowest date is testStart_f
		assertEquals(testStart_f, testPacket.getStartDay());

		// The lastest day is testEnd_IT
		assertEquals(testEnd_IT, testPacket.getEndDay());
	}

	@Test
	public void TestCloseIfPossible() throws SQLException
	{

		assertFalse(testPacket.closeAutomatically());
		Calendar calendar = new GregorianCalendar();

		testPacket.getBookings().get(0).setStart(calendar.getTime());

		assertTrue(testPacket.closeAutomatically());

		// Comprobar que esta cerrado
		assertTrue(testPacket.IsClose());

	}

}
