package padsof.gui.views;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

public class SearchPacketView extends View
{
	private JButton forgotPw;
	private JPasswordField pass;
	private JTextField usuario;
	private JButton login;
	
	public SearchPacketView()
	{
		super("Login");
		
		this.setLayout(new BorderLayout(0, 0));
		
		Component horizontalStrut = Box.createHorizontalStrut(120);
		add(horizontalStrut, BorderLayout.WEST);
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		Component verticalStrut = Box.createVerticalStrut(8);
		panel.add(verticalStrut);
		
		JLabel lblVendedorX = new JLabel("Vendedor: X   ");
		lblVendedorX.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panel.add(lblVendedorX);
		
		Component verticalStrut_1 = Box.createVerticalStrut(8);
		panel.add(verticalStrut_1);
		
		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		JLabel lblDniconLetra = new JLabel("DNI (con letra):");
		lblDniconLetra.setAlignmentY(Component.TOP_ALIGNMENT);
		panel_1.add(lblDniconLetra);
		
		JTextField textField = new JTextField();
		textField.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel_1.add(textField);
		textField.setColumns(10);
		textField.setMaximumSize(new Dimension(200, 28));
		
		JButton btnBuscar = new JButton("Buscar");
		panel_1.add(btnBuscar);
		
		String data[] = {"test Add add", "Dos", "tres"};
		JList<String> list = new JList<String>(data);
		list.setAlignmentX(Component.LEFT_ALIGNMENT);
		list.setMinimumSize(new Dimension(200, 50));
		panel_1.add(list);
		
		Component verticalGlue = Box.createVerticalGlue();
		panel_1.add(verticalGlue);
		
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
