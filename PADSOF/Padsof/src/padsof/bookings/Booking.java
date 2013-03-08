package padsof.bookings;

import java.util.Date;

import padsof.services.Service;
import padsof.system.Client;

public abstract class Booking
{
	private Client client;
	private PaymentState state;
	private Date start;
	private Date end;
	public Booking(){
		
	}
	public Booking(Client client, Date start, Date end){
		this.client = client;
		this.state = PaymentState.None;
		this.start = start;
		this.end = end;
	}
	/**
	 * @return the client
	 */
	public Client getClient()
	{
		return client;
	}

	/**
	 * @param client the client to set
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
	 * @return the associatedService
	 */
	public abstract Service getAssociatedService();

	/**
	 * Set the booking as booked.
	 * 
	 * @return Payment to make.
	 */
	public abstract int book();

	/**
	 * Confirm the booking.
	 * 
	 * @return Payment to make.
	 */
	public abstract int confirm();
}
