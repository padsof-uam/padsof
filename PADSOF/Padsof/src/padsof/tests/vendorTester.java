package padsof.tests;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import padsof.bookings.Booking;
import padsof.bookings.BookingFactory;
import padsof.bookings.PaymentState;
import padsof.services.Flight;
import padsof.services.Hotel;
import padsof.services.ImsersoTravel;
import padsof.services.Travel;
import padsof.system.Client;
import padsof.system.ImsersoClient;
import padsof.system.Vendor;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class vendorTester
{
	private Vendor testVendor;
	private Vendor testAdmin;
	/**
	 * 
	 */
	@Before
	public void setUp(){
		testVendor = new Vendor ("Victor","VicdeJuan","qwerty");
		testAdmin = new Vendor ("Victor","VicdeJuan","qwerty");
		testAdmin.becomeAdmin();
	}
	@Test (expected = UnsupportedOperationException.class)
	public void checkNotAdmin(){
		Vendor testVendor1 = testAdmin.createVendor("Victor","VicdeJuan","qwerty");
		testVendor1.createVendor(new String(), new String(), new String());		
	}
	@Test
	public void checkMoneyGot(){
		/**
		 * setUp()
		 */
		Client testClient = new Client();
		testClient.setDNI("671681831A");
		testClient.setName("Pepito");
		testClient.setSurname("Grillo");
		Vendor testVendor = new Vendor("Victor","Vicdejuan","qwerty");
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
		
		//TODO: pero... este set es de servicio... 
		Hotel h1 = new Hotel();
		h1.setPrice(300);
		
		ImsersoTravel IT1 = new ImsersoTravel();
		IT1.setPrice(100);
			
		Travel T1 = new Travel();
		T1.setPrice(200);
		
		Booking hotel = factory.book(h1, testClient, testStart_h, testEnd_h);
		Booking ImTravel = factory.book(IT1, new ImsersoClient(), testStart_IT, testEnd_IT);
		Booking travel = factory.book(T1, testClient, testStart_f, testEnd_f);
		
		ImTravel.setState(PaymentState.Booked);
		ImTravel.setVendorUser(testVendor);

		travel.setState(PaymentState.None);
		travel.setVendorUser(testVendor);
		
		hotel.setState(PaymentState.Payed);
		hotel.setVendorUser(testVendor);
		
		
		assertEquals(testVendor.getNumberBookings(),1);
		assertEquals(testVendor.getNumberConfirmed(),1);
		assertEquals(testVendor.getNumberCanceled(),1);
		
		assertEquals((double)testVendor.getMoneyGot(),(double)310,0.0000001);
	}
	@Test (expected = UnsupportedOperationException.class)
	public void checkChangePass(){
		testAdmin.changePassword(testVendor, "asdfgh");
		assertEquals(testVendor.getPassword(),"asdfgh");
		testVendor.changePassword(testAdmin, "asdfgh");
	}
	
	
}
