/**
 * 
 */
package padsof.gui.views;

import java.awt.FlowLayout;
import java.rmi.NoSuchObjectException;

import javax.swing.*;

import padsof.gui.controllers.Controller;
import padsof.gui.utils.FormGenerator;

/**
 * @author Víctor de Juan Sanz - Guillermo Julián Moreno
 *
 */
public class RegisterClientView extends View
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1798909379089006043L;

	public RegisterClientView() throws NoSuchObjectException
	{
		super("Registrar cliente");
		
		FormGenerator generator = new FormGenerator();
		
		generator.addFields("DNI", "Nombre", "Apellido 1", "Apellido 2", "Nº Seguridad Social",
				"Código de verificación", "Fecha de nacimiento");
		
		generator.addButton(new JButton("Crear"));
		generator.setTitle("Alta de cliente");
		
		JPanel form = generator.generateForm();
		setLayout(new FlowLayout());
		add(form);
	}


	@Override
	public <V extends View> void setController(Controller<V> c)
	{
		// TODO Auto-generated method stub
		
	}
}
