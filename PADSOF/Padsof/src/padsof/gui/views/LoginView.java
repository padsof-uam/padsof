package padsof.gui.views;

import java.awt.*;
import java.awt.event.ActionListener;
import java.rmi.NoSuchObjectException;

import javax.swing.*;

import padsof.gui.utils.*;

public class LoginView extends View
{
	
	public LoginView() throws NoSuchObjectException
	{
		super("Login");
		
		
		FormGenerator generator = new FormGenerator();

		generator.addFields("Usuario","Contraseña");
		
		generator.addButton(new JButton("Entrar"));
		generator.addButton(new JButton("Contraseña olvidada"));
		
		JPanel form = generator.generateForm();
		setLayout(new FlowLayout());
		add(form);
		
		this.setSize(200, 200);
	}

	private static final long serialVersionUID = 4113492593900193949L;

	@Override
	public void setController(ActionListener c)
	{
	}

	@Override
	public void show(JFrame window)
	{
		Container container = window.getContentPane();
		container.add(this);
		
		window.setVisible(true);
	}


			
		
	
		
}
