package padsof.system;

import java.sql.SQLException;
import java.util.*;

import padsof.bookings.*;
import padsof.db.DBWrapper;

/**
 * @author Víctor de Juan Sanz - Guillermo Julián Moreno
 */
public class StatsReporter
{
	private List<Booking> bookings;
	private Date minDate = new Date(Long.MIN_VALUE);
	private Date maxDate = new Date(Long.MAX_VALUE);
	
	/**
	 * @return the maxDate
	 */
	public Date getMaxDate()
	{
		return maxDate;
	}


	/**
	 * @param maxDate the maxDate to set
	 */
	public void setMaxDate(Date maxDate)
	{
		this.maxDate = maxDate;
	}

	/**
	 * @return the minDate
	 */
	public Date getMinDate()
	{
		return minDate;
	}


	/**
	 * @param minDate the minDate to set
	 */
	public void setMinDate(Date minDate)
	{
		this.minDate = minDate;
	}


	private List<Booking> filterDate(List<Booking> bookings)
	{
		ArrayList<Booking> filtered = new ArrayList<Booking>();
		
		for(Booking b : bookings)
			if(b.getStart().after(minDate) && b.getEnd().before(maxDate))
				filtered.add(b);
		
		return filtered;
	}
	
	public List<Booking> getAllBookings() throws SQLException
	{
		if (bookings == null)
			refreshBookings();

		return bookings;
	}

	
	/**
	 * @param bookings
	 * @param state
	 * @return all the bookings of the param bookings that has state equals to
	 *         the state given
	 */
	public List<Booking> filterState(List<Booking> bookings, PaymentState state)
	{
		ArrayList<Booking> lst = new ArrayList<Booking>();

		for (Booking b : filterDate(bookings))
			if (b.getState() == state)
				lst.add(b);

		return lst;
	}

	public void refreshBookings() throws SQLException
	{
		bookings = new ArrayList<Booking>();
		bookings.addAll(DBWrapper.getInstance().getAll(FlightBooking.class));
		bookings.addAll(DBWrapper.getInstance().getAll(HotelBooking.class));
		bookings.addAll(DBWrapper.getInstance().getAll(TravelBooking.class));
		bookings.addAll(DBWrapper.getInstance().getAll(ImsersoTravelBooking.class));
	}

	public double getBenefits() throws SQLException
	{
		return getTotalMoneyIn() - getTotalMoneyOut();
	}

	/**
	 * @param vendor
	 * @return the benefits a vendor has made.
	 * @throws SQLException
	 */
	public double getBenefitsOf(Vendor vendor) throws SQLException
	{
		return getTotalMoneyInOf(vendor) - getTotalMoneyOutOf(vendor);
	}

	public double getTotalMoneyIn() throws SQLException
	{
		double money = 0;

		for (Booking b : filterDate(getAllBookings()))
			if (b.isPayed())
				money += b.getAssociatedService().getPrice();
			else if (b.getState() == PaymentState.Booked)
				money += b.getAssociatedService().getBookingPrice();

		return money;
	}

	public double getTotalMoneyInOf(Vendor vendor) throws SQLException
	{
		double money = 0;

		for (Booking b : filterDate(vendor.getBookings()))
			if (b.isPayed())
				money += b.getAssociatedService().getPrice();
			else if (b.getState() == PaymentState.Booked)
				money += b.getAssociatedService().getBookingPrice();

		return money;
	}

	public double getTotalMoneyOut() throws SQLException
	{
		double money = 0;

		for (Booking b : filterDate(getAllBookings()))
			if (b.getState() != PaymentState.None)
				money += b.getAssociatedService().getPrice();

		return money;
	}

	public double getTotalMoneyOutOf(Vendor vendor) throws SQLException
	{
		double money = 0;

		for (Booking b : filterDate(vendor.getBookings()))
			if (b.getState() != PaymentState.None)
				money += b.getAssociatedService().getPrice();

		return money;
	}

	/**
	 * @return the number of services.
	 * @throws SQLException
	 */
	public int getTotalServices() throws SQLException
	{
		return getAllBookings().size();
	}

	/**
	 * @param vendor
	 * @return the number of services booked,payed or canceled of the vendor
	 *         given as param.
	 * @throws SQLException
	 */
	public int getTotalServicesOf(Vendor vendor) throws SQLException
	{
		return vendor.getBookings().size();
	}

	public int getServicesSold() throws SQLException
	{
		return filterState(getAllBookings(), PaymentState.Payed).size();
	}

	public int getServicesSoldOf(Vendor vendor) throws SQLException
	{
		return filterState(vendor.getBookings(), PaymentState.Payed).size();
	}

	public int getServicesBooked() throws SQLException
	{
		return filterState(getAllBookings(), PaymentState.Booked).size();
	}

	public int getServicesBookedOf(Vendor vendor) throws SQLException
	{
		return filterState(vendor.getBookings(), PaymentState.Booked).size();
	}

	public int getServicesCanceled() throws SQLException
	{
		return filterState(getAllBookings(), PaymentState.None).size();
	}

	public int getServicesCanceledOf(Vendor vendor) throws SQLException
	{
		return filterState(vendor.getBookings(), PaymentState.None).size();
	}
}
