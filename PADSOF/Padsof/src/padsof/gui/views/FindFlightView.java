package padsof.gui.views;

import java.awt.*;
import java.awt.event.ActionListener;
import java.rmi.NoSuchObjectException;

import javax.swing.*;

import padsof.gui.utils.*;

public class FindFlightView extends View
{

	public FindFlightView() throws NoSuchObjectException
	{
		super("Buscar vuelo");

		GroupLayoutHelper mainLayout = new GroupLayoutHelper();

		FormGenerator generator = new FormGenerator();

		GroupLayoutHelper midLayoutHelper = new GroupLayoutHelper();
		JPanel midPanel = new JPanel();

		JLabel lblHotel = new JLabel("<html><b><u><big>Vuelos</b></u></html>");
		//JLabel lblFechas = new JLabel("Rango de fechas");
		generator.addFields("Fecha Inicial", "Fecha Final", "Origen",
				"Destino", "Precio mínimo", "Precio máximo", "Plazas");
		generator.addButton(new JButton("Buscar"));

		JPanel form = generator.generateForm();
		setLayout(new FlowLayout());
		add(form);

		midLayoutHelper
				.addColumn(lblHotel, generator.generateForm());

		midPanel.setLayout(midLayoutHelper.generateLayout(midPanel));

		mainLayout.addColumn(Box.createHorizontalStrut(10));
		mainLayout.addColumn(midPanel);
		mainLayout.addColumn(Box.createHorizontalStrut(10));

		mainLayout.setInnerMargins(10, 10, 10, 10);

		this.setMinimumSize(new Dimension(500, 500));
		this.setLayout(mainLayout.generateLayout(this));
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 9012509925372645373L;

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
