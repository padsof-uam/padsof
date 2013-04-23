package padsof.gui.views;

import java.awt.FlowLayout;
import java.rmi.NoSuchObjectException;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;

import padsof.gui.controllers.*;
import padsof.gui.controllers.FindFlightController.AirportInfoWrapper;
import padsof.gui.controllers.FindFlightController.FlightInfoWrapper;
import padsof.gui.utils.*;

public class FindFlightView extends View
{

	private JList<FlightInfoWrapper> flightList;
	private JButton btnBook;
	private JButton btnSearch;
	private FormGenerator generator;

	public Date getStartDate()
	{
		return generator.getDatefor("Fecha Inicial");
	}
	
	public Date getEndDate()
	{
		return generator.getDatefor("Fecha Final");
	}
	
	public AirportInfoWrapper getOrigin()
	{
		return (AirportInfoWrapper) generator.getSelectedOption("Origen");
	}
	
	public AirportInfoWrapper getDestination()
	{
		return (AirportInfoWrapper) generator.getSelectedOption("Destino");
	}

	public String getMaximumPrice()
	{
		return generator.getValueFor("Precio máximo");
	}
	
	public String getPersons()
	{
		return generator.getValueFor("Plazas");
	}
	
	public FindFlightView() throws NoSuchObjectException
	{
		super("Buscar vuelo");

		GroupLayoutHelper mainLayout = new GroupLayoutHelper();

		generator = new FormGenerator();

		GroupLayoutHelper midLayoutHelper = new GroupLayoutHelper();
		JPanel midPanel = new JPanel();

		generator.setTitle("Vuelos");
		generator.addOptionField("Origen", null);
		generator.addOptionField("Destino", null);
		generator.addFields("Fecha Inicial", "Fecha Final","Precio máximo", "Plazas");

		flightList = new JList<FlightInfoWrapper>();
		flightList.setAutoscrolls(true);
		
		btnSearch = new JButton("Buscar");
		btnBook = new JButton("Reservar");
		btnBook.setEnabled(false);

		flightList.addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				btnBook.setEnabled(flightList.getSelectedValue() != null);
			}

		});

		midLayoutHelper.addColumn(generator.generateForm(), btnSearch, Box.createGlue());
		midLayoutHelper.addColumn(new JScrollPane(flightList), btnBook, Box.createHorizontalStrut(300));

		midPanel.setLayout(midLayoutHelper.generateLayout(midPanel));

		mainLayout.addColumn(Box.createHorizontalStrut(10));
		mainLayout.addColumn(midPanel);
		mainLayout.addColumn(Box.createHorizontalStrut(10));

		mainLayout.setInnerMargins(10, 10, 10, 10);

		this.setLayout(mainLayout.generateLayout(this));
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 9012509925372645373L;

	public FlightInfoWrapper getSelectedFlight()
	{
		return flightList.getSelectedValue();
	}

	public void setAirports(List<AirportInfoWrapper> airports)
	{
		generator.setOptionsModel("Origen", airports);
		generator.setOptionsModel("Destino", airports);
	}
	
	public void setResult(List<FlightInfoWrapper> flights)
	{
		DefaultListModel<FlightInfoWrapper> model = new DefaultListModel<FlightInfoWrapper>();
		
		for(FlightInfoWrapper flight : flights)
			model.addElement(flight);
		
		flightList.setModel(model);
	}
	
	@Override
	public <V extends View> void setController(Controller<V> c)
	{
		btnBook.setActionCommand("Book");
		btnSearch.setActionCommand("Search");
		
		btnBook.addActionListener(c);
		btnSearch.addActionListener(c);
	}
}
