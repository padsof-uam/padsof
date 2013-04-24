package padsof.gui.controllers;

import java.util.*;

import javax.swing.JOptionPane;

import padsof.db.DBWrapper;
import padsof.gui.Application;
import padsof.gui.controllers.utils.Listener;
import padsof.gui.views.*;
import padsof.system.*;

public class RegisterClientController extends Controller<RegisterClientView>
{
	@Listener("Crear")
	public void createClient()
	{
		Client cliente = null;

		Client NCliente;
		ImsersoClient ICliente;

		if (((String) view.getValueFor("Código de verificación")).equals(""))
		{
			NCliente = new Client();
			cliente = NCliente;
		}
		else
		{
			ICliente = new ImsersoClient();
			if (((String) view.getValueFor("Nº Seguridad Social")).isEmpty()
					|| !isOldEnough((Date) view.getValueFor("Fecha de nacimiento")))
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
		if (((String) view.getValueFor("DNI")).isEmpty()
				|| ((String) view.getValueFor("Nombre")).isEmpty()
				|| ((String) view.getValueFor("Apellidos")).isEmpty())
			{
			JOptionPane.showMessageDialog(view,
					"Por favor rellene todos los campos", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		else
		{
			String dni = (String) view.getValueFor("DNI");
			if (isDNIValido(dni))
				cliente.setDNI(dni);
			else {
				JOptionPane.showMessageDialog(view,"Intruduzca un dni válido","Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			cliente.setName((String) view.getValueFor("Nombre"));
			cliente.setSurname((String) view.getValueFor("Apellidos"));
		}

		Application app = Application.getInstance();
		app.setCliente(cliente);
		/**
		 * Guardamos en la base de datos
		 */
		try
		{
			DBWrapper.getInstance().save(cliente);
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(view,
					"No se ha podido guardar el cliente");
			return;
		}

		JOptionPane.showMessageDialog(view, "Cliente creado.");
		navigator.navigate(SelectPacketView.class);
	}

	private boolean isOldEnough(Date value)
	{
		Calendar c = Calendar.getInstance();
		c.add(Calendar.YEAR, -65);
		return value.before(c.getTime());		
	}

	private boolean isDNIValido(String dni)
	{
		if (dni.length() != 9)
			return false;
		char letra = dni.charAt(8);
		if (!Character.isAlphabetic(letra))
			return false;
		String numeros = dni.substring(0, 7);
		try{
			Long.parseLong(numeros);
		} catch(Exception e){
			return false;
		}
		return true;
	}

}
