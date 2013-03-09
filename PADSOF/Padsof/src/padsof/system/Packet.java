package padsof.system;

import padsof.bookings.*;
import java.util.*;

public class Packet
{
	private boolean isClose = false;
	
	public boolean IsClose(){
		return isClose;
	}
	
	public void closePacket(){
		isClose=true;
	}
	
	/**
	 * 
	 * @param book to add to the packet
	 */
	public void add(Booking book){
		if (!IsClose())
		bookings.add(book);		
	}
	/**
	 * 
	 * @param bookings to add to the packet
	 */
	public void addAll(ArrayList<Booking> bookings){
		if (!IsClose())
		bookings.addAll(bookings);
	}
	/**
	 * get thes start day of the packet
	 * @return the date
	 */
	public Date getStartDay(){
		Date min = bookings.get(0).getStart();
		
		for (Booking aux : bookings){
			System.out.println("ee");
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
	 * close package automatically if possible
	 * @return true if the package was closed or false if is still open
	 */
	public boolean closeAutomatically(){
		if (!IsClose()){
		GregorianCalendar c = new GregorianCalendar();
		c.add(Calendar.DAY_OF_MONTH, 1);
		Date d = c.getTime();
		boolean retval = checkIfAllPayed();
		if(!retval && getStartDay().after(d)){
			closePacket();
		}
		return retval;
		} else return true;
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
