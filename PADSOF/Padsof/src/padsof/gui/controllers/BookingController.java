package padsof.gui.controllers;

import java.sql.SQLException;

import padsof.bookings.Booking;
import padsof.db.DBWrapper;
import padsof.gui.Application;
import padsof.gui.controllers.utils.Listener;
import padsof.gui.views.BookingView;
import padsof.system.Packet;

public class BookingController extends Controller<BookingView>
{
	private void refreshBookings()
	{
		try
		{
			Packet packet = Application.getInstance().getPacket();
			packet.refreshBookings();
			view.setModel(packet.getBookings());
		}
		catch (SQLException e)
		{
			showError("No se han podido cargar las reservas del paquete.");
		}
	}

	@Override
	public void setView(BookingView view)
	{
		super.setView(view);
		refreshBookings();
	}

	@Listener("Cancel")
	public void cancel()
	{
		Booking toCancel = view.getSelectedItem();
		toCancel.cancel();
		Packet currentPacket = Application.getInstance().getPacket();

		try
		{
			DBWrapper.getInstance().delete(toCancel);
			currentPacket.refreshBookings();
		}
		catch (Exception e)
		{
			showError("Error cancelando la reserva: " + e.getMessage());
		}

		refreshBookings();
	}
	@Listener ("Refrescar")
	public void Refresh(){
		refreshBookings();
	}

	@Listener("Confirm")
	public void confirm()
	{
		Booking toConfirm = view.getSelectedItem();
		try
		{
			double price = toConfirm.confirm();
			DBWrapper.getInstance().save(toConfirm);
			Application.getInstance().getPacket().refreshBookings();
			showMessage("Reserva confirmada. Precio: " + price + "€");
		}
		catch (Exception e)
		{
			showError("Error confirmando la reserva: " + e.getMessage());
		}
		
		refreshBookings();
	}

}
