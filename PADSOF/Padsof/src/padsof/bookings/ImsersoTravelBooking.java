/**
 * 
 */
package padsof.bookings;

import padsof.services.*;

/**
 * @author gjulianm
 *
 */
public class ImsersoTravelBooking extends Booking
{
	private String couponCode;
	private ImsersoTravel associatedService;

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

	/* (non-Javadoc)
	 * @see padsof.bookings.Booking#book()
	 */
	@Override
	public int book()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see padsof.bookings.Booking#confirm()
	 */
	@Override
	public int confirm()
	{
		// TODO Auto-generated method stub
		return 0;
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
