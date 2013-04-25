package padsof.gui.views;

import java.rmi.NoSuchObjectException;
import java.util.List;

import javax.swing.*;
import javax.swing.event.*;

import padsof.gui.Application;
import padsof.gui.controllers.Controller;
import padsof.gui.utils.*;
import padsof.system.Packet;

public class SelectPacketView extends View
{

	private JButton modificar;
	private JButton crear;
	private JList<Packet> listPacket;
	private DefaultListModel<Packet> packets;
	private JButton delete;
	private JButton close;
	
	public Packet getSelectedPacket(){
		if (listPacket == null)
			return null;
		
		return listPacket.getSelectedValue();
	}
	
	public SelectPacketView() throws NoSuchObjectException
	{
		super("Buscar paquete");
		modificar = new JButton("Elegir paquete");
		delete = new JButton("Eliminar paquete");
		crear = new JButton ("Crear uno nuevo");
		close = new JButton("Cerrar paquete");
		listPacket = new JList<Packet>();
		JLabel title = new JLabel("Seleccionar paquete");
		GuiUtils.applyTitleStyle(title);
		modificar.setEnabled(false);
		delete.setEnabled(false);
		close.setEnabled(false);
		
		listPacket.addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				modificar.setEnabled(listPacket.getSelectedValue() != null);
				delete.setEnabled(listPacket.getSelectedValue() != null);
				close.setEnabled(listPacket.getSelectedValue() != null && !listPacket.getSelectedValue().IsClose());
				if(listPacket.getSelectedValue() != null)
					Application.getInstance().setDefaultButton(modificar);
			}
		});
		
		GroupLayoutHelper layout = new GroupLayoutHelper();

		layout.addColumn(
				title,
				new JScrollPane(listPacket),
				GuiUtils.generateButtonPanel(modificar, crear, delete, close)
				);
		
		layout.setAsLayoutOf(this);
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
		this.crear.setActionCommand("Nuevo");
		this.crear.addActionListener(c);
		this.modificar.setActionCommand("Existente");
		this.modificar.addActionListener(c);
		this.delete.setActionCommand("Delete");
		this.delete.addActionListener(c);
		this.close.setActionCommand("Close");
		this.close.addActionListener(c);

	}
}
