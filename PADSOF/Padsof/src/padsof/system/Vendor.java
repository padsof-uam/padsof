package padsof.system;

import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.*;

import padsof.bookings.*;
import padsof.db.*;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author Víctor de Juan Sanz - Guillermo Julián Moreno
 */
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

	/**
	 * Hashes a password with SHA-256
	 * 
	 * @param pass
	 * @return the password hashed to save.
	 * @throws Exception
	 */
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
		hashedPassword = hashPassword(password);
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
		return contains(b);
	}

	// Statistics:
	List<Booking> bookings = null;

	public void refreshBookings()
	{
		bookings = null;
	}

	public List<Booking> getBookings() throws SQLException
	{
		if (bookings != null)
			return bookings;

		bookings = new ArrayList<Booking>();

		bookings.addAll(DBWrapper.getInstance().get(FlightBooking.class,
				"vendor", this));
		bookings.addAll(DBWrapper.getInstance().get(HotelBooking.class,
				"vendor", this));
		bookings.addAll(DBWrapper.getInstance().get(TravelBooking.class,
				"vendor", this));
		bookings.addAll(DBWrapper.getInstance().get(ImsersoTravelBooking.class,
				"vendor", this));

		return bookings;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return getName();
	}	
}
