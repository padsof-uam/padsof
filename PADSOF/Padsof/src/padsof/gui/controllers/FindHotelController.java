package padsof.gui.controllers;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import padsof.bookings.*;
import padsof.db.*;
import padsof.gui.Application;
import padsof.gui.controllers.utils.Listener;
import padsof.gui.views.FindHotelView;
import padsof.services.Hotel;
import padsof.system.Margin;

public class FindHotelController extends Controller<FindHotelView>
{
	@Listener("Search")
	public void search()
	{
		double maxPrice = -1;
		String city, country;

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
			view.setModel(DBWrapper.getInstance().executeQuery(query));
		}
		catch (Exception e)
		{
			showError("No se ha podido consultar a la base de datos.");
		}
	}

	@Listener("Ver")
	public void seeInformation()
	{
		Hotel hotel = view.getSelectedHotel();
		showMessage(hotel.toString());
	}

	@Listener("Book")
	public void book()
	{
		Hotel hotel = view.getSelectedHotel();

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
			int simple = view.getSimples(), doble = view.getDobles(), triple = view.getTriples();
			double price = doble * hotel.getDoublePrice() + simple
					* hotel.getSimplePrice() + triple * hotel.getTriplePrice();

			if (hotel.hasBreakfast())
			{
				int answer = JOptionPane
						.showConfirmDialog(view, "¿Quiere desayuno?");
				if (answer == JOptionPane.YES_OPTION)
				{
					price += hotel.getSupplement();
				}else if(answer == JOptionPane.CANCEL_OPTION || answer == JOptionPane.CLOSED_OPTION){
					return;
				}
			}
			((HotelBooking) booking).setPrice(price + Margin.getMargin().getMarginPoints() * price);	
			((HotelBooking) booking).setCost(price);
			price = booking.book();
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
