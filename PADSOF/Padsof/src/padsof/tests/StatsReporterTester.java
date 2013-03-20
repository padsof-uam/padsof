package padsof.tests;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.*;

import org.junit.*;

import padsof.bookings.*;
import padsof.db.DBWrapper;
import padsof.services.*;
import padsof.system.*;

public class StatsReporterTester
{
	private static DBWrapper db;
	private static Vendor vendor1, vendor2;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		db = new DBWrapper("StatsReporterTestDB");
		
		db.clear();
		Date start = new GregorianCalendar(2016, 1, 10).getTime();
		Date end = new GregorianCalendar(2017, 1, 10).getTime();
		
		vendor1 = new Vendor();
		vendor1.setName("me");
		vendor1.setPassword("test");
		vendor1.setUser("asdasd");
		
		vendor2 = new Vendor();
		vendor2.setName("you");
		vendor2.setPassword("another test");
		vendor2.setUser("Test");
		
		BookingFactory bf1 = new BookingFactory(vendor1);
		BookingFactory bf2 = new BookingFactory(vendor2);
		
		ImsersoClient client = new ImsersoClient();
		client.setDNI("7691001D");
		client.setName("Test client");
		client.setSurname("Client");
		client.setBirth(start);
		client.setSsNumber(187676713);
		
		Flight f = new Flight();
		f.setLocalizer("EF1920");
		f.setCost(50);
		f.setDescription("Flight test.");
		f.setName("Flight to N");
		f.setPrice(60);
		
		FlightBooking fb = (FlightBooking) bf1.book(f, client, start, end);

		Passenger a = new Passenger();
		a.setDNI("ASDJKJ");
		a.setName("Mey");
		a.setSurname("jkjk");
		
		Passenger b = new Passenger();
		b.setName("JFK");
		b.setDNI("0");
		b.setSurname("Grillo");
		
		fb.addPassenger(a);
		fb.addPassenger(b);
		
		Hotel h = new Hotel(
				"España",
				"Madrid",
				"NH Palacio de Tepa",
				"91 555555",
				"San Sebastian 2",
				"28012",
				"4",
				(double) 80,
				(double) 120,
				(double) 160,
				(double) 15,
				(double) 40,
				(double) 60,
				"Restaurante, Bar, Recepción 24 horas, Prensa , Habitaciones no fumadores, Adaptado personas de movilidad reducida, Ascensor, Registro de entrada y salida exprés, Caja fuerte, Calefacción, Hotel de diseño, Guardaequipaje, Aire acondicionado, Zona de fumadores, Restaurante (a la carta), free wifi");
		
		HotelBooking hb = (HotelBooking) bf1.book(h, client, start, end);
		
		hb.book();
		hb.cancel();
		
		ImsersoTravel it = new ImsersoTravel("Mallorca-temporada baja",
				(double) 125, 8, 7, "1 al 17 de diciembre, 1 al 31 de Enero",
				"Madrid", "Mallorca",
				"Vuelo, Hotel 3* en régimen de PC y desplazamiento");
				
		ImsersoTravelBooking itb = (ImsersoTravelBooking) bf1.book(it, client, start, end);
		
		itb.book();
		
		Travel t = new Travel(
				"Mallorca-1",
				"Halc�n Viajes",
				"91 218 21 28",
				(double) 190,
				3,
				2,
				"Todos los lunes de Enero-Mayo y Octubre-Diciembre. Todos los d�as de Junio a Septiembre",
				"Madrid", "Mallorca",
				"Hotel Be Live Punta Amer 4* en Media Pensi�n.");
		
		TravelBooking tb = (TravelBooking) bf1.book(t, client, start, end);
		tb.confirm();
		
		Packet p = new Packet();
		p.setClient(client);
		p.add(tb);
		p.add(itb);
		p.add(hb);
		p.add(fb);
		
		FlightBooking fb2 = (FlightBooking) bf2.book(f, client, start, end);
		
		HotelBooking hb2 = (HotelBooking) bf2.book(h, client, start, end);
		
		hb2.book();
		hb2.cancel();
	
		ImsersoTravelBooking itb2 = (ImsersoTravelBooking) bf2.book(it, client, start, end);
		
		itb2.book();
		TravelBooking tb2 = (TravelBooking) bf2.book(t, client, start, end);
		tb2.confirm();
		
		Packet p2 = new Packet();
		p2.setClient(client);
		p2.add(tb2);
		p2.add(itb2);
		p2.add(hb2);
		p2.add(fb2);
		
		db.save(p);
		db.save(p2);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
		db.close();
		db.delete();
	}

	@Test
	public void test() throws SQLException
	{
		StatsReporter reporter = new StatsReporter();
	
		assertEquals(-225, reporter.getBenefits(), 0.1);
		assertEquals(-112.5, reporter.getBenefitsOf(vendor1), 0.1);
		assertEquals(2, reporter.getServicesBooked());
		assertEquals(1, reporter.getServicesBookedOf(vendor1));
		assertEquals(4, reporter.getServicesCanceled());
		assertEquals(2, reporter.getServicesCanceledOf(vendor1));
		assertEquals(2, reporter.getServicesSold());
		assertEquals(1, reporter.getServicesSoldOf(vendor1));
		assertEquals(405, reporter.getTotalMoneyIn(), 0.1);
		assertEquals(202.5, reporter.getTotalMoneyInOf(vendor1), 0.1);
		assertEquals(630, reporter.getTotalMoneyOut(), 0.1);
		assertEquals(315, reporter.getTotalMoneyOutOf(vendor1), 0.1);
		assertEquals(8, reporter.getTotalServices());
		assertEquals(4, reporter.getTotalServicesOf(vendor1));


	}

}
