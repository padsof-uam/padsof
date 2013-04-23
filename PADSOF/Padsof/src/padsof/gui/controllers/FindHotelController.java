package padsof.gui.controllers;

import java.sql.SQLException;
import java.util.Date;

import padsof.db.*;
import padsof.gui.controllers.utils.Listener;
import padsof.gui.views.FindHotelView;
import padsof.services.Hotel;

public class FindHotelController extends Controller<FindHotelView>
{
	@Listener("Search")
	public void search()
	{
		Date start, end;
		double maxPrice = -1;
		String city, country;

		start = view.getStartDate();
		end = view.getEndDate();
		city = view.getCity();
		country = view.getCountry();

		try
		{
			String sPrice = view.getPrice();
			if (!sPrice.isEmpty())
				maxPrice = Double.valueOf(sPrice);
		}
		catch (NumberFormatException e)
		{
			showError("Formato de número inválido.");
			return;
		}
		Query<Hotel> query;
		try
		{
			query = DBWrapper.getInstance().prepareQuery(Hotel.class);
		}
		catch (SQLException e)
		{
			showError("Error preparando la consulta en base de datos.");
			return;
		}

		try
		{
			if (!city.isEmpty())
				query.setEquals("city", city);
			if (!country.isEmpty())
				query.setEquals("country", country);
			if (maxPrice != -1)
				query.setMax("simplePrice", maxPrice);
			view.setModel(DBWrapper.getInstance().executeQuery(Hotel.class, query));
		}
		catch (Exception e)
		{
			showError("No se ha podido consultar a la base de datos.");
		}

	}
}
