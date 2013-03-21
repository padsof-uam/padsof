package padsof.db.feeder;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import padsof.csv.*;
import padsof.db.DBWrapper;
import padsof.services.*;

/**
 * The class for reading files and load them into memory. It has methods to save
 * them into the database.
 * 
 * @author Víctor de Juan Sanz - Guillermo Julián Moreno
 */
public class DBFeeder
{
	private class ImsersoTravelCreator implements CSVCreator<ImsersoTravel>
	{
		@Override
		public ImsersoTravel parseLine(List<String> fields)
		{
			return new ImsersoTravel(fields.get(0), Double.parseDouble(fields
					.get(1)), Integer.parseInt(fields.get(2)),
					Integer.parseInt(fields.get(3)), fields.get(4),
					fields.get(5), fields.get(6), fields.get(7));
		}
	}

	private class HotelCreator implements CSVCreator<Hotel>
	{

		@Override
		public Hotel parseLine(List<String> iterator)
		{
			return new Hotel(iterator.get(0), iterator.get(1), iterator.get(2),
					iterator.get(3), iterator.get(4), iterator.get(5),
					iterator.get(6), Double.parseDouble(iterator.get(7)),
					Double.parseDouble(iterator.get(8)),
					Double.parseDouble(iterator.get(9)),
					Double.parseDouble(iterator.get(10)),
					Double.parseDouble(iterator.get(11)),
					Double.parseDouble(iterator.get(12)), iterator.get(13));
		}
	}

	private class TravelCreator implements CSVCreator<Travel>
	{
		@Override
		public Travel parseLine(List<String> iterator)
		{
			return new Travel(iterator.get(0), iterator.get(1),
					iterator.get(2), Double.parseDouble(iterator.get(3)),
					Integer.parseInt(iterator.get(4)),
					Integer.parseInt(iterator.get(5)), iterator.get(6),
					iterator.get(7), iterator.get(8), iterator.get(9));
		}
	}

	/**
	 * Load the file of IMSERSO travels.
	 * @param file
	 * @return a list of ImsersoTravels
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public List<ImsersoTravel> loadImsersoTravelData(String file)
			throws InvalidFormatException, IOException
	{
		CSVReader CSV = new CSVReader(file);
		List<ImsersoTravel> travels;
		try
		{
			travels = CSV.parseAll(new ImsersoTravelCreator());
		}
		finally
		{
			CSV.close();
		}

		return travels;
	}

	/**
	 * Save into the database the catalog of IMSERSO travels
	 * @param file
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws SQLException
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public void saveImsersoTravelData(String file)
			throws IllegalArgumentException, IllegalAccessException,
			SQLException, InvalidFormatException, IOException
	{
		DBWrapper.getInstance().save(loadImsersoTravelData(file));
	}

	/**
	 * Load the file of travels.
	 * @param file to read
	 * @return a list of Travels
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public List<Travel> loadTravelData(String file)
			throws InvalidFormatException, IOException
	{
		CSVReader CSV = new CSVReader(file);
		List<Travel> travels;
		try
		{
			travels = CSV.parseAll(new TravelCreator());
		}
		finally
		{
			CSV.close();
		}

		return travels;
	}

	/**
	 * Save into database the catalog of travels.
	 * @param file to read
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws SQLException
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public void saveTravelData(String file) throws IllegalArgumentException,
			IllegalAccessException, SQLException, InvalidFormatException,
			IOException
	{
		DBWrapper.getInstance().save(loadTravelData(file));
	}

	/**
	 * Save into database the catalog of hotels.
	 * @param file to read
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws SQLException
	 * @throws Exception
	 */
	public void saveHotelData(String file) throws IllegalArgumentException,
			IllegalAccessException, SQLException, Exception
	{
		DBWrapper.getInstance().save(loadHotelData(file));
	}

	/**
	 * Load from file.
	 * @param file to read
	 * @return a list of Hotels.
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public List<Hotel> loadHotelData(String file)
			throws InvalidFormatException, IOException
	{
		CSVReader CSV = new CSVReader(file);
		List<Hotel> hotels;
		try
		{
			hotels = CSV.parseAll(new HotelCreator());
		}
		finally
		{
			CSV.close();
		}

		return hotels;
	}
}
