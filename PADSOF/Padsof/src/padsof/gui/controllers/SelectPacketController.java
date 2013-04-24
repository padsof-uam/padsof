package padsof.gui.controllers;

import java.sql.SQLException;
import java.util.*;

import javax.swing.JOptionPane;

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
			packets = Application.getInstance().getVendor().getPackets();
		}
		catch (SQLException e)
		{
			JOptionPane.showMessageDialog(view,
					"No se pueden recuperar los paquetes.");
			return;
		}
		
		List<Packet> clientPackets = new ArrayList<Packet>();
		
		for(Packet p: packets)
			if(p.getClient().equals(Application.getInstance().getClient()))
				clientPackets.add(p);
		
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

		JOptionPane.showMessageDialog(view, "Paquete creado.");
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
}
