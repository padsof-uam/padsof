package padsof.provider;

import es.uam.eps.pads.services.ServicesFactory;
import es.uam.eps.pads.services.flights.FlightsProvider;

public class Provider
{
	FlightsProvider fp;

	public Provider()
	{
		fp = ServicesFactory.getServicesFactory().getFlightsProvider();
	}

}
