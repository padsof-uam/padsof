/**
 * 
 */
package padsof.bookings;

import java.util.Date;

import padsof.services.Hotel;
import padsof.system.*;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author Víctor de Juan Sanz - Guillermo Julián Moreno
 */
@DatabaseTable
public class HotelBooking extends Booking
{
	@DatabaseField(foreign = true, foreignAutoCreate = true,
			foreignAutoRefresh = true)
	private Hotel associatedService;
	@DatabaseField
	private double price;
	@DatabaseField
	private int simples;
	@DatabaseField
	private int dobles;
	
	@DatabaseField
	private int triples;
	
	@DatabaseField
	private double cost;

	/**
	 * @return the simples
	 */
	public int getSimples()
	{
		return simples;
	}

	/**
	 * @param simples
	 *            the simples to set
	 */
	public void setSimples(int simples)
	{
		this.simples = simples;
	}

	/**
	 * @return the dobles
	 */
	public int getDobles()
	{
		return dobles;
	}

	/**
	 * @param dobles
	 *            the dobles to set
	 */
	public void setDobles(int dobles)
	{
		this.dobles = dobles;
	}

	/**
	 * @return the triples
	 */
	public int getTriples()
	{
		return triples;
	}

	/**
	 * @param triples
	 *            the triples to set
	 */
	public void setTriples(int triples)
	{
		this.triples = triples;
	}

	public HotelBooking(Hotel service, Client client, Date start, Date end,
			Vendor vendor)
	{
		super(client, start, end, vendor);
		associatedService = service;
	}

	public HotelBooking()
	{
	}

	public void setPrice(double cost)
	{
		this.price = cost;
	}

	@Override
	public double getBookingPrice()
	{
		return 0.1 * price;
	}

	@Override
	public double confirm() throws Exception
	{
		double retval;
		if (getState() != PaymentState.Payed)
		{
			setState(PaymentState.Payed);
			if (getState() == PaymentState.None)
			{
				this.book();
				retval = price;
			}
			else
				retval = price - getBookingPrice();
			return retval;

		}
		else
			throw new UnsupportedOperationException(
					"This service is already confirmed");
	}

	public double getPrice()
	{
		return price;
	}

	/*
	 * (non-Javadoc)
	 * @see padsof.bookings.Booking#getAssociatedService()
	 */
	@Override
	public Hotel getAssociatedService()
	{
		return associatedService;
	}

	/**
	 * Sets the associated service.
	 * 
	 * @param service
	 *            Service.
	 */
	public void setAssociatedService(Hotel service)
	{
		associatedService = service;
	}

	public void setCost(double cost)
	{
		this.cost = cost;
	}

	@Override
	public double getCost()
	{
		return cost;
	}
}
