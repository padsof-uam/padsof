package padsof.bookings;

import java.sql.SQLException;
import java.util.Date;

import padsof.db.DBWrapper;
import padsof.services.Flight;
import padsof.system.*;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.*;
import com.j256.ormlite.table.DatabaseTable;

import es.uam.eps.pads.services.*;
import es.uam.eps.pads.services.flights.FlightsProvider;

/**
 * @author Víctor de Juan Sanz - Guillermo Julián Moreno
 */

@DatabaseTable
public class FlightBooking extends Booking
{
	@DatabaseField
	private String bookingLocalizer;
	private FlightsProvider fp = ServicesFactory.getServicesFactory()
			.getFlightsProvider();

	@ForeignCollectionField(eager = true)
	private ForeignCollection<Passenger> passengers;

	public FlightBooking(Flight service, Client client, Date start, Date end,
			Vendor vendor)
	{
		super(client, start, end, vendor);
		associatedFlight = service;
	}

	/**
	 * @return the passengers
	 */
	public ForeignCollection<Passenger> getPassengers()
	{
		return passengers;
	}

	public void addPassenger(Passenger passenger)
			throws IllegalArgumentException, IllegalAccessException,
			SQLException
	{
		passenger.setFlight(this);
		DBWrapper.getInstance().save(this);
		DBWrapper.getInstance().save(passenger);
	}

	public void removePassenger(Passenger passenger)
			throws IllegalArgumentException, IllegalAccessException,
			SQLException
	{
		if (equals(passenger.getFlight()))
		{
			passenger.setFlight(null);
			DBWrapper.getInstance().save(this);
			DBWrapper.getInstance().save(passenger);
		}
	}

	@DatabaseField(foreign = true, foreignAutoCreate = true,
			foreignAutoRefresh = true)
	private Flight associatedFlight;

	/**
	 * Gets the associated service.
	 * 
	 * @see padsof.bookings.Booking#getAssociatedService()
	 */
	@Override
	public Flight getAssociatedService()
	{
		return associatedFlight;
	}

	/**
	 * Sets the associated service
	 * 
	 * @param service
	 *            the service
	 */
	public void setAssociatedService(Flight service)
	{
		associatedFlight = service;
	}

	public String getBookingLocalizer()
	{
		return bookingLocalizer;
	}

	public FlightBooking()
	{
	}

	@Override
	public double book() throws Exception
	{
		if (getState() != PaymentState.Booked)
		{
			bookingLocalizer = fp.book(getAssociatedService().getLocalizer(),
					getClient().getName());
			setState(PaymentState.Booked);
			return getBookingPrice();
		}
		else
			throw new UnsupportedOperationException(
					"This service is already booked or confirmed");
	}

	@Override
	public void cancel()
	{
		if (getState() != PaymentState.None)
		{
			try
			{
				fp.cancel(bookingLocalizer);
			}
			catch (InvalidParameterException e)
			{
				// throw new UnsupportedOperationException(e.getMessage());
			}
			setState(PaymentState.None);
		}
		else
			throw new UnsupportedOperationException(
					"This service is not booked nor confirmed");

	}

	@Override
	public double confirm() throws Exception
	{
		PaymentState state = getState();
		double retval = 0;
		if (state != PaymentState.Payed)
		{
			fp.confirm(bookingLocalizer);
			if (state == PaymentState.Booked)
				retval = getAssociatedService().getPrice() - getBookingPrice();
			else if (state == PaymentState.None)
			{
				// To set the value of bookingLocalizer
				book();
				retval = getAssociatedService().getPrice();
			}
			setState(PaymentState.Payed);
			return retval;
		}
		else
			throw new UnsupportedOperationException(
					"This service is already confirmed");
	}

}
