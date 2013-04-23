package padsof.services;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Hotel extends Service
{
	@DatabaseField
	private String country;

	@DatabaseField
	private String city;

	@DatabaseField
	private String phone;

	@DatabaseField
	private String address;

	@DatabaseField
	private String postalCode;

	@DatabaseField
	private String category;

	@DatabaseField
	private double simplePrice;

	@DatabaseField
	private double doublePrice;

	@DatabaseField
	private double triplePrice;

	@DatabaseField
	private double supplement;

	@DatabaseField
	private boolean hasBreakfast;

	@DatabaseField
	private double mpSupplement;

	@DatabaseField
	private double pcSupplement;

	@DatabaseField
	private String services;

	public Hotel()
	{

	}

	public Hotel(String country, String city, String name, String phone,
			String address, String postalcode, String category, double simple,
			double doublep, double triple, double supplement,
			double mpSupplement, double pcSupplement, String services)
	{
		this.country = country;
		this.city = city;
		this.name = name;
		this.phone = phone;
		this.address = address;
		postalCode = postalcode;
		this.category = category;
		simplePrice = simple;
		doublePrice = doublep;
		triplePrice = triple;
		this.supplement = supplement;
		if (supplement == 0)
			hasBreakfast = false;
		else
			hasBreakfast = true;
		this.mpSupplement = mpSupplement;
		this.pcSupplement = pcSupplement;
		this.services = services;
	}

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

	public String getServices()
	{
		return services;
	}

	public void setServices(String services)
	{
		this.services = services;
	}

	/**
	 * @return the country
	 */
	public String getCountry()
	{
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country)
	{
		this.country = country;
	}

	/**
	 * @return the city
	 */
	public String getCity()
	{
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city)
	{
		this.city = city;
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
	 * @return the address
	 */
	public String getAddress()
	{
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address)
	{
		this.address = address;
	}

	/**
	 * @return the postalCode
	 */
	public String getPostalCode()
	{
		return postalCode;
	}

	/**
	 * @param postalCode
	 *            the postalCode to set
	 */
	public void setPostalCode(String postalCode)
	{
		this.postalCode = postalCode;
	}

	/**
	 * @return the category
	 */
	public String getCategory()
	{
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(String category)
	{
		this.category = category;
	}

	/**
	 * @return the simplePrice
	 */
	public double getSimplePrice()
	{
		return simplePrice;
	}

	/**
	 * @param simplePrice
	 *            the simplePrice to set
	 */
	public void setSimplePrice(double simplePrice)
	{
		this.simplePrice = simplePrice;
	}

	/**
	 * @return the doublePrice
	 */
	public double getDoublePrice()
	{
		return doublePrice;
	}

	/**
	 * @param doublePrice
	 *            the doublePrice to set
	 */
	public void setDoublePrice(double doublePrice)
	{
		this.doublePrice = doublePrice;
	}

	/**
	 * @return the triplePrice
	 */
	public double getTriplePrice()
	{
		return triplePrice;
	}

	/**
	 * @param triplePrice
	 *            the triplePrice to set
	 */
	public void setTriplePrice(double triplePrice)
	{
		this.triplePrice = triplePrice;
	}

	/**
	 * @return the supplement
	 */
	public double getSupplement()
	{
		return supplement;
	}

	/**
	 * @param supplement
	 *            the supplement to set
	 */
	public void setSupplement(double supplement)
	{
		this.supplement = supplement;
	}

	/**
	 * @return the hasBreakfast
	 */
	public boolean hasBreakfast()
	{
		return hasBreakfast;
	}

	/**
	 * @param hasBreakfast
	 *            the hasBreakfast to set
	 */
	public void setHasBreakfast(boolean hasBreakfast)
	{
		this.hasBreakfast = hasBreakfast;
	}

	/**
	 * @return the mpSupplement
	 */
	public double getMpSupplement()
	{
		return mpSupplement;
	}

	/**
	 * @param mpSupplement
	 *            the mpSupplement to set
	 */
	public void setMpSupplement(double mpSupplement)
	{
		this.mpSupplement = mpSupplement;
	}

	/**
	 * @return the pcSupplement
	 */
	public double getPcSupplement()
	{
		return pcSupplement;
	}

	/**
	 * @param pcSupplement
	 *            the pcSupplement to set
	 */
	public void setPcSupplement(double pcSupplement)
	{
		this.pcSupplement = pcSupplement;
	}

	public double getTotalPrice(int simple, int doublep, int triple)
	{
		return simple * simplePrice + doublep * doublePrice + triple
				* triplePrice;
	}

	public double getBookingPrice(int simple, int doublep, int triple)
	{
		return 0.1 * (simple * simplePrice + doublep * doublePrice + triple
				* triplePrice);
	}

	@Override
	public String toString()
	{
		return this.getAddress() + " - " + this.getCity() + " (" + this.getCountry() + ") "
				+ this.getSimplePrice() + "€";
	}
}
