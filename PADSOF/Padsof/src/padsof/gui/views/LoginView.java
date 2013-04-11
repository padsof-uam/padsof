package padsof.gui.views;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

public class LoginView extends View
{
	private JButton forgotPw;
	private JPasswordField pass;
	private JTextField usuario;
	private JButton login;
	
	public LoginView()
	{
		super("Login");
		
		this.setLayout(new BorderLayout(0,0));
		
		Box box = new Box(BoxLayout.Y_AXIS);
		add(box);

		usuario = new JTextField();
		usuario.setText("Usuario");
		usuario.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		usuario.setMaximumSize(new Dimension(200, 28));

		pass = new JPasswordField();
		pass.setText("Contraseña");
		pass.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		pass.setMaximumSize(new Dimension(200, 28));

		login = new JButton("Entrar");
		login.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		forgotPw = new JButton("Contraseña olvidada");
		forgotPw.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		
		box.add(Box.createVerticalGlue());
		box.add(usuario);
		box.add(pass);
		box.add(login);
		box.add(forgotPw);
		box.add(Box.createVerticalGlue());
		
		
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
