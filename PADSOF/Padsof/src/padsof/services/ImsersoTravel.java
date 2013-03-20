package padsof.services;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class ImsersoTravel extends Service
{
	@DatabaseField
	private int days;

	@DatabaseField
	private int nights;

	@DatabaseField
	private Date start;

	@DatabaseField
	private String departingCity;

	@DatabaseField
	private String visitedCities;

	public ImsersoTravel()
	{

	}

	public ImsersoTravel(String name, double price_client, int days,
			int nights, String start, String departingCity,
			String visitedCities, String description)
	{

		this.name = name;
		price = price_client;
		this.days = days;
		this.nights = nights;
		this.departingCity = departingCity;
		this.visitedCities = visitedCities;
		this.description = description;
	}

	/**
	 * 
	 */
	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public String getDescription()
	{
		return description;
	}

	@Override
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @return the price
	 */
	@Override
	public double getPrice()
	{
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	@Override
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

	@Override
	public double getBookingPrice()
	{
		return 0.1 * price;
	}
}
