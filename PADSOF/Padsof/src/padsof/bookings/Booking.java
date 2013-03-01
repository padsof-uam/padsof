package padsof.bookings;

import java.util.Date;

import padsof.services.Service;

public abstract class Booking {
	String name;
	String surname;
	String DNI;
	PaymentState state;
	Date start;
	Date end;
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}
	/**
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}
	/**
	 * @return the dNI
	 */
	public String getDNI() {
		return DNI;
	}
	/**
	 * @param dNI the dNI to set
	 */
	public void setDNI(String dNI) {
		DNI = dNI;
	}
	/**
	 * @return the state
	 */
	public PaymentState getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(PaymentState state) {
		this.state = state;
	}
	/**
	 * @return the start
	 */
	public Date getStart() {
		return start;
	}
	/**
	 * @param start the start to set
	 */
	public void setStart(Date start) {
		this.start = start;
	}
	/**
	 * @return the end
	 */
	public Date getEnd() {
		return end;
	}
	/**
	 * @param end the end to set
	 */
	public void setEnd(Date end) {
		this.end = end;
	}
	/**
	 * @return the associatedService
	 */
	public abstract Service getAssociatedService();
	
	/**
	 * Set the booking as booked.
	 * @return	Payment to make.
	 */
	public abstract int book();
	
	/**
	 * Confirm the booking.	
	 * @return	Payment to make.
	 */
	public abstract int confirm();
}
