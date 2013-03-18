package padsof.services;

import java.util.*;

public class ImsersoTravel extends Service
{
	private String name;
	private double price;
	private int days;
	private int nights;
	private Date start;
	private String departingCity;
	private String visitedCities;
	private String description;


	
	public ImsersoTravel(){

	}
	public ImsersoTravel(String name,double price_client, int days, int nights, 
			String start, String departingCity, String visitedCities,String description){
	
	this.name = name;
	this.price = price_client;
	this.days = days;
	this.nights = nights;
	this.departingCity = departingCity;
	this.visitedCities = visitedCities;
	this.description = description;
	}
	
	/**
	 * 
	 */
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	/**
	 * @return the price
	 */
	public double getPrice()
	{
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(double price)
	{
		this.price = price;
	}

	/**
	 * @return the days
	 */
	public int getDays()
	{
		return days;
	}

	/**
	 * @param days
	 *            the days to set
	 */
	public void setDays(int days)
	{
		this.days = days;
	}

	/**
	 * @return the nights
	 */
	public int getNights()
	{
		return nights;
	}

	/**
	 * @param nights
	 *            the nights to set
	 */
	public void setNights(int nights)
	{
		this.nights = nights;
	}

	/**
	 * @return the start
	 */
	public Date getStart()
	{
		return start;
	}

	/**
	 * @param start
	 *            the start to set
	 */
	public void setStart(Date start)
	{
		this.start = start;
	}

	/**
	 * @return the departingCity
	 */
	public String getDepartingCity()
	{
		return departingCity;
	}

	/**
	 * @param departingCity
	 *            the departingCity to set
	 */
	public void setDepartingCity(String departingCity)
	{
		this.departingCity = departingCity;
	}

	/**
	 * @return the visitedCities
	 */
	public String getVisitedCities()
	{
		return visitedCities;
	}

	/**
	 * @param visitedCities
	 *            the visitedCities to set
	 */
	public void setVisitedCities(String visitedCities)
	{
		this.visitedCities = visitedCities;
	}
	
	public double getBookingPrice(){
		return 0.1*price;
	}
}
