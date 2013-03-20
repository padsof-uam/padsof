package padsof.services;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Travel extends Service
{
	@DatabaseField
	private String company;

	@DatabaseField
	private String phone;

	@DatabaseField
	private int days;

	@DatabaseField
	private int nights;

	@DatabaseField
	private String start;

	@DatabaseField
	private String departingCity;

	@DatabaseField
	private String visitedCities;

	public Travel()
	{

	}

	public Travel(String name, String company, String phone, double price,
			int days, int nights, String dates, String departure,
			String visited, String description)
	{
		this.name = name;
		this.company = company;
		this.price = price;
		this.phone = phone;
		this.price = price;
		this.days = days;
		this.nights = nights;
		start = dates;
		departingCity = departure;
		visitedCities = visited;
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

	/**
	 * 
	 */

	@Override
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * 
	 */
	@Override
	public String getDescription()
	{
		return description;
	}

	/**
	 * 
	 */
	@Override
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @param start
	 *            the start to set
	 */

	public void setStart(String start)
	{
		this.start = start;
	}

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
	public String getStart()
	{
		return start;
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
