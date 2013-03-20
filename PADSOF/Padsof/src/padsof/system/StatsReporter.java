package padsof.system;

import java.sql.SQLException;
import java.util.*;

import padsof.bookings.*;
import padsof.db.DBWrapper;

public class StatsReporter
{
	List<Booking> bookings;

	public List<Booking> getAllBookings() throws SQLException
	{
		if (bookings == null)
			refreshBookings();

		return bookings;
	}

	public List<Booking> filterState(List<Booking> bookings, PaymentState state)
	{
		ArrayList<Booking> lst = new ArrayList<Booking>();

		for (Booking b : bookings)
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
		bookings.addAll(DBWrapper.getInstance().getAll(
				ImsersoTravelBooking.class));
	}

	public double getBenefits() throws SQLException
	{
		return getTotalMoneyIn() - getTotalMoneyOut();
	}

	public double getBenefitsOf(Vendor vendor) throws SQLException
	{
		return getTotalMoneyInOf(vendor) - getTotalMoneyOutOf(vendor);
	}

	public double getTotalMoneyIn() throws SQLException
	{
		double money = 0;

		for (Booking b : getAllBookings())
			if (b.isPayed())
				money += b.getAssociatedService().getPrice();
			else if (b.getState() == PaymentState.Booked)
				money += b.getAssociatedService().getBookingPrice();

		return money;
	}

	public double getTotalMoneyInOf(Vendor vendor) throws SQLException
	{
		double money = 0;

		for (Booking b : vendor.getBookings())
			if (b.isPayed())
				money += b.getAssociatedService().getPrice();
			else if (b.getState() == PaymentState.Booked)
				money += b.getAssociatedService().getBookingPrice();

		return money;
	}

	public double getTotalMoneyOut() throws SQLException
	{
		double money = 0;

		for (Booking b : getAllBookings())
			if (b.getState() != PaymentState.None)
				money += b.getAssociatedService().getPrice();

		return money;
	}

	public double getTotalMoneyOutOf(Vendor vendor) throws SQLException
	{
		double money = 0;

		for (Booking b : vendor.getBookings())
			if (b.getState() != PaymentState.None)
				money += b.getAssociatedService().getPrice();

		return money;
	}

	public int getTotalServices() throws SQLException
	{
		return getAllBookings().size();
	}

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
