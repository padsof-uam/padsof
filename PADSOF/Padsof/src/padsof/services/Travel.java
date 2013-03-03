package padsof.services;

import java.util.*;

public class Travel extends Service
{
	private String company;
	private String phone;
	private double price;
	private int days;
	private int nights;
	private Date start;
	private String departingCity;
	private String visitedCities;

	/**
	 * @return the company
	 */
	public String getCompany()
	{
		return company;
	}

	/**
	 * @param company
	 *            the company to set
	 */
	public void setCompany(String company)
	{
		this.company = company;
	}

	/**
	 * @return the phone
	 */
	public String getPhone()
	{
		return phone;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(String phone)
	{
		this.phone = phone;
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
}
