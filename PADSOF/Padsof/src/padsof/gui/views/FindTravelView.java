package padsof.gui.views;

/**
 * 
 */
import java.awt.*;
import java.awt.event.ActionListener;
import java.rmi.NoSuchObjectException;

import javax.swing.*;

import padsof.gui.utils.*;

public class FindTravelView extends View
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3047669393550390186L;

	public FindTravelView() throws NoSuchObjectException
	{
		super("Buscar Viaje Organizado");
		GroupLayoutHelper mainLayout = new GroupLayoutHelper();

		FormGenerator generator = new FormGenerator();

		GroupLayoutHelper midLayoutHelper = new GroupLayoutHelper();
		JPanel midPanel = new JPanel();

		JLabel lblHotel = new JLabel(
				"<html><b><u><big>Viajes Organizados</b></u></html>");
		// JLabel lblFechas = new JLabel("Rango de fechas");
		generator.addFields("Fecha Inicial", "Fecha Final", "Precio mínimo",
				"Precio máximo");
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
}
