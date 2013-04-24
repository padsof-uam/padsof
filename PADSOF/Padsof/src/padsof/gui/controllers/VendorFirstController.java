package padsof.gui.controllers;

import java.sql.SQLException;
import java.util.*;

import padsof.db.DBWrapper;
import padsof.gui.Application;
import padsof.gui.controllers.utils.Listener;
import padsof.gui.views.*;
import padsof.system.*;

public class VendorFirstController extends Controller<VendorFirstView>
{
	private List<Client> clients = new ArrayList<Client>();
	
	@Override
	public void setView(VendorFirstView view)
	{
		super.setView(view);
	
		
		try
		{
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
	
	@Listener("DocChange")
	public void dniChanged()
	{
		String dni = view.getDNI();
		List<Client> filtered = new ArrayList<Client>();
		
		if(dni == null || dni.isEmpty())
		{
			view.setModel(clients);
			return;
		}
			
		for(Client c : clients)
			if(c.getDNI().indexOf(dni) != -1)
				filtered.add(c);
		
		view.setModel(filtered);
	}
	
	@Listener("Select")
	public void selected()
	{
		Client client = view.getSelectedClient();
		Application.getInstance().setCliente(client);
		
		navigator.navigate(FindPacketView.class);
	}
}
