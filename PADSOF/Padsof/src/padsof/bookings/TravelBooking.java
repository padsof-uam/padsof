/**
 * 
 */
package padsof.bookings;

import java.util.Date;

import padsof.services.Travel;
import padsof.system.*;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author gjulianm
 */

@DatabaseTable
public class TravelBooking extends Booking
{
	@DatabaseField(foreign = true, foreignAutoCreate = true,
			foreignAutoRefresh = true)
	private Travel associatedTravel;

	public TravelBooking(Travel service, Client client, Date start, Date end,
			Vendor vendor)
	{
		super(client, start, end, vendor);
		associatedTravel = service;
	}

	public TravelBooking()
	{
	}

	/*
	 * (non-Javadoc)
	 * @see padsof.bookings.Booking#getAssociatedService()
	 */
	@Override
	public Travel getAssociatedService()
	{
		return associatedTravel;
	}

	public void setAssociatedService(Travel service)
	{
		associatedTravel = service;
	}

}
