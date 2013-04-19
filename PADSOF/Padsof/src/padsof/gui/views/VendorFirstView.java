package padsof.gui.views;

import java.awt.*;
import java.awt.event.ActionListener;
import java.rmi.NoSuchObjectException;

import javax.swing.*;

import padsof.gui.controllers.Controller;
import padsof.gui.utils.GroupLayoutHelper;

public class VendorFirstView extends View
{

	public VendorFirstView() throws NoSuchObjectException
	{
		super("Vendedor");
		
		JButton addCliente = new JButton ("<html><big><p align="+"justify"+"><b>Dar de alta</big></b><br><p align="+"justify"+">a un cliente</html>");
		JButton addReserva = new JButton ("<html><big><p align="+"justify"+"><b>Añadir reserva</big></b><br><p align="+"justify"+">a un paquete existente</html>");
		JButton newPacket = new JButton ("<html><big><p align="+"justify"+"><b>Crear paquete</html>");
		
		GroupLayoutHelper mainLayout = new GroupLayoutHelper();
		JPanel mainpanel = new JPanel();
		
		mainLayout.addColumn(Box.createGlue(), Box.createGlue(), Box.createGlue());
		mainLayout.addColumn(addCliente, addReserva,newPacket);
		mainLayout.addColumn(Box.createGlue(), Box.createGlue(), Box.createGlue());
		
		mainpanel.setLayout(mainLayout.generateLayout(mainpanel));
		mainLayout.linkVerticalSize(addCliente,addReserva, newPacket);
		mainLayout.linkHorizontalSize(addCliente,addReserva, newPacket);
		
		mainpanel.setLayout(mainLayout.generateLayout(mainpanel));
		

		mainLayout.setInnerMargins(10, 10, 10, 10);
		
		this.setMinimumSize(new Dimension(500, 500));
		this.setLayout(mainLayout.generateLayout(this));
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1993546369866479786L;

	@Override
	public <V extends View> void setController(Controller<V> c) {
		// TODO Auto-generated method stub
		
	}


}
