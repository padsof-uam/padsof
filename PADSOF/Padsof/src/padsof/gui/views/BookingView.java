package padsof.gui.views;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.*;

import padsof.gui.utils.GroupLayoutHelper;

public class BookingView extends View
{

	public BookingView (){
		
		super("Booking View");
		
		GroupLayoutHelper mainLayout = new GroupLayoutHelper();
		
		JLabel lblAdd = new JLabel("Añadir");

		JButton btnHotel = new JButton ("Hotel");
		JButton btnVuelo = new JButton ("Vuelo");
		JButton btnViaje = new JButton ("Viaje organizado");
		
		JPanel leftPanel = new JPanel();
		GroupLayoutHelper leftLayoutHelper = new GroupLayoutHelper();
		
		leftLayoutHelper.addColumn(lblAdd,btnHotel,btnVuelo,btnViaje);
		
		leftPanel.setLayout(leftLayoutHelper.generateLayout(leftPanel));
		
		String[] data = { "test", "it", "darling" };
		JComboBox<String> packet = new JComboBox<String>(data);
		
		JLabel lblPacket = new JLabel ("<html>Elementos del paquete</html>");
		
		JPanel rightPanel = new JPanel();
		GroupLayoutHelper rightLayoutHelper = new GroupLayoutHelper();
		
		
		rightLayoutHelper.addColumn(lblPacket,packet);
		
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

	@Override
	public void show(JFrame window)
	{
		// TODO Auto-generated method stub

	}

}
