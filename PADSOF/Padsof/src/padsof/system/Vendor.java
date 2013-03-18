package padsof.system;

import java.util.ArrayList;
import java.util.List;
import padsof.bookings.Booking;
import padsof.bookings.PaymentState;
public class Vendor
{
	private String name;
	private String user;
	private String password;
	private boolean isAdmin;
	private List<Booking> bookings;
	
	public Vendor (String name, String user, String password){
		this.name = name;
		this.user = user;
		this.password = password;
		isAdmin=false;
		bookings = new ArrayList<Booking>();
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

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public boolean IsAdmin(){
		return isAdmin;
	}
	
	//TODO: un admin puede crear nuevos admins? 
	
	public void addBooking(Booking book){
		bookings.add(book);
	}
	
	public void becomeAdmin(){
		isAdmin = true;
	}
	/**
	 * 
	 * @param name,user,password
	 * @return a new Vendor
	 * @throws UnsupportedOperationException
	 */
	public Vendor createVendor(String name, String user, String password) throws UnsupportedOperationException{
		if (IsAdmin()){
			return new Vendor (name,user,password);
		} else throw new UnsupportedOperationException("You are not admin");
	}
	/**
	 * 
	 * @param vendor to change the password
	 * @param newPass to be setted as vendor's password
	 */
	public void changePassword(Vendor vendor,String newPass) throws UnsupportedOperationException{
		if (IsAdmin()){
			if (!vendor.IsAdmin()){
				setPassword(newPass);
			
			}throw new UnsupportedOperationException("You can't change admin's password here.");
		}else throw new UnsupportedOperationException("You are not admin.");
	}	
	
	public boolean contains(Booking b){
		return this.contains(b);		
	}
	//Estadistics: 
	public int getNumberBookings(){
		int i=0;
		for (Booking iterator : this.bookings)
			if (iterator.getState() == PaymentState.Booked)
				i++;
		return i;
	}
	public int getNumberConfirmed(){
		int i=0;
		for (Booking iterator : this.bookings)
			if (iterator.getState() == PaymentState.Payed)
				i++;
		return i;
	}
	public int getNumberCanceled(){
		int i=0;
		for (Booking iterator : this.bookings)
			if (iterator.getState() == PaymentState.None)
				i++;
		return i;
	}
	public double getMoneyGot(){
		int i=0;
		for (Booking iterator : this.bookings)
			if (iterator.getState() == PaymentState.Payed)
				i+= iterator.getAssociatedService().getPrice();
			else if( iterator.getState()==PaymentState.Booked)
				i+= iterator.getAssociatedService().getBookingPrice();
		return i;
	}
	
}
