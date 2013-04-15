package padsof.gui.views;

import java.awt.*;
import java.awt.event.ActionListener;
import java.rmi.NoSuchObjectException;
import java.util.Arrays;

import javax.swing.*;

import padsof.gui.utils.*;

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

		GroupLayoutHelper mainLayout = new GroupLayoutHelper();
		
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
		
		GroupLayoutHelper leftLayoutHelper = new GroupLayoutHelper();
		
		leftLayoutHelper.addColumn(
				lblEstadisticas,
				lblVendors,
				vendors,
				GroupLayoutHelper.fluidGenerateGroupLayout(
						Arrays.asList(fromLabel, fromChooser),
						Arrays.asList(toLabel, toChooser)
						)
						.linkHorizontalSize(fromChooser, toChooser)
						.linkVerticalSize(fromChooser, toChooser)
						.setIsInnerPanel(true)
						.generatePanel()
				,
				viewButton
		);
		
		leftLayoutHelper.linkVerticalSize(viewButton, vendors);
		leftLayoutHelper.linkHorizontalSize(vendors);
		
		leftPanel.setLayout(leftLayoutHelper.generateLayout(leftPanel));
		
		FormGenerator generator = new FormGenerator();
		
		generator.addFields("Nombre", "Usuario", "Contraseña");
		generator.setTitle("Nuevo vendedor");
		generator.setIsInnerPanel(true);
		generator.setButton(new JButton("Crear"));
		JPanel rightPanel = generator.generateForm();
		
		mainLayout.addColumn(leftPanel);
		mainLayout.addColumn(Box.createHorizontalStrut(10));
		mainLayout.addColumn(rightPanel);
		
		mainLayout.setInnerMargins(10, 10, 10, 10);
		
		this.setMinimumSize(new Dimension(500, 500));
		this.setLayout(mainLayout.generateLayout(this));
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
		window.setMinimumSize(new Dimension(550, 220));
		
		window.setVisible(true);
	}

}
