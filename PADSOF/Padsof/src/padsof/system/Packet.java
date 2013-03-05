package padsof.system;

import padsof.bookings.*;
import java.util.*;

public class Packet
{
	private Client client;
	private ArrayList<Booking> bookings = new ArrayList<Booking>();
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
	 * @return the bookings
	 */
	public List<Booking> getBookings()
	{
		return bookings;
	}
}
