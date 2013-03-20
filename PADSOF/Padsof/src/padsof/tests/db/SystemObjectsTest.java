package padsof.tests.db;

import static org.junit.Assert.assertEquals;
import static padsof.tests.Assert.assertAreSameCollection;

import java.sql.SQLException;
import java.util.*;

import org.junit.*;

import padsof.bookings.*;
import padsof.db.DBWrapper;
import padsof.services.*;
import padsof.system.*;

public class SystemObjectsTest
{

	private static DBWrapper db;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		db = new DBWrapper("testSystemDB");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
		db.close();
		db.delete();
	}

	@Test
	public void testSaveClient() throws IllegalArgumentException, IllegalAccessException, SQLException
	{
		Client client = new Client();
		client.setDNI("ASDkkk");
		client.setName("Myname");
		client.setSurname("Test");
		
		db.save(client);
		
		Client retrieved = db.getAll(Client.class).get(0);
		
		assertEquals(client, retrieved);
	}

	@Test
	public void testSaveImsersoClient() throws IllegalArgumentException, IllegalAccessException, SQLException
	{
		ImsersoClient client = new ImsersoClient();
		client.setDNI("ASDkkk");
		client.setName("Myname");
		client.setSurname("Test");
		client.setBirth(new Date());
		client.setSsNumber(1765770123);
			
		db.save(client);
		
		ImsersoClient retrieved = db.getAll(ImsersoClient.class).get(0);
		
		assertEquals(client, retrieved);
	}


	@Test
	public void testSaveVendor() throws Exception
	{
		Vendor vendor = new Vendor();
		vendor.setName("me");
		vendor.setPassword("test");
		vendor.setUser("asdasd");
		
		db.save(vendor);
		
		Vendor retrieved = db.getAll(Vendor.class).get(0);
		
		assertEquals(vendor, retrieved);
	}
	
	@Test
	public void testPacket() throws Exception
	{
		db.clear();
		Date start = new GregorianCalendar(2016, 1, 10).getTime();
		Date end = new GregorianCalendar(2017, 1, 10).getTime();
		
		Vendor vendor = new Vendor();
		vendor.setName("me");
		vendor.setPassword("test");
		vendor.setUser("asdasd");
		
		BookingFactory bf = new BookingFactory(vendor);
		
		ImsersoClient client = new ImsersoClient();
		client.setDNI("7691001D");
		client.setName("Test client");
		client.setSurname("Client");
		client.setBirth(start);
		client.setSsNumber(187676713);
		
		Flight f = new Flight();
		f.setLocalizer("EF1920");
		f.setCost(53.1);
		f.setDescription("Flight test.");
		f.setName("Flight to N");
		f.setPrice(60);
		
		FlightBooking fb = (FlightBooking) bf.book(f, client, start, end);

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
		
		HotelBooking hb = (HotelBooking) bf.book(h, client, start, end);
		
		hb.book();
		hb.cancel();
		
		ImsersoTravel it = new ImsersoTravel("Mallorca-temporada baja",
				(double) 125, 8, 7, "1 al 17 de diciembre, 1 al 31 de Enero",
				"Madrid", "Mallorca",
				"Vuelo, Hotel 3* en régimen de PC y desplazamiento");
				
		ImsersoTravelBooking itb = (ImsersoTravelBooking) bf.book(it, client, start, end);
		
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
		
		TravelBooking tb = (TravelBooking) bf.book(t, client, start, end);
		tb.confirm();
		
		Packet p = new Packet();
		p.setClient(client);
		p.add(tb);
		p.add(itb);
		p.add(hb);
		p.add(fb);
		
		db.save(p);
		
		Packet retrieved = db.getAll(Packet.class).get(0);
		
		assertEquals(p, retrieved);
		assertAreSameCollection(p.getBookings(), retrieved.getBookings());
		db.clear();
	}
}
