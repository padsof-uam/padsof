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
	private JButton crear;
	private JList<Packet> listPacket;
	private DefaultListModel<Packet> packets;
	

	public Packet getSelectedPacket(){
		if (listPacket == null)
			return null;
		return listPacket.getSelectedValue();
	}
	@SuppressWarnings("unchecked")
	public FindPacketView() throws NoSuchObjectException
	{
		super("Buscar paquete");
		modificar = new JButton("Elegir paquete");
		crear = new JButton ("Crear uno nuevo");
		listPacket = new JList<Packet>();
		listPacket.setAlignmentX(LEFT_ALIGNMENT);
		
		GroupLayoutHelper midLayoutHelper = new GroupLayoutHelper();

		midLayoutHelper.addColumn(GroupLayoutHelper
				.fluidGenerateGroupLayout(Arrays.asList(modificar),
						Arrays.asList(Box.createHorizontalStrut(20)),
						Arrays.asList(listPacket))
				.setIsInnerPanel(true)
				.generatePanel(),
				crear);

		
		JPanel midPanel =  new JPanel();
		midPanel.setLayout(midLayoutHelper.generateLayout(midPanel));

		GroupLayoutHelper mainLayout = new GroupLayoutHelper();
		mainLayout.addColumn(Box.createGlue());
		mainLayout.addColumn(midPanel);
		mainLayout.addColumn(Box.createGlue());
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
		this.modificar.setActionCommand("Nuevo");
		this.modificar.addActionListener(c);
		this.modificar.setActionCommand("Existente");
		this.modificar.addActionListener(c);

	}
}
