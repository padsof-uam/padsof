package padsof.gui.views;

import java.awt.*;
import java.awt.event.ActionListener;
import java.rmi.NoSuchObjectException;

import javax.swing.*;

import padsof.gui.utils.*;

public class FindHotelView extends View
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4300658132199468882L;

	public FindHotelView() throws NoSuchObjectException
	{
		super("Buscar hotel");
		GroupLayoutHelper mainLayout = new GroupLayoutHelper();

		FormGenerator generator = new FormGenerator();

		GroupLayoutHelper midLayoutHelper = new GroupLayoutHelper();
		JPanel midPanel = new JPanel();
		JLabel lblHotel = new JLabel("<html><b><u><big>Hotel</b></u></html>");
		// JLabel lblFechas = new JLabel("Rango de fechas");
		generator.addFields("Pais", "Ciudad", "Fecha inicial", "Fecha final",
				"Nombre", "CP", "Categoría", "Precio Simple mínimo",
				"Precio Simple máximo", "Precio Doble mínimo",
				"Precio Doble máximo", "Precio Triple mínimo",
				"Precio Triple máximo", "Suplemento Desayuno", "Supl. MP",
				"Supl. PC");
		generator.addButton(new JButton("Buscar"));

		JPanel form = generator.generateForm();
		setLayout(new FlowLayout());
		add(form);

		midLayoutHelper.addColumn(lblHotel, generator.generateForm());

		midPanel.setLayout(midLayoutHelper.generateLayout(midPanel));

		mainLayout.addColumn(Box.createHorizontalStrut(10));
		mainLayout.addColumn(midPanel);
		mainLayout.addColumn(Box.createHorizontalStrut(10));

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
		Container container = window.getContentPane();
		container.add(this);
		window.setMinimumSize(new Dimension(550, 220));

		window.setVisible(true);
	}

}