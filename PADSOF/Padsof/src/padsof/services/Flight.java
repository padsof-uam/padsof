package padsof.services;

import padsof.system.Margin;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import es.uam.eps.pads.services.flights.FlightInfo;

@DatabaseTable
public class Flight extends Service
{
	@DatabaseField
	private String localizer;

	/**
	 * @return the localizer
	 */
	public String getLocalizer()
	{
		return localizer;
	}

	/**
	 * @param localizer
	 *            the localizer to set
	 */
	public void setLocalizer(String localizer)
	{
		this.localizer = localizer;
	}

	public Flight(String localizer)
	{
		this.localizer = localizer;
	}

	public Flight()
	{
	}

	public Flight(FlightInfo flight)
	{
		this.localizer = flight.getCode();
		this.setName("Flight from " + flight.getSource() + " to " + flight.getDestination());
		this.setCost(flight.getPrice());
		this.setPrice(flight.getPrice() * (Margin.getMargin().getMarginPoints() + 1));
		this.setDescription(flight.toString());
	}
	@Override
	public String toString(){
		return name;
	}
}
