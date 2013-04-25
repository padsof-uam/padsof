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

public class SelectClientController extends Controller<SelectClientView>
{
	private List<Client> clients = new ArrayList<Client>();

	public void refreshClients()
	{
		try
		{
			clients.removeAll(clients);
			clients.addAll(DBWrapper.getInstance().getAll(Client.class));
			clients.addAll(DBWrapper.getInstance().getAll(ImsersoClient.class));
		}
		catch (SQLException e)
		{
			showError("No se han podido recuperar los clientes.");
			return;
		}

		view.setModel(clients);
	}
	
	@Override
	public void setView(SelectClientView view)
	{
		super.setView(view);

		refreshClients();
	}

	@Listener("DocChange")
	public void dniChanged()
	{
		String dni = view.getDNI();
		List<Client> filtered = new ArrayList<Client>();

		if (dni == null || dni.isEmpty())
		{
			view.setModel(clients);
			return;
		}

		for (Client c : clients)
			if (c.getDNI().indexOf(dni) != -1)
				filtered.add(c);

		view.setModel(filtered);
	}

	@Listener("Select")
	public void selected()
	{
		Client client = view.getSelectedClient();
		Application.getInstance().setClient(client);

		navigator.navigate(SelectPacketView.class);
	}

	@Listener("Delete")
	public void delete()
	{
		Client client = view.getSelectedClient();
		int option = JOptionPane
				.showConfirmDialog(
						view,
						"¿Está seguro de querer borrar este cliente, borrar todos los paquetes asociados y cancelar todas sus reservas?");

		if (option == JOptionPane.YES_OPTION)
		{
			try
			{
				List<Packet> packets = DBWrapper.getInstance().get(
						Packet.class, "client", client);
				for (Packet p : packets)
				{
					for (Booking b : p.getBookings())
					{
						b.cancel();
						DBWrapper.getInstance().delete(b);
					}

					DBWrapper.getInstance().delete(p);
				}

				DBWrapper.getInstance().delete(client);
				showMessage("Cliente borrado con éxito.");
				refreshClients();
			}
			catch (Exception e)
			{
				showError("Error borrando el cliente: " + e.getMessage());
			}
		}
	}
}
