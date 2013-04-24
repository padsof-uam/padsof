package padsof.gui.controllers;

import java.sql.SQLException;

import padsof.bookings.*;
import padsof.db.*;
import padsof.gui.Application;
import padsof.gui.controllers.utils.Listener;
import padsof.gui.views.FindTravelView;
import padsof.services.Travel;

public class FindTravelController extends Controller<FindTravelView>
{
	@Listener("Search")
	public void search()
	{
		Query<Travel> query;
		try
		{
			query = DBWrapper.getInstance().prepareQuery(Travel.class);
		}
		catch (SQLException e)
		{
			showError("Error recuperando viajes");
			return;
		}

		double maxPrice = -1;

		try
		{
			String price = view.getMaxPrice();

			if (!price.isEmpty())
				maxPrice = Double.valueOf(price);
		}
		catch (NumberFormatException e)
		{
			showError("Formato de precio inválido.");
			return;
		}

		try
		{
			if (maxPrice != -1)
				query.setMax("price", maxPrice);

			view.setResults(DBWrapper.getInstance().executeQuery(query));
		}
		catch (Exception e)
		{
			showError("Error buscando en la base de datos.");
		}
	}

	@Listener("Book")
	public void book()
	{
		Travel hotel = view.getSelectedTravel();

		if (view.getStartDate().after(view.getEndDate()))
		{
			showError("Fechas inválidas.");
			return;
		}

		BookingFactory factory = new BookingFactory(Application.getInstance()
				.getVendor());

		try
		{
			Booking booking = factory.book(hotel, Application.getInstance()
					.getClient(), view.getStartDate(), view.getEndDate());
			double price = booking.book();
			Application.getInstance().getPacket().add(booking);

			showMessage("Reserva realizada. Precio: " + price + "€");
		}
		catch (Exception e)
		{
			showError("Error reservando el paquete: " + e.getMessage());
			return;
		}

		navigator.goBack();
	}
}
