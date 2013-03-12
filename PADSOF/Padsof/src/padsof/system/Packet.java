package padsof.system;

import padsof.bookings.*;
import java.util.*;

public class Packet
{
	private Client client;
	private ArrayList<Booking> bookings = new ArrayList<Booking>();
	private boolean isClose = false;
	
	
	
	/**
	 * 
	 * @param book to add to the packet
	 */
	public void add(Booking book){
		if (!IsClose())
		bookings.add(book);		
	}
	
	/**
	 * get thes start day of the packet
	 * @return the date
	 */
	public Date getStartDay(){
		Date min = bookings.get(0).getStart();
		
		for (Booking aux : bookings){
			if (min.compareTo(aux.getStart())>=0){
				min = aux.getStart();
			}
		}
		return min;
	}
	/**
	 * get the last day of the packet
	 * @return the date
	 */
	public Date getEndDay(){
	Date max = bookings.get(0).getStart();
		
		for (Booking aux : bookings){
			if (max.compareTo(aux.getEnd())<=0)
				max = aux.getEnd();
		}
		return max;		
	}
	

	/**
	 * checks if all the bookings of the packet are payed.
	 * @return true if all bookings are payed, false if not.
	 */
	public boolean checkIfAllPayed(){
		
		for (Booking aux : bookings){
			if (!aux.checkIfPayed())
				return false;
		}
		return true;
	}

	/**
	 * close package automatically if possible
	 * @return true if the package was closed or false if is still open
	 */
	public boolean closeAutomatically(){
		if (!IsClose()){
		GregorianCalendar c = new GregorianCalendar();
		c.add(Calendar.DAY_OF_MONTH, 1);
		Date d = c.getTime();
		if(getStartDay().after(d)){
			closePacket();
			return true;
		}
		return false;
		} else return true;
	}
	
	
	// Setters y getters
	
	/**
	 * 
	 * @return if it's closed or not
	 */
	public boolean IsClose(){
		return isClose;
	}
	
	/**
	 * Close the packet
	 */
	public void closePacket(){
		isClose=true;
	}
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
