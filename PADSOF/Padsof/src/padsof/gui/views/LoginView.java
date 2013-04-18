package padsof.gui.views;

import java.awt.FlowLayout;

import javax.swing.*;

import padsof.gui.controllers.Controller;
import padsof.gui.utils.FormGenerator;

public class LoginView extends View
{
	
	public LoginView()
	{
		super("Login");
		
		FormGenerator generator = new FormGenerator();
		generator.setTitle("Entrar a la aplicación");
		
		generator.addFields("Usuario","Contraseña");
		
		generator.addButton(new JButton("Entrar"));
		generator.addButton(new JButton("Contraseña olvidada"));
		generator.setInnerMargins(8, 8, 8, 8);
		JPanel form = generator.generateForm();
		setLayout(new FlowLayout());
		add(form);
		
		this.setSize(200, 200);
	}

	private static final long serialVersionUID = 4113492593900193949L;

	@Override
	public <V extends View> void setController(Controller<V> c)
	{
		// TODO Auto-generated method stub
		
	}	
}
