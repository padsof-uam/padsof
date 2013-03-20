package padsof.db.feeder;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import padsof.csv.*;
import padsof.db.DBWrapper;
import padsof.services.*;

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

	public void saveImsersoTravelData(String file)
			throws IllegalArgumentException, IllegalAccessException,
			SQLException, InvalidFormatException, IOException
	{
		DBWrapper.getInstance().save(loadImsersoTravelData(file));
	}

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

	public void saveTravelData(String file) throws IllegalArgumentException,
			IllegalAccessException, SQLException, InvalidFormatException,
			IOException
	{
		DBWrapper.getInstance().save(loadTravelData(file));
	}

	public void saveHotelData(String file) throws IllegalArgumentException,
			IllegalAccessException, SQLException, Exception
	{
		DBWrapper.getInstance().save(loadHotelData(file));
	}

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
