package padsof.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import padsof.bookings.Booking;
import padsof.bookings.BookingFactory;
import padsof.bookings.PaymentState;
import padsof.db.DBWrapper;
import padsof.services.Hotel;
import padsof.services.ImsersoTravel;
import padsof.services.Travel;
import padsof.system.Client;
import padsof.system.ImsersoClient;
import padsof.system.Vendor;

public class vendorTester
{
	private static Vendor testVendor;
	private static Vendor testAdmin;
	private static DBWrapper db;
	/**
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUp() throws Exception
	{
		testVendor = new Vendor("Victor", "VicdeJuan", "qwerty");
		testAdmin = new Vendor("Victor", "VicdeJuan", "qwerty");
		testAdmin.becomeAdmin();
		db = new DBWrapper("testVendor");
	}

	@AfterClass
	public static void tearDown() throws SQLException
	{
		db.close();
		db.delete();
	}
	
	@Test
	public void checkMoneyGot() throws Exception
	{
		/**
		 * setUp()
		 */
		Client testClient = new Client();
		testClient.setDNI("671681831A");
		testClient.setName("Pepito");
		testClient.setSurname("Grillo");
		Vendor testVendor = new Vendor("Victor", "Vicdejuan", "qwerty");
		BookingFactory factory = new BookingFactory(testVendor);

		Calendar calendar = new GregorianCalendar();
		/*
		 * Dates for the flight
		 */
		calendar.add(Calendar.DAY_OF_MONTH, 2);
		Date testStart_f = calendar.getTime();
		calendar.add(Calendar.DAY_OF_MONTH, 5);
		Date testEnd_f = calendar.getTime();

		calendar.add(Calendar.DAY_OF_MONTH, -3);

		/*
		 * Dates for the hotel
		 */
		Date testStart_h = calendar.getTime();
		calendar.add(Calendar.DAY_OF_MONTH, 20);
		Date testEnd_h = calendar.getTime();

		calendar.add(Calendar.MONTH, 1);

		/*
		 * Dates for the ImsersoTravel
		 */
		Date testStart_IT = calendar.getTime();
		calendar.add(Calendar.DAY_OF_MONTH, 2);
		Date testEnd_IT = calendar.getTime();

		Hotel h1 = new Hotel();
		h1.setPrice(300);

		ImsersoTravel IT1 = new ImsersoTravel();
		IT1.setPrice(100);

		Travel T1 = new Travel();
		T1.setPrice(200);

		Booking hotel = factory.book(h1, testClient, testStart_h, testEnd_h);
		Booking ImTravel = factory.book(IT1, new ImsersoClient(), testStart_IT,
				testEnd_IT);
		Booking travel = factory.book(T1, testClient, testStart_f, testEnd_f);

		ImTravel.setState(PaymentState.Booked);
		ImTravel.setVendorUser(testVendor);

		travel.setState(PaymentState.None);
		travel.setVendorUser(testVendor);

		hotel.setState(PaymentState.Payed);
		hotel.setVendorUser(testVendor);

		assertEquals("Number of bookings", 1, testVendor.getNumberBookings());
		assertEquals("Number of confirmed bookings", 1, testVendor.getNumberConfirmed());
		assertEquals("Number of canceled bookings", 1, testVendor.getNumberCanceled());

		assertEquals((double) testVendor.getMoneyGot(), (double) 310, 0.0000001);
	}

	@Test
	public void checkPassword() throws Exception
	{
		testVendor.setPassword("asdfgh");
		assertTrue(testVendor.checkPassword("asdfgh"));
	}

}
