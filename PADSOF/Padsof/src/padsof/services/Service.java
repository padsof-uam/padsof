package padsof.services;

import padsof.db.DBObject;

import com.j256.ormlite.field.DatabaseField;

public abstract class Service extends DBObject
{
	@DatabaseField
	private double cost;
	@DatabaseField
	protected double price;
	@DatabaseField
	protected String name;
	@DatabaseField
	protected String description;

	/**
	 * @return the cost
	 */
	public double getCost()
	{
		return cost;
	}

	/**
	 * @param cost
	 *            the cost to set
	 */
	public void setCost(double cost)
	{
		this.cost = cost;
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
	 * @return the price to pay for booking the service
	 */
	public double getBookingPrice()
	{
		return 0.1 * price;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}
}
