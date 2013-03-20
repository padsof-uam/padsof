/**
 * 
 */
package padsof.bookings;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import padsof.services.*;
import padsof.system.Client;
import padsof.system.Vendor;

/**
 * @author gjulianm
 */

@DatabaseTable
public class ImsersoTravelBooking extends Booking
{
	@DatabaseField
	private String couponCode;

	@DatabaseField(foreign = true, foreignAutoCreate = true,
			foreignAutoRefresh = true)
	private ImsersoTravel associatedService;

	public ImsersoTravelBooking(ImsersoTravel service, Client client,
			Date start, Date end, Vendor vendor)
	{
		super(client, start, end, vendor);
		this.associatedService = service;
	}

	public ImsersoTravelBooking(){}
	
	/*
	 * (non-Javadoc)
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
	 * @param couponCode
	 *            the couponCode to set
	 */
	public void setCouponCode(String couponCode)
	{
		this.couponCode = couponCode;
	}

}
