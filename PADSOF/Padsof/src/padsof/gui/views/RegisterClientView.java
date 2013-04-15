/**
 * 
 */
package padsof.gui.views;

import java.awt.*;
import java.awt.event.ActionListener;
import java.rmi.NoSuchObjectException;

import javax.swing.*;

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

	public RegisterClientView() throws NoSuchObjectException
	{
		super("Registrar cliente");
		
		FormGenerator generator = new FormGenerator();
		
		generator.addFields("DNI", "Nombre", "Apellido 1", "Apellido 2", "Nº Seguridad Social",
				"Código de verificación", "Fecha de nacimiento");
		
		generator.setButton(new JButton("Crear"));
		generator.setTitle("Alta de cliente");
		
		JPanel form = generator.generateForm();
		setLayout(new FlowLayout());
		add(form);
	}

	/* (non-Javadoc)
	 * @see padsof.gui.views.View#setController(java.awt.event.ActionListener)
	 */
	@Override
	public void setController(ActionListener c)
	{
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see padsof.gui.views.View#show(javax.swing.JFrame)
	 */
	@Override
	public void show(JFrame window)
	{
		Container container = window.getContentPane();
		container.add(this);
		window.setMinimumSize(new Dimension(550, 220));
		
		window.setVisible(true);
	}

}
