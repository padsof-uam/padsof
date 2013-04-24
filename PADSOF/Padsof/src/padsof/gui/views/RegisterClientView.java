/**
 * 
 */
package padsof.gui.views;

import java.awt.FlowLayout;
import java.rmi.NoSuchObjectException;

import javax.swing.*;

import padsof.gui.controllers.Controller;
import padsof.gui.utils.*;

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
	private JButton Crear;
	private FormGenerator generator;
	
	
	public Object getValueFor(String field) throws ComponentNotFoundException{
		Object retval = null;
		if (field.compareToIgnoreCase("DNI") == 0)
			retval = generator.getValueFor("DNI");
		else if (field.compareToIgnoreCase("Nombre") == 0)
			retval = generator.getValueFor("Nombre");
		else if (field.compareToIgnoreCase("Apellidos") == 0)
			retval = generator.getValueFor("Apellidos");
		else if (field.compareToIgnoreCase("Nº Seguridad Social") == 0)
			retval = generator.getValueFor("Nº Seguridad Social");
		else if (field.compareToIgnoreCase("Código de verificación") == 0)
			retval = generator.getValueFor("Código de verificación");
		else if (field.compareToIgnoreCase("Fecha de Nacimiento") == 0)
				retval = generator.getDatefor("Fecha de nacimiento");
		return retval;
	}
	public RegisterClientView() throws NoSuchObjectException
	{
		super("Registrar cliente");
		
		generator = new FormGenerator();
		
		generator.addFields("DNI", "Nombre", "Apellidos", "Nº Seguridad Social",
				"Código de verificación", "Fecha de nacimiento");
		
		Crear = new JButton("Crear");
		generator.addButton(Crear);
		generator.setTitle("Alta de cliente");
		
		JPanel form = generator.generateForm();
		setLayout(new FlowLayout());
		add(form);
	}
	
	

	@Override
	public <V extends View> void setController(Controller<V> c)
	{
		Crear.setActionCommand("Crear");
		Crear.addActionListener(c);
	}

}
