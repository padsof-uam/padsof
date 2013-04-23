package padsof.gui.controllers;

import java.util.*;

import javax.swing.JOptionPane;

import padsof.db.DBWrapper;
import padsof.gui.Application;
import padsof.gui.controllers.utils.Listener;
import padsof.gui.views.RegisterClientView;
import padsof.system.*;

public class RegisterClientController extends Controller<RegisterClientView>
{
	@Listener("Crear")
	public void createClient()
	{
		Client cliente = null;

		Client NCliente;
		ImsersoClient ICliente;
		if (view.getValueFor("Código de verificación") == null)
		{
			NCliente = new Client();
			cliente = NCliente;
		}
		else
		{
			ICliente = new ImsersoClient();
			if ((String) view.getValueFor("Nº Seguridad Social") == null
					|| (Date) view.getValueFor("Fecha de nacimiento") == null)
			{
				JOptionPane
						.showMessageDialog(
								view,
								"Por favor rellene todos los campos para un cliente Imserso",
								"Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			long SsNumber = Long.parseLong(((String) view.getValueFor("Nº Seguridad Social")));
			ICliente.setBirth((Date) view.getValueFor("Fecha de nacimiento"));
			ICliente.setSsNumber(SsNumber);
			cliente = ICliente;
		}
		if (((String) view.getValueFor("DNI") == null)
				|| ((String) view.getValueFor("Nombre") == null)
				|| ((String) view.getValueFor("Apellido 1") == null)
				|| ((String) view.getValueFor("Apellido 2") == null))
			{
			JOptionPane.showMessageDialog(view,
					"Por favor rellene todos los campos", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
			}
		else
		{
			cliente.setDNI((String) view.getValueFor("DNI"));
			cliente.setName((String) view.getValueFor("Nombre"));
			cliente.setSurname((String) view.getValueFor("Apellido 1")
					+ (String) view.getValueFor("Apellido 2"));
		}
		
		//TODO: guardar cliente en base de datos
		Application.getInstance().setCliente(cliente);
		
		try
		{
			DBWrapper.getInstance().save(cliente);
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(view, "No se ha podido guardar el cliente");
			return;
		}
		
		JOptionPane.showMessageDialog(view, "Cliente creado.");
	}


}
