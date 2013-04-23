package padsof.gui.views;

import java.awt.Dimension;
import java.rmi.NoSuchObjectException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.*;

import padsof.bookings.Booking;
import padsof.gui.*;
import padsof.gui.controllers.Controller;
import padsof.gui.utils.GroupLayoutHelper;
import padsof.system.Packet;

public class BookingView extends View
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5982811955870714412L;

	private JList<Booking> elements;
	private DefaultListModel<Booking> packets;

	private NavigateButton btnHotel;

	private NavigateButton btnVuelo;

	private NavigateButton btnViaje;

	public void setModel(List<Booking> bookings)
	{
		this.packets = new DefaultListModel<Booking>();
		for (Booking aux : bookings)
			this.packets.addElement(aux);
		elements.setModel(this.packets);
	}

	/**
	 * Tendria que tener un argumento que fuese el paquete al que esta asociado o algo asi?
	 * @throws NoSuchObjectException
	 * @throws SQLException 
	 */
	public BookingView() throws NoSuchObjectException, SQLException
	{

		super("Booking View");

		GroupLayoutHelper mainLayout = new GroupLayoutHelper();

		/**
		 * Left Panel
		 */
		JLabel lblAdd = new JLabel("Añadir");

		btnHotel = new NavigateButton("Hotel",FindHotelView.class);
		btnVuelo = new NavigateButton("Vuelo",FindFlightView.class);
		btnViaje = new NavigateButton("Viaje organizado",FindTravelView.class);

		JPanel leftPanel = new JPanel();
		GroupLayoutHelper leftLayoutHelper = new GroupLayoutHelper();

		leftLayoutHelper.addColumn(lblAdd, btnHotel, btnVuelo, btnViaje);

		leftLayoutHelper.linkVerticalSize(btnHotel, btnVuelo, btnViaje);

		leftPanel.setLayout(leftLayoutHelper.generateLayout(leftPanel));

		/**
		 * Right panel
		 */
		JPanel rightPanel = new JPanel();
		
		GroupLayoutHelper rightLayoutHelper = new GroupLayoutHelper();
		
		Packet actualPacket = Application.getInstance().getActualPacket();

		this.setModel(actualPacket.getBookings());
		elements = new JList<Booking>();
		elements.setAlignmentX(LEFT_ALIGNMENT);

		JLabel lblPacket = new JLabel("<html>Elementos del paquete</html>");

		
		rightLayoutHelper.addColumn(lblPacket, elements);

		rightPanel.setLayout(rightLayoutHelper.generateLayout(rightPanel));

		/**
		 * Main panel
		 */
		mainLayout.addColumn(leftPanel);
		mainLayout.addColumn(Box.createHorizontalStrut(10));
		mainLayout.addColumn(rightPanel);

		mainLayout.setInnerMargins(10, 10, 10, 10);

		this.setMinimumSize(new Dimension(500, 500));
		this.setLayout(mainLayout.generateLayout(this));

	}

	@Override
	public <V extends View> void setController(Controller<V> c)
	{

	}
}
