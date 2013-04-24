package padsof.gui.controllers;


import padsof.gui.Application;
import padsof.gui.controllers.utils.Listener;
import padsof.gui.views.*;
import padsof.system.Client;

public class SearchClientController extends Controller<SearchClient>
{
	@Listener ("Search")
	public void searchClient(){
		String DNI = view.getDni();
		Client cliente = null;
		//TODO: consulta en base de datos para sacar el cliente con ese DNI.
		Application.getInstance().setCliente(cliente);		
		navigator.navigate(FindPacketView.class);
		
	}
}
