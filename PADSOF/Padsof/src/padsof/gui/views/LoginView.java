package padsof.gui.views;

import java.awt.FlowLayout;

import javax.swing.*;

import padsof.gui.Application;
import padsof.gui.controllers.Controller;
import padsof.gui.utils.FormGenerator;

public class LoginView extends View
{
	private JButton loginButton;
	private FormGenerator generator;
	private JButton forgotButton;
	public LoginView()
	{
		super("Login");
		
		generator = new FormGenerator();
		generator.setTitle("Entrar a la aplicación");
		generator.addFields("Usuario","Contraseña");
		
		loginButton = new JButton("Entrar");
		Application.getInstance().setDefaultButton(loginButton);
		generator.addButton(loginButton);
		forgotButton = new JButton("Contraseña olvidada");
		generator.addButton(forgotButton);
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
		loginButton.setActionCommand("login");
		loginButton.addActionListener(c);
		
		forgotButton.setActionCommand("forgot");
		forgotButton.addActionListener(c);		
	}	
}
