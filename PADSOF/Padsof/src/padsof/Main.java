package padsof;

import java.util.*;

import com.j256.ormlite.logger.LocalLog;

import es.uam.eps.pads.services.ServicesFactory;
import es.uam.eps.pads.services.flights.*;
import padsof.bookings.*;
import padsof.db.DBWrapper;
import padsof.db.feeder.DBFeeder;
import padsof.gui.Application;
import padsof.services.*;
import padsof.system.*;

public class Main
{

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
	{
		startGui();
	}
	
	private static void startGui() throws Exception
	{
		Application app = Application.getInstance();
	}

	@SuppressWarnings("unused")
	private static void consoleDemo() throws Exception
	{
		// Suppress debug messages.
		System.setProperty(LocalLog.LOCAL_LOG_LEVEL_PROPERTY, "error");
		System.out.println("Creating database...");
		DBWrapper db = new DBWrapper("TestMain");

		System.out.println("Database created. Clearing (for test purposes).");
		db.clear();
		System.out.println("Creating system objects...");

		Date start = new GregorianCalendar(2016, 1, 10).getTime();
		Date end = new GregorianCalendar(2017, 1, 10).getTime();

		Vendor vendor1 = new Vendor();
		vendor1.setName("Pepe Pérez");
		vendor1.setPassword("pperez");
		vendor1.setUser("pperez");

		Vendor vendor2 = new Vendor();
		vendor2.setName("Paco Porras");
		vendor2.setPassword("p190_%%%");
		vendor2.setUser("pporras");

		db.save(vendor1);
		db.save(vendor2);

		System.out.println("Created vendors " + vendor1.getName() + " and "
				+ vendor2.getName());

		BookingFactory bf1 = new BookingFactory(vendor1);
		BookingFactory bf2 = new BookingFactory(vendor2);

		ImsersoClient client = new ImsersoClient();
		client.setDNI("7691001D");
		client.setName("Pepe");
		client.setSurname("Testclient");
		client.setBirth(start);
		client.setSsNumber(187676713);

		db.save(client);

		System.out.println("Initialized our client " + client.getName());

		System.out.println("Filling database from files...");

		DBFeeder dbf = new DBFeeder();

		System.out.println("Filling hotel data...");
		dbf.saveHotelData("utils/Hoteles.csv");

		System.out.println("Filling travel data...");
		dbf.saveTravelData("utils/ViajesOrganizados.csv");

		System.out.println("Filling IMSERSO travel data...");
		dbf.saveImsersoTravelData("utils/ViajesIMSERSO.csv");

		System.out.println("Done filling data.");
		System.out.println("Starting operation.");

		FlightsProvider fp = ServicesFactory.getServicesFactory()
				.getFlightsProvider();
		List<AirportInfo> airports = fp.queryAirports();
		AirportInfo source = airports.get(0);
		AirportInfo destination = airports.get(1);

		Date flightStart = new GregorianCalendar(2014, 0, 1).getTime();
		Date flightEnd = new GregorianCalendar(2014, 0, 6).getTime();

		System.out.format(
				"Querying flights from %s to %s between %s and %s...\n",
				source.getName(), destination.getName(),
				flightStart.toString(), flightEnd.toString());

		List<String> flights = fp.queryFlights(source.getCode(),
				destination.getCode(), start, end);

		System.out.println("Got flight " + flights.get(0));

		Flight f = new Flight();
		f.setLocalizer(flights.get(0));
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

		fb.book();
		db.save(fb);
		System.out.println("Successfully booked our flight");

		System.out.println("Searching for a hotel in Madrid...");
		Hotel h = db.get(Hotel.class, "city", "Madrid").get(0);
		System.out.println("Found hotel " + h.getName());

		HotelBooking hb = (HotelBooking) bf1.book(h, client, start, end);
		hb.confirm();

		db.save(hb);
		System.out.println("Successfully confirmed our hotel room.");

		System.out.println("Searching for an IMSERSO travel to Mallorca...");
		ImsersoTravel it = db.get(ImsersoTravel.class, "visitedCities",
				"Mallorca").get(0);

		System.out.println("Got " + it.getName() + ": " + it.getDescription());

		ImsersoTravelBooking itb = (ImsersoTravelBooking) bf1.book(it, client,
				start, end);

		itb.confirm();
		db.save(itb);
		System.out.println("Confirmed!");
		itb.cancel();
		db.save(itb);
		System.out.println("Better cancel it. Done!");

		System.out.println("Getting the Mallorca-1 travel...");

		Travel t = db.get(Travel.class, "name", "Mallorca-1").get(0);

		TravelBooking tb = (TravelBooking) bf1.book(t, client, start, end);
		tb.book();
		db.save(tb);
		System.out.println("Retrieved and booked");

		System.out.println("Arranging everything in a packet...");

		Packet p = new Packet();
		p.setClient(client);
		p.add(tb);
		p.add(itb);
		p.add(hb);
		p.add(fb);
		db.save(p);
		System.out.println("Done!");

		System.out.println("The other vendor " + vendor2.getName()
				+ " is doing operations...");
		FlightBooking fb2 = (FlightBooking) bf2.book(f, client, start, end);

		HotelBooking hb2 = (HotelBooking) bf2.book(h, client, start, end);

		hb2.book();
		hb2.cancel();

		ImsersoTravelBooking itb2 = (ImsersoTravelBooking) bf2.book(it, client,
				start, end);

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

		System.out.println("Operations ended. Printing report...");

		StatsReporter reporter = new StatsReporter();

		System.out.println("Number of services we have managed:"
				+ reporter.getTotalServices());
		System.out.println("Booked services:" + reporter.getServicesBooked());
		System.out.println("Canceled services:"
				+ reporter.getServicesCanceled());
		System.out.println("Sold services:" + reporter.getServicesSold());
		System.out.println("Income:" + reporter.getTotalMoneyIn());
		System.out.println("Outcome:" + reporter.getTotalMoneyOut());
		System.out.println("Benefits:" + reporter.getBenefits());
		System.out.println();
		System.out.println("Stats for vendor 1 " + vendor1.getName());
		System.out.println("Number of services managed:"
				+ reporter.getTotalServicesOf(vendor1));
		System.out.println("Booked services:"
				+ reporter.getServicesBookedOf(vendor1));
		System.out.println("Canceled services:"
				+ reporter.getServicesCanceledOf(vendor1));
		System.out.println("Sold services:"
				+ reporter.getServicesSoldOf(vendor1));
		System.out.println("Income:" + reporter.getTotalMoneyInOf(vendor1));
		System.out.println("Outcome:" + reporter.getTotalMoneyOutOf(vendor1));
		System.out.println("Benefits:" + reporter.getBenefitsOf(vendor1));

		db.close();
	}

}
