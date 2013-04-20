package padsof.gui.views;

import java.awt.Dimension;
import java.rmi.NoSuchObjectException;
import java.util.List;

import javax.swing.*;

import padsof.gui.NavigateButton;
import padsof.gui.controllers.Controller;
import padsof.gui.utils.GroupLayoutHelper;
import padsof.system.Packet;

public class BookingView extends View
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5982811955870714412L;

	private JList<Packet> packet;
	private DefaultListModel<Packet> packets;

	private NavigateButton btnHotel;

	private NavigateButton btnVuelo;

	private NavigateButton btnViaje;

	public void setModel(List<Packet> packets)
	{
		this.packets = new DefaultListModel<Packet>();
		for (Packet aux : packets)
			this.packets.addElement(aux);
		packet.setModel(this.packets);
	}

	/**
	 * Tendria que tener un argumento que fuese el paquete al que esta asociado o algo asi?
	 * @throws NoSuchObjectException
	 */
	public BookingView() throws NoSuchObjectException
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
		
		packet = new JList<Packet>();
		packet.setAlignmentX(LEFT_ALIGNMENT);

		JLabel lblPacket = new JLabel("<html>Elementos del paquete</html>");

		
		rightLayoutHelper.addColumn(lblPacket, packet);

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
