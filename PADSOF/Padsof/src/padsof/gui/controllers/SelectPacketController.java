package padsof.gui.controllers;

import java.sql.SQLException;
import java.util.*;

import javax.swing.JOptionPane;

import padsof.bookings.Booking;
import padsof.db.DBWrapper;
import padsof.gui.Application;
import padsof.gui.controllers.utils.Listener;
import padsof.gui.views.*;
import padsof.system.*;

public class SelectPacketController extends Controller<SelectPacketView>
{

	public void refreshPackets()
	{
		List<Packet> packets;

		try
		{
			Application.getInstance().getVendor().refreshBookings();
			packets = Application.getInstance().getVendor().getPackets();
		}
		catch (SQLException e)
		{
			JOptionPane.showMessageDialog(view,
					"No se pueden recuperar los paquetes.");
			return;
		}

		List<Packet> clientPackets = new ArrayList<Packet>();

		for (Packet p : packets)
			if (p.getClient().equals(Application.getInstance().getClient()))
			{
				try
				{
					p.refreshBookings();
					p.closeAutomatically();
				}
				catch (SQLException e)
				{
				}
				clientPackets.add(p);
			}

		view.setModel(clientPackets);
	}

	@Override
	public void setView(SelectPacketView view)
	{
		super.setView(view);
		refreshPackets();
	}

	@Listener("Nuevo")
	public void createPacket()
	{
		Packet packet = new Packet();

		packet.setClient(Application.getInstance().getClient());

		try
		{
			DBWrapper.getInstance().save(packet);
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(view,
					"No se ha podido guardar el paquete");
			return;
		}

		Application.getInstance().setPacket(packet);
		refreshPackets();

		navigator.navigate(BookingView.class);

	}

	@Listener("Existente")
	public void existentPacket()
	{
		if (view.getSelectedPacket() != null)
		{
			Application.getInstance().setPacket(view.getSelectedPacket());
			navigator.navigate(BookingView.class);
		}
		else
			JOptionPane
					.showMessageDialog(view,
							"Por favor, seleccione un paquete o elija crear un paquete nuevo.");
	}

	@Listener("Delete")
	public void delete()
	{
		int option = JOptionPane
				.showConfirmDialog(view,
						"¿Está seguro que quiere eliminar este paquete y todas sus reservas?");

		if (option == JOptionPane.YES_OPTION)
		{
			try
			{
				Packet toDelete = view.getSelectedPacket();

				for (Booking b : toDelete.getBookings())
				{
					b.cancel();
					DBWrapper.getInstance().delete(b);
				}

				DBWrapper.getInstance().delete(toDelete);
				showMessage("Paquete eliminado.");
				refreshPackets();
			}
			catch (Exception e)
			{
				showError("Error eliminando el paquete: " + e.getMessage());
			}
		}
	}

	@Listener("Close")
	public void close()
	{
		Packet packet = view.getSelectedPacket();
		
		int option = JOptionPane
				.showConfirmDialog(view,
						"¿Está seguro que quiere cerrar este paquete?\n Se cancelarán todas las reservas no confirmadas.\nEsta operación no se puede deshacer.");

		if (option == JOptionPane.YES_OPTION)
		{
			try
			{
				for(Booking b: packet.getBookings())
				{
					if(!b.isPayed())
					{
						b.cancel();
						DBWrapper.getInstance().save(b);
					}
				}
				
				packet.closePacket();
			}
			catch(Exception e)
			{
				showError("No se ha podido cerrar el paquete: " + e.getMessage());
				return;
			}
	
			showMessage("Paquete cerrado correctamente.");
		}	
	}
}
