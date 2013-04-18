package padsof.gui.views;

import java.awt.*;
import java.awt.event.ActionListener;
import java.rmi.NoSuchObjectException;

import javax.swing.*;

import padsof.gui.utils.GroupLayoutHelper;

public class BookingView extends View
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5982811955870714412L;

	public BookingView() throws NoSuchObjectException
	{

		super("Booking View");

		GroupLayoutHelper mainLayout = new GroupLayoutHelper();

		JLabel lblAdd = new JLabel("Añadir");

		JButton btnHotel = new JButton("Hotel");
		JButton btnVuelo = new JButton("Vuelo");
		JButton btnViaje = new JButton("Viaje organizado");

		JPanel leftPanel = new JPanel();
		GroupLayoutHelper leftLayoutHelper = new GroupLayoutHelper();

		leftLayoutHelper.addColumn(lblAdd, btnHotel, btnVuelo, btnViaje);

		leftLayoutHelper.linkVerticalSize(btnHotel, btnVuelo, btnViaje);
		
		leftPanel.setLayout(leftLayoutHelper.generateLayout(leftPanel));

		String[] data = { "test", "it", "darling" };
		JList<String> packet = new JList<String>(data);
        add( new JScrollPane( packet ) );

		JLabel lblPacket = new JLabel("<html>Elementos del paquete</html>");

		JPanel rightPanel = new JPanel();
		GroupLayoutHelper rightLayoutHelper = new GroupLayoutHelper();

		rightLayoutHelper.addColumn(lblPacket, packet);

		rightPanel.setLayout(rightLayoutHelper.generateLayout(rightPanel));

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
}
