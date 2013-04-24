package padsof.gui.controllers;

import java.util.*;

import padsof.bookings.*;
import padsof.gui.Application;
import padsof.gui.controllers.utils.Listener;
import padsof.gui.views.FindFlightView;
import padsof.services.Flight;
import padsof.system.Packet;
import es.uam.eps.pads.services.*;
import es.uam.eps.pads.services.flights.*;

public class FindFlightController extends Controller<FindFlightView>
{
	public class AirportInfoWrapper
	{
		public AirportInfo info;

		public String toString()
		{
			return info.getCode() + " (" + info.getCity() + ")";
		}

		public AirportInfoWrapper(AirportInfo info)
		{
			this.info = info;
		}
	}

	public class FlightInfoWrapper
	{
		public FlightInfo info;

		public String dateToString(Date date)
		{
			Calendar departure = new GregorianCalendar();
			departure.setTime(date);

			return departure.get(Calendar.DAY_OF_MONTH) + "/"
					+ (departure.get(Calendar.MONTH) + 1) + " "
					+ departure.get(Calendar.HOUR) + ":"
					+ departure.get(Calendar.MINUTE);
		}

		public String toString()
		{
			return info.getSource() + " (" + dateToString(info.getDeparture())
					+ ") => " + info.getDestination() + "("
					+ dateToString(info.getArrival()) + ") - "
					+ info.getPrice() + "€";
		}

		public FlightInfoWrapper(FlightInfo info)
		{
			this.info = info;
		}
	}

	@Listener("Search")
	public void search()
	{
		Date start, end;
		AirportInfo origin, destination;
		Double maxPrice = -1.0;
		int spots = -1;

		if (view.getDestination() == null || view.getOrigin() == null)
		{
			showError("Necesitamos un aeropuerto de origen y otro de destino.");
			return;
		}

		FlightsProvider fp = ServicesFactory.getServicesFactory()
				.getFlightsProvider();
		origin = view.getOrigin().info;
		destination = view.getDestination().info;

		try
		{
			String max = view.getMaximumPrice();
			String sSpots = view.getPersons();

			if (!max.isEmpty())
				maxPrice = Double.valueOf(max);
			if (!sSpots.isEmpty())
				spots = Integer.valueOf(sSpots);
		}
		catch (NumberFormatException e)
		{
			showError("Formato de número incorrecto.");
			return;
		}

		start = view.getStartDate();
		end = view.getEndDate();

		if (origin.getCode().equals(destination.getCode()))
		{
			showError("Seleccione dos aeropuertos distintos.");
			return;
		}

		if (end.before(start))
		{
			showError("Fecha inválida.");
			return;
		}

		List<FlightInfo> info = new ArrayList<FlightInfo>();
		List<FlightInfo> filtered = new ArrayList<FlightInfo>();

		try
		{
			List<String> flights = fp.queryFlights(origin.getCode(),
					destination.getCode(), start, end);
			for (String f : flights)
				info.add(fp.flightInfo(f));
		}
		catch (InvalidParameterException e)
		{
			showError("Error recuperando los vuelos.");
			return;
		}

		if (maxPrice != -1.0)
			for (FlightInfo f : info)
				if (f.getPrice() > maxPrice)
					filtered.add(f);

		if (spots != -1)
			for (FlightInfo f : info)
				if (f.getAvailableSeats() < spots)
					filtered.add(f);

		for (FlightInfo f : info)
			if (f.getAvailableSeats() == 0)
				filtered.add(f);

		for (FlightInfo f : filtered)
			info.remove(f);

		if (info.size() == 0)
		{
			showError("No se han podido encontrar vuelos con esos parámetros.");
			return;
		}

		List<FlightInfoWrapper> wrapped = new ArrayList<FlightInfoWrapper>();
		for (FlightInfo f : info)
			wrapped.add(new FlightInfoWrapper(f));

		view.setResult(wrapped);
	}

	@Listener("Book")
	public void book()
	{
		FlightInfo flight = view.getSelectedFlight().info;

		BookingFactory factory = new BookingFactory(Application.getInstance()
				.getVendor());
		Booking booking;

		try
		{
			booking = factory.book(new Flight(flight), Application
					.getInstance().getClient(), flight.getDeparture(), flight
					.getArrival());
			double price = booking.book();
			Packet current = Application.getInstance().getPacket();
			current.add(booking);

			showMessage("Reserva realizada. Precio: " + price + "€");
		}
		catch (Exception e)
		{
			showError("No se ha podido guardar la reserva: " + e.getMessage());
			return;
		}

	}

	@Override
	public void setView(FindFlightView view)
	{
		super.setView(view);
		FlightsProvider fp = ServicesFactory.getServicesFactory()
				.getFlightsProvider();
		List<AirportInfo> airports = fp.queryAirports();
		List<AirportInfoWrapper> aw = new ArrayList<AirportInfoWrapper>();

		for (AirportInfo info : airports)
			aw.add(new AirportInfoWrapper(info));
		view.setAirports(aw);
	}
}
