package padsof.services;

import java.util.*;

public class ImsersoTravel extends Service
{
	private double price;
	private int days;
	private int nights;
	private Date start;
	private String departingCity;
	private List<String> visitedCities;

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
	public List<String> getVisitedCities()
	{
		return visitedCities;
	}

	/**
	 * @param visitedCities
	 *            the visitedCities to set
	 */
	public void setVisitedCities(List<String> visitedCities)
	{
		this.visitedCities = visitedCities;
	}
}
