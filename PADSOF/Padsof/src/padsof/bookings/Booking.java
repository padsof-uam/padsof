package padsof.bookings;

import java.util.Date;
import padsof.system.*;

import es.uam.eps.pads.services.InvalidParameterException;

import padsof.services.Service;
import padsof.system.Client;

public abstract class Booking
{
	private Client client;
	private PaymentState state;
	private Date start;
	private Date end;
	private Vendor vendorUser;
	
	public Booking(){
		
	}
	public Booking(Client client, Date start, Date end,Vendor vendor){
		this.client = client;
		this.state = PaymentState.None;
		this.start = start;
		this.end = end;
		this.vendorUser = vendor;
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
	 * 
	 * @return true if is Payed, false if not
	 */
	public boolean checkIfPayed(){
		if (state == PaymentState.Payed) return true;
		return false;
	}

	public Vendor getVendorUser()
	{
		return vendorUser;
	}
	public void setVendorUser(Vendor vendorUser)
	{
		this.vendorUser = vendorUser;
	}
	
	/**
	 * @return the associatedService
	 */
	public abstract Service getAssociatedService();

	/**
	 * Set the booking as booked.
	 * 
	 * @return Payment to make.
	 * @throws Exception 
	 */
	public double book() throws Exception
	{
		if(getState()!=PaymentState.Booked){
			setState(PaymentState.Booked);
			if (!vendorUser.contains(this))
				vendorUser.addBooking(this);
			return getBookingPrice();	
		} else throw new UnsupportedOperationException ("This service is already booked or payed");	
	}
	/**
	 * 
	 * @return Payment to make
	 */
	public double getBookingPrice(){
		return 0.1 * getAssociatedService().getPrice();
	}
	/**
	 * Confirm the booking.
	 * 
	 * @return Payment to make.
	 * @throws InvalidParameterException 
	 * @throws Exception 
	 */
	public double confirm() throws Exception
	{
		double retval;
		if (getState()!=PaymentState.Payed){
			setState(PaymentState.Payed);
			if(getState() == PaymentState.None){
				book();
				retval = getAssociatedService().getPrice(); 
			} else
				retval = getAssociatedService().getPrice()-getBookingPrice();
			return retval;
			
		}
		else throw new UnsupportedOperationException("This service is already confirmed");
	}	
	/**
	 * 
	 * @throws InvalidParameterException
	 */
public void cancel() throws InvalidParameterException{
		if (getState()!= PaymentState.None){
			setState(PaymentState.None);
		}else  throw new UnsupportedOperationException("This service is already cancel");
	}
	
	
}
