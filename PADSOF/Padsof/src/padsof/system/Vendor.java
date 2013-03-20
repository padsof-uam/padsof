package padsof.system;

import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import padsof.bookings.Booking;
import padsof.bookings.FlightBooking;
import padsof.bookings.HotelBooking;
import padsof.bookings.ImsersoTravelBooking;
import padsof.bookings.PaymentState;
import padsof.bookings.TravelBooking;
import padsof.db.DBObject;
import padsof.db.DBWrapper;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Vendor extends DBObject
{
	@DatabaseField
	private String name;

	@DatabaseField
	private String user;

	@DatabaseField
	private String hashedPassword;

	@DatabaseField
	private boolean isAdmin;

	public Vendor(String name, String user, String password) throws Exception
	{
		this.name = name;
		this.user = user;
		setPassword(password);
		isAdmin = false;
	}

	public Vendor()
	{
		
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getUser()
	{
		return user;
	}

	public void setUser(String user)
	{
		this.user = user;
	}

	private String hashPassword(String pass) throws Exception
	{
		MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
		sha256.update(pass.getBytes("UTF-8"));
		return new String(sha256.digest(), "UTF-8");
	}
	
	public boolean checkPassword(String pass) throws Exception
	{
		return hashPassword(pass).equals(hashedPassword);
	}

	public void setPassword(String password) throws Exception
	{
		this.hashedPassword = hashPassword(password);
	}

	public boolean IsAdmin()
	{
		return isAdmin;
	}
	
	public void becomeAdmin()
	{
		isAdmin = true;
	}

	public boolean contains(Booking b)
	{
		return this.contains(b);
	}

	// Statistics:
	List<Booking> bookings = null;
	
	public void refreshBookings()
	{
		bookings = null;
	}
	
	public List<Booking> getBookings() throws SQLException
	{
		if(bookings != null)
			return bookings;
		
		bookings = new ArrayList<Booking>();
		
		bookings.addAll(DBWrapper.getInstance().get(FlightBooking.class, "Vendor", this));
		bookings.addAll(DBWrapper.getInstance().get(HotelBooking.class, "Vendor", this));
		bookings.addAll(DBWrapper.getInstance().get(TravelBooking.class, "Vendor", this));
		bookings.addAll(DBWrapper.getInstance().get(ImsersoTravelBooking.class, "Vendor", this));
		
		return bookings;
	}
	
	public int getNumberBookings() throws SQLException
	{
		int i = 0;
		for (Booking iterator : getBookings())
			if (iterator.getState() == PaymentState.Booked)
				i++;
		return i;
	}

	public int getNumberConfirmed() throws SQLException
	{
		int i = 0;
		for (Booking iterator : getBookings())
			if (iterator.getState() == PaymentState.Payed)
				i++;
		return i;
	}

	public int getNumberCanceled() throws SQLException
	{
		int i = 0;
		for (Booking iterator : getBookings())
			if (iterator.getState() == PaymentState.None)
				i++;
		return i;
	}

	public double getMoneyGot() throws SQLException
	{
		int i = 0;
		for (Booking iterator : getBookings())
			if (iterator.getState() == PaymentState.Payed)
				i += iterator.getAssociatedService().getPrice();
			else if (iterator.getState() == PaymentState.Booked)
				i += iterator.getAssociatedService().getBookingPrice();
		return i;
	}

}
