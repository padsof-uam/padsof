package padsof.gui.controllers;


import java.util.List;

import padsof.db.DBWrapper;
import padsof.gui.Application;
import padsof.gui.controllers.utils.Listener;
import padsof.gui.views.*;
import padsof.system.*;

public class SearchClientController extends Controller<SearchClient>
{
	@Listener ("Search")
	public void searchClient(){
		String DNI = view.getDni();
		Client cliente;
		
		try
		{
			List<Client> clientes = DBWrapper.getInstance().get(Client.class, "DNI", DNI);
			clientes.addAll(DBWrapper.getInstance().get(ImsersoClient.class, "DNI", DNI));
			cliente = clientes.isEmpty() ? null : clientes.get(0);
		}
		catch(Exception e)
		{
			showError("No se ha podido recuperar los clientes.");
			return;
		}
		
		if(cliente == null)
		{
			showError("Ese cliente no existe.");
			return;
		}
		
		Application.getInstance().setCliente(cliente);		
		navigator.navigate(FindPacketView.class);
		
	}
}
