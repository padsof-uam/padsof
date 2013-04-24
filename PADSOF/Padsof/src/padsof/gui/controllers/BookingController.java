package padsof.gui.controllers;

import java.sql.SQLException;

import padsof.gui.Application;
import padsof.gui.views.BookingView;
public class BookingController extends Controller<BookingView>
{
	@Override
	public void setView(BookingView view){
		super.setView(view);
		try
		{
			view.setModel(Application.getInstance().getPacket().getBookings());
		}
		catch (SQLException e)
		{
			showError("No se han podido cargar las reservas del paquete.");
		}
		
	}
}
