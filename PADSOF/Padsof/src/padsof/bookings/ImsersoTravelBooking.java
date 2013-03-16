/**
 * 
 */
package padsof.bookings;

import java.util.Date;


import padsof.services.*;
import padsof.system.Client;
import padsof.system.Vendor;
/**
 * @author gjulianm
 *
 */
public class ImsersoTravelBooking extends Booking
{
	private String couponCode;
	private ImsersoTravel associatedService;

	public ImsersoTravelBooking(ImsersoTravel service, Client client,
			Date start, Date end, Vendor vendor)
	{
		super(client, start, end,vendor);
		this.associatedService = service;
	}

	/* (non-Javadoc)
	 * @see padsof.bookings.Booking#getAssociatedService()
	 */
	@Override
	public ImsersoTravel getAssociatedService()
	{
		return associatedService;
	}
	
	public void setAssociatedService(ImsersoTravel service)
	{
		associatedService = service;
	}

	/**
	 * @return the couponCode
	 */
	public String getCouponCode()
	{
		return couponCode;
	}

	/**
	 * @param couponCode the couponCode to set
	 */
	public void setCouponCode(String couponCode)
	{
		this.couponCode = couponCode;
	}

}
