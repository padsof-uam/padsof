package padsof.system;

import padsof.bookings.*;
import java.util.*;

public class Packet
{
	/**
	 * 
	 * @param book to add to the packet
	 */
	public void add(Booking book){
		bookings.add(book);		
	}
	/**
	 * 
	 * @param bookings to add to the packet
	 */
	public void addAll(ArrayList<Booking> bookings){
		bookings.addAll(bookings);
	}
	/**
	 * get thes start day of the packet
	 * @return the date
	 */
	public Date getStartDay(){
		Date min = bookings.get(0).getStart();
		
		for (Booking aux : bookings){
			if (min.compareTo(aux.getStart())<0){
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
			if (max.compareTo(aux.getStart())>0)
				max = aux.getStart();
		}
		return max;		
	}
	/**
	 * checks if all the bookings of the packet are payed.
	 * @return true if all bookings are payed, false if not.
	 */
	public boolean checkIfAllPayed(){
		for (Booking aux : bookings){
			if (aux.checkIfPayed())
				return false;
		}
		return true;
	}
	
	/**
	 * close the packet
	 */
	public void closePacket(){
		
	}
	/**
	 * close package automatically if possible
	 * @return true if the package was closed or false if is still open
	 */
	public boolean closeAutomatically(){
		GregorianCalendar c = new GregorianCalendar();
		c.add(Calendar.DAY_OF_MONTH, 1);
		Date d = c.getTime();
		boolean retval = checkIfAllPayed();
		if(!retval && getStartDay().after(d)){
			closePacket();
		}
		return retval;
	}
	
	
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
