package padsof.gui.views;

import java.awt.Dimension;
import java.rmi.NoSuchObjectException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.*;
import javax.swing.event.*;

import padsof.bookings.Booking;
import padsof.gui.*;
import padsof.gui.controllers.Controller;
import padsof.gui.utils.*;
import padsof.system.*;

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
	private NavigateButton btnImserso;
	private JButton cancelButton;
	private JButton refreshButton;
	private JButton confirmButton;
	private JLabel closedLabel;

	public void setModel(List<Booking> bookings)
	{
		this.packets = new DefaultListModel<Booking>();
		for (Booking aux : bookings)
			this.packets.addElement(aux);
		elements.setModel(this.packets);
	}

	/**
	 * Tendria que tener un argumento que fuese el paquete al que esta asociado
	 * o algo asi?
	 * 
	 * @throws NoSuchObjectException
	 * @throws SQLException
	 */
	public BookingView()
	{

		super("Booking View");

		GroupLayoutHelper mainLayout = new GroupLayoutHelper();

		/**
		 * Left Panel
		 */
		JLabel lblAdd = new JLabel("Añadir");
		GuiUtils.applyTitleStyle(lblAdd);
		
		btnHotel = new NavigateButton("Hotel", FindHotelView.class);
		btnVuelo = new NavigateButton("Vuelo", FindFlightView.class);
		btnViaje = new NavigateButton("Viaje organizado", FindTravelView.class);
		btnImserso = new NavigateButton("Viaje IMSERSO",
				FindImsersoTravelView.class);
		Client cliente = Application.getInstance().getClient();

		closedLabel = new JLabel("Paquete cerrado");
		closedLabel.setVisible(false);
		
		cancelButton = new JButton("Cancelar");
		confirmButton = new JButton("Confirmar");
		refreshButton = new JButton("Refrescar");
		
		btnImserso.setEnabled(ImsersoClient.class.isInstance(cliente));

		JPanel leftPanel = new JPanel();
		GroupLayoutHelper leftLayoutHelper = new GroupLayoutHelper();

		leftLayoutHelper.addColumn(lblAdd, closedLabel, btnHotel, btnVuelo, btnViaje,
				btnImserso);
		leftLayoutHelper.linkVerticalSize(btnHotel, btnVuelo, btnViaje,
				btnImserso);

		leftLayoutHelper.setAsLayoutOf(leftPanel);

		/**
		 * Right panel
		 */
		JPanel rightPanel = new JPanel();

		GroupLayoutHelper rightLayoutHelper = new GroupLayoutHelper();

		elements = new JList<Booking>();
		elements.setAlignmentX(LEFT_ALIGNMENT);

		JLabel lblPacket = new JLabel("Elementos del paquete:");
		GuiUtils.applyTitleStyle(lblPacket);

		elements.addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				cancelButton.setEnabled(elements.getSelectedValue() != null
						&& !elements.getSelectedValue().isPayed());
				confirmButton.setEnabled(elements.getSelectedValue() != null
						&& !elements.getSelectedValue().isPayed());
			}
		});

		rightLayoutHelper.addColumn(lblPacket, new JScrollPane(elements),
				GuiUtils.generateLinePanel(cancelButton, confirmButton,
						refreshButton));

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
		cancelButton.setActionCommand("Cancel");
		confirmButton.setActionCommand("Confirm");

		cancelButton.addActionListener(c);
		confirmButton.addActionListener(c);
	}

	public void setIsClosed(boolean closed)
	{
		if(closed)
		{
			closedLabel.setVisible(true);
			btnHotel.setEnabled(false);
			btnVuelo.setEnabled(false);
			btnViaje.setEnabled(false);
			btnImserso.setEnabled(false);
		}
	}
	
	public Booking getSelectedItem()
	{
		return elements.getSelectedValue();
	}
}
