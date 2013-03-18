package padsof.db.feeder;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import es.uam.eps.pads.services.InvalidParameterException;
import es.uam.eps.pads.services.ServicesFactory;
import es.uam.eps.pads.services.flights.AirportInfo;
import es.uam.eps.pads.services.flights.FlightsProvider;

import padsof.services.*;
import padsof.system.*;

public class DataBase
{
	private ArrayList<Vendor> vendors;
	private ArrayList<Hotel> hotels;
	private ArrayList<Flight> flights;
	private ArrayList<Travel> travels;
	private ArrayList<ImsersoTravel> IMTravels;

	public ArrayList<Vendor> getVendors()
	{
		return vendors;
	}

	public ArrayList<Hotel> getHotels()
	{
		return hotels;
	}

	public ArrayList<Flight> getFlights()
	{
		return flights;
	}

	public ArrayList<Travel> getTravels()
	{
		return travels;
	}

	public ArrayList<ImsersoTravel> getIMTravels()
	{
		return IMTravels;
	}

	public DataBase()
	{
		vendors = new ArrayList<Vendor>();
		hotels = new ArrayList<Hotel>();
		flights = new ArrayList<Flight>();
		travels = new ArrayList<Travel>();
		IMTravels = new ArrayList<ImsersoTravel>();
	}

	public void DBFeeder(String file) throws Exception
	{

		switch (file)
		{
			case "utils/Hoteles.csv":
				DBFeederHotel(file);
				break;
			case "utils/ViajesIMSERSO.csv":
				DBFeederImserso(file);
				break;
			case "utils/ViajesOrganizados.csv":
				DBFeederTravel(file);
				break;
			case "flights":
				DBFeederFlights();
				break;
			default:
				throw new Exception("Can't load that file");

		}
	}

	private void DBFesederFlights() throws InvalidParameterException
	{
		FlightsProvider fp= ServicesFactory.getServicesFactory().getFlightsProvider();
		List<AirportInfo> airports = fp.queryAirports();
		for (AirportInfo source: airports){
			for (AirportInfo destination : airports){
				List<String> aux = fp.queryFlights(source.getCode(), destination.getCode(), new GregorianCalendar().getTime(),new GregorianCalendar().getTime());
				for( String iterator: aux)
					if (this.flights.contains(aux))
						this.flights.add(new Flight(iterator));
				}
		}
		}

	private void DBFeederImserso(String file) throws Exception
	{
		CSVReader CSV = new CSVReader(file);
		List<List<String>> data = new ArrayList<List<String>>();
		data = CSV.parseAll();
		// La cabecera del fichero
		data.remove(0);
		try {
			for (List<String> iterator : data){
				this.IMTravels.add(new ImsersoTravel(iterator.get(0),Double.parseDouble(iterator.get(1)),
					Integer.parseInt(iterator.get(2)),Integer.parseInt(iterator.get(3)),
					iterator.get(4),iterator.get(5),iterator.get(6),iterator.get(7)));
			}

		} catch (Exception e){ throw new Exception("Bad Format");}
		finally{
			CSV.close();
		}


	}

	public void DBFeederTravel(String file) throws Exception
	{
		CSVReader CSV = new CSVReader(file);
		List<List<String>> data = new ArrayList<List<String>>();
		data = CSV.parseAll();
		// La cabecera del fichero
		data.remove(0);
		try
		{
			for (List<String> iterator : data)
			{
				travels.add(new Travel(iterator.get(0), iterator.get(1),
						iterator.get(2), Double.parseDouble(iterator.get(3)),
						Integer.parseInt(iterator.get(4)), Integer
								.parseInt(iterator.get(5)), iterator.get(6),
						iterator.get(8), iterator.get(8), iterator.get(9)));
			}

		} catch (Exception e){ throw new Exception("Bad Format");}
		finally
		{
			CSV.close();
		}

	}

	
	public void DBFeederHotel(String file) throws Exception
	{
		CSVReader CSV = new CSVReader(file);
		List<List<String>> data = new ArrayList<List<String>>();
		data = CSV.parseAll();
		// La cabecera del fichero
		data.remove(0);
		try
		{
			// TODO: chapuzaaa...
			for (List<String> iterator : data)
			{
				hotels.add(new Hotel(iterator.get(0), iterator.get(1), iterator
						.get(2), iterator.get(3), iterator.get(4), iterator
						.get(5), iterator.get(6), Double.parseDouble(iterator
						.get(7)), Double.parseDouble(iterator.get(8)), Double
						.parseDouble(iterator.get(9)), Double
						.parseDouble(iterator.get(10)), Double
						.parseDouble(iterator.get(11)), Double
						.parseDouble(iterator.get(12)), iterator.get(13)));
			}
		}catch (Exception e){ throw new Exception("Bad Format");}
		finally
		{
			CSV.close();

		}

	}

}
