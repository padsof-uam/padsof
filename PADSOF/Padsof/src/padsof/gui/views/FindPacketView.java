package padsof.gui.views;

import java.awt.Dimension;
import java.rmi.NoSuchObjectException;
import java.util.*;

import javax.swing.*;

import padsof.gui.controllers.Controller;
import padsof.gui.utils.GroupLayoutHelper;
import padsof.system.Packet;

public class FindPacketView extends View
{

	private JButton modificar;
	private JList<Packet> listPacket;
	private DefaultListModel<Packet> packets;
	

	@SuppressWarnings("unchecked")
	public FindPacketView() throws NoSuchObjectException
	{
		super("Buscar paquete");
		modificar = new JButton("Elegir paquete");
		listPacket = new JList<Packet>();
		listPacket.setAlignmentX(LEFT_ALIGNMENT);

		GroupLayoutHelper midLayoutHelper = new GroupLayoutHelper();

		midLayoutHelper.addColumn(GroupLayoutHelper
				.fluidGenerateGroupLayout(Arrays.asList(modificar),
						Arrays.asList(listPacket))
				.setIsInnerPanel(true)
				.generatePanel());

		JPanel midPanel =  new JPanel();
		midPanel.setLayout(midLayoutHelper.generateLayout(midPanel));

		GroupLayoutHelper mainLayout = new GroupLayoutHelper();
		mainLayout.addColumn(Box.createHorizontalStrut(10));
		mainLayout.addColumn(midPanel);

		mainLayout.setInnerMargins(10, 10, 10, 10);

		this.setMinimumSize(new Dimension(500, 500));
		this.setLayout(mainLayout.generateLayout(this));

	}

	/**
	 * 
	 */
	public void setModel(List<Packet> packets)
	{
		this.packets = new DefaultListModel<Packet>();
		for (Packet aux : packets)
			this.packets.addElement(aux);
		listPacket.setModel(this.packets);
	}

	private static final long serialVersionUID = -5632029553992648537L;

	@Override
	public <V extends View> void setController(Controller<V> c)
	{
		this.modificar.setActionCommand("Modificar");
		this.modificar.addActionListener(c);
	}
}
