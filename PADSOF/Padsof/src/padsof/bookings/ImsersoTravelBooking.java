/**
 * 
 */
package padsof.bookings;

import java.util.Date;

import padsof.services.ImsersoTravel;
import padsof.system.*;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author Víctor de Juan Sanz - Guillermo Julián Moreno
 */

@DatabaseTable
public class ImsersoTravelBooking extends Booking
{
	@DatabaseField
	private String couponCode;

	@DatabaseField(foreign = true, foreignAutoCreate = true,
			foreignAutoRefresh = true)
	private ImsersoTravel associatedService;

	@DatabaseField(foreign = true, foreignAutoCreate = true,
			foreignAutoRefresh = true)
	private ImsersoClient iClient; // Forget the inherited Client field for DB
									// purposes.

	public ImsersoTravelBooking(ImsersoTravel service, ImsersoClient client,
			Date start, Date end, Vendor vendor)
	{
		super(client, start, end, vendor);
		iClient = client;
		associatedService = service;
	}

	public ImsersoTravelBooking()
	{
	}

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

	@Override
	public Client getClient()
	{
		return iClient;
	}

	public void setClient(ImsersoClient client)
	{
		iClient = client;
	}

}
