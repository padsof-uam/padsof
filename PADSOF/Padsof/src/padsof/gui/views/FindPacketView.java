package padsof.gui.views;

import java.awt.*;
import java.awt.event.ActionListener;
import java.rmi.NoSuchObjectException;
import java.util.Arrays;

import javax.swing.*;

import padsof.gui.utils.GroupLayoutHelper;

public class FindPacketView extends View
{

	@SuppressWarnings("unchecked")
	public FindPacketView() throws NoSuchObjectException
	{
		super("Buscar paquete");
		JLabel descripcion = new JLabel(
				"<html><p align="+"justify"+">Introduzca el dni del cliente<br>" +
				"<p align="+"justify"+">para identificar el paquete" +
				"<br><small>con letra</html>");
		JTextField JtfDni = new JTextField("");
		JButton btnBuscar = new JButton ("Buscar");
	
		String[] data = { "test", "it", "darling,test it darling, test darling it" };
		JList<String> listPacket = new JList<String>(data);
        add( new JScrollPane( listPacket ) );

        GroupLayoutHelper midLayoutHelper = new GroupLayoutHelper();
		JPanel midPanel = new JPanel();
		
		midLayoutHelper.addColumn(
				descripcion,
				GroupLayoutHelper.fluidGenerateGroupLayout(
						Arrays.asList(JtfDni,btnBuscar)
						)
						.linkHorizontalSize(JtfDni,btnBuscar)
						.linkVerticalSize(JtfDni,btnBuscar)
						.setIsInnerPanel(true)
						.generatePanel()
				);
		GroupLayoutHelper rightLayoutHelper = new GroupLayoutHelper();
		JPanel rightPanel = new JPanel();
		
		rightLayoutHelper.addColumn(listPacket);
		
		rightPanel.setLayout(rightLayoutHelper.generateLayout(rightPanel));
		
		midPanel.setLayout(midLayoutHelper.generateLayout(midPanel));

		GroupLayoutHelper mainLayout = new GroupLayoutHelper();
		mainLayout.addColumn(midPanel);
		mainLayout.addColumn(Box.createHorizontalStrut(10));
		mainLayout.addColumn(rightPanel);

		mainLayout.setInnerMargins(10, 10, 10, 10);

		this.setMinimumSize(new Dimension(500, 500));
		this.setLayout(mainLayout.generateLayout(this));

		

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -5632029553992648537L;

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
