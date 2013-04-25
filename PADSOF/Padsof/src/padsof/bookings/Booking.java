package padsof.bookings;

import java.util.*;

import padsof.db.DBObject;
import padsof.services.Service;
import padsof.system.*;

import com.j256.ormlite.field.DatabaseField;
/**
 * 
 * @author Víctor de Juan Sanz - Guillermo Julián Moreno
 *
 */
public abstract class Booking extends DBObject
{
	@DatabaseField(foreign = true, foreignAutoCreate = true,
			foreignAutoRefresh = true)
	private Client client;

	@DatabaseField
	private PaymentState state;

	@DatabaseField
	private Date start;

	@DatabaseField
	private Date end;

	@DatabaseField(foreign = true, foreignAutoCreate = true,
			foreignAutoRefresh = true)
	private Vendor vendor;

	@DatabaseField(foreign = true, foreignAutoCreate = true,
			foreignAutoRefresh = true)
	private Packet pertainingPacket;

	/**
	 * @return the pertainingPacket
	 */
	public Packet getPertainingPacket()
	{
		return pertainingPacket;
	}

	/**
	 * @param pertainingPacket
	 *            the pertainingPacket to set
	 */
	public void setPertainingPacket(Packet pertainingPacket)
	{
		this.pertainingPacket = pertainingPacket;
	}

	/**
	 * @return the vendor
	 */
	public Vendor getVendor()
	{
		return vendor;
	}

	public Booking()
	{

	}

	public Booking(Client client, Date start, Date end, Vendor vendor)
	{
		this.client = client;
		state = PaymentState.None;
		this.start = start;
		this.end = end;
		this.vendor = vendor;
	}

	/**
	 * @return the client
	 */
	public Client getClient()
	{
		return client;
	}

	/**
	 * @param client
	 *            the client to set
	 */
	public void setClient(Client client)
	{
		this.client = client;
	}

	/**
	 * @return the state
	 */
	public PaymentState getState()
	{
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(PaymentState state)
	{
		this.state = state;
	}

	/**
	 * @return the start
	 */
	public Date getStart()
	{
		return start;
	}

	/**
	 * @param start
	 *            the start to set
	 */
	public void setStart(Date start)
	{
		this.start = start;
	}

	/**
	 * @return the end
	 */
	public Date getEnd()
	{
		return end;
	}

	/**
	 * @param end
	 *            the end to set
	 */
	public void setEnd(Date end)
	{
		this.end = end;
	}

	/**
	 * @return true if is Payed, false if not
	 */
	public boolean isPayed()
	{
		return state == PaymentState.Payed;
	}
	
	public Vendor getVendorUser()
	{
		return vendor;
	}

	public void setVendorUser(Vendor vendorUser)
	{
		vendor = vendorUser;
	}

	/**
	 * @return the associatedService
	 */
	public abstract Service getAssociatedService();

	/**
	 * Set the booking as booked if its not already booked.
	 * 
	 * @return Payment to make.
	 * @throws Exception if the booking is already book or payed.
	 */
	public double book() throws Exception
	{
		if (getState() != PaymentState.Booked)
		{
			setState(PaymentState.Booked);
			return getBookingPrice();
		}
		else
			throw new UnsupportedOperationException(
					"This service is already booked or payed");
	}

	/**
	 * @return Payment to make
	 */
	public double getBookingPrice()
	{
		return 0.1 * getAssociatedService().getPrice();
	}

	/**
	 * Confirm the booking (if its not booked yet, confirm() books it)
	 * 
	 * @return Payment to make.
	 * @throws InvalidParameterException.
	 * @throws Exception if the booking is already payed.
	 */
	public double confirm() throws Exception
	{
		double retval;
		if (getState() != PaymentState.Payed)
		{
			setState(PaymentState.Payed);
			if (getState() == PaymentState.None)
			{
				book();
				retval = getAssociatedService().getPrice();
			}
			else
				retval = getAssociatedService().getPrice() - getBookingPrice();
			return retval;

		}
		else
			throw new UnsupportedOperationException(
					"This service is already confirmed");
	}

	@Override
	public String toString()
	{
		return "[" + this.state.toString() + "] " + this.getClass().getSimpleName() + ": " + this.getAssociatedService().toString();
	}

	/**
	 * Cancel the booking
	 * @throws UnsupportedOperationException (if its already canceled)
	 */
	public void cancel() 
	{
		if (getState() != PaymentState.None)
			setState(PaymentState.None);
		else
			throw new UnsupportedOperationException(
					"This service is already canceled.");
	}

}
