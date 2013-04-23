package padsof.gui.controllers;

import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import padsof.db.DBWrapper;
import padsof.gui.Application;
import padsof.gui.controllers.utils.Listener;
import padsof.gui.views.FindPacketView;
import padsof.system.Packet;

public class FindPacketController extends Controller<FindPacketView>
{

	public void refreshPackets()
	{
		List<Packet> packets;
		
		try
		{
			packets = DBWrapper.getInstance().getAll(Packet.class);
		}
		catch (SQLException e)
		{
			JOptionPane.showMessageDialog(view, "No se pueden recuperar los vendedores.");
			return;
		}
		
		view.setModel(packets);
	}
	
	@Override
	public void setView(FindPacketView view)
	{
		super.setView(view);
		refreshPackets();
	}
	
	@Listener("Crear")
	public void createPacket()
	{
		Packet packet = new Packet();

		try
		{
			DBWrapper.getInstance().save(packet);
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(view, "No se ha podido guardar el paquete");
			return;
		}
		
		JOptionPane.showMessageDialog(view, "Paquete creado.");
		refreshPackets();
		
		packet.setClient(Application.getInstance().getCliente());
	}
	
	@Listener("Existente")
	public void existentPacket(){
		refreshPackets();
		
	}
	
}
