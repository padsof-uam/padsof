package padsof.gui.views;

import java.awt.*;
import java.awt.event.ActionListener;
import java.rmi.NoSuchObjectException;
import java.util.Arrays;

import javax.swing.*;

import padsof.gui.layout.GroupLayoutHelper;

import com.toedter.calendar.JDateChooser;

public class AdminView extends View
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3957591110532891073L;

	@SuppressWarnings("unchecked")
	public AdminView() throws NoSuchObjectException
	{
		super("Admin");
		
		setLayout(new BorderLayout());
		
		JPanel container = new JPanel();
		
		container.setLayout(new GridLayout(1,2));
		
		JLabel lblEstadisticas = new JLabel("Estadísticas");
		lblEstadisticas.setFont(lblEstadisticas.getFont().deriveFont((float) 20.0));
		
		JLabel lblVendors = new JLabel("Vendedores: ");
		
		String[] data = { "test", "it", "darling" };
		JComboBox<String> vendors = new JComboBox<String>(data);
		
		JDateChooser fromChooser = new JDateChooser();
		JDateChooser toChooser = new JDateChooser();
		
		JLabel fromLabel = new JLabel("Desde: ");
		JLabel toLabel = new JLabel("Hasta: ");
		
		fromLabel.setLabelFor(fromChooser);
		toLabel.setLabelFor(toChooser);
		
		JButton viewButton = new JButton("Ver");
		
		JPanel leftPanel = new JPanel();
		GroupLayout layout = new GroupLayout(leftPanel);
		leftPanel.setLayout(layout);
		
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(lblEstadisticas)
				.addComponent(lblVendors)
				.addComponent(vendors)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(fromLabel)
										.addComponent(toLabel)
										)
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(fromChooser)
										.addComponent(toChooser)
										)
								)
						)
			    .addComponent(viewButton)
		);
		
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(lblEstadisticas)
				.addComponent(lblVendors)
				.addComponent(vendors)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(fromLabel)
										.addComponent(fromChooser))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(toLabel)
										.addComponent(toChooser))))
			    .addComponent(viewButton)
		); 
		
		layout.linkSize(SwingConstants.HORIZONTAL, viewButton);
		layout.linkSize(SwingConstants.VERTICAL, toChooser, fromChooser, vendors);
		
		container.add(leftPanel);
		
		JPanel rightPanel = new JPanel();
		GroupLayoutHelper rightLayoutHelper = new GroupLayoutHelper();
		
		JLabel lblNuevo = new JLabel("Nuevo vendedor");
		lblNuevo.setFont(lblEstadisticas.getFont().deriveFont((float) 20.0));
		
		JLabel lblNombre = new JLabel("Nombre: ");
		JLabel lblUsuario = new JLabel("Usuario: ");
		JLabel lblPass = new JLabel("Contraseña: ");
		
		JTextField txtNombre = new JTextField();
		JTextField txtUsuario = new JTextField();
		JTextField txtPass = new JTextField();
		
		lblNombre.setLabelFor(txtNombre);
		lblUsuario.setLabelFor(txtUsuario);
		lblPass.setLabelFor(txtPass);
		
		JButton createButton = new JButton("Crear");
		
		rightLayoutHelper.addColumn(
				lblNuevo, 
				GroupLayoutHelper.fluidGenerateGroupLayout(
						Arrays.asList(lblUsuario, lblNombre, lblPass),
						Arrays.asList(txtUsuario, txtNombre, txtPass)
						)
						.linkVerticalSize(txtUsuario, txtNombre, txtPass)
						.generatePanel(),
				createButton
		);
	
		rightPanel.setLayout(rightLayoutHelper.generateLayout(rightPanel));
		
		container.add(rightPanel);
		
		
		add(Box.createVerticalStrut(8), BorderLayout.NORTH);
		add(Box.createHorizontalStrut(8), BorderLayout.WEST);

		add(Box.createVerticalStrut(8), BorderLayout.SOUTH);
		add(Box.createHorizontalStrut(8), BorderLayout.EAST);
		
		add(container, BorderLayout.CENTER);
	}

	@Override
	public void setController(ActionListener c)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void show(JFrame window)
	{
		Container container = window.getContentPane();
		container.add(this);
		
		window.setVisible(true);
	}

}
