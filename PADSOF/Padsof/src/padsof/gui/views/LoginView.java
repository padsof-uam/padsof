package padsof.gui.views;

import java.awt.FlowLayout;

import javax.swing.*;

import padsof.gui.controllers.Controller;
import padsof.gui.utils.FormGenerator;

public class LoginView extends View
{
	JButton loginButton;
	FormGenerator generator;
	public LoginView()
	{
		super("Login");
		
		generator = new FormGenerator();
		generator.setTitle("Entrar a la aplicación");
		
		generator.addFields("Usuario","Contraseña");
		loginButton = new JButton("Entrar");
		
		generator.addButton(loginButton);
		generator.addButton(new JButton("Contraseña olvidada"));
		generator.setInnerMargins(8, 8, 8, 8);
		JPanel form = generator.generateForm();
		setLayout(new FlowLayout());
		add(form);
	}

	private static final long serialVersionUID = 4113492593900193949L;

	public String getUser()
	{
		return generator.getValueFor("Usuario");
	}
	
	public String getPassword()
	{
		return generator.getValueFor("Contraseña");
	}
	
	@Override
	public <V extends View> void setController(Controller<V> c)
	{
		loginButton.addActionListener(c);
	}	
}
