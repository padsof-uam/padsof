package padsof.gui.views;

/**
 * 
 */
import java.rmi.NoSuchObjectException;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;

import padsof.gui.controllers.Controller;
import padsof.gui.utils.*;
import padsof.services.*;
public class FindImsersoTravelView extends View
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3047669393550390186L;
	private JList<ImsersoTravel> travelList;
	private JButton btnBook;
	private JButton btnSearch;
	private FormGenerator generator;

	public FindImsersoTravelView() throws NoSuchObjectException
	{
		super("Buscar Viaje IMSERSO");

		generator = new FormGenerator();

		GroupLayoutHelper midLayoutHelper = new GroupLayoutHelper();
		btnSearch = new JButton("Buscar");
		btnBook = new JButton("Reservar");
		btnBook.setEnabled(false);
		
		travelList = new JList<ImsersoTravel>();
		
		travelList.addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				btnBook.setEnabled(travelList.getSelectedValue() != null);
			}

		});
		
		generator.setTitle("Viajes IMSERSO");
		generator.addFields("Fecha Inicial", "Fecha Final",
				"Precio máximo");

		midLayoutHelper.addColumn(generator.generateForm(), btnSearch);
		midLayoutHelper.addColumn(new JScrollPane(travelList), btnBook);

		JPanel midPanel = new JPanel();
		
		midPanel.setLayout(midLayoutHelper.generateLayout(midPanel));
		GroupLayoutHelper mainLayout = new GroupLayoutHelper();
		
		mainLayout.addColumn(Box.createHorizontalStrut(10));
		mainLayout.addColumn(midPanel);
		mainLayout.addColumn(Box.createHorizontalStrut(10));

		mainLayout.setInnerMargins(10, 10, 10, 10);

		this.setLayout(mainLayout.generateLayout(this));

	}
	
	public Date getStartDate()
	{
		return generator.getDateFor("Fecha Inicial");
	}
	
	public Date getEndDate()
	{
		return generator.getDateFor("Fecha Final");
	}
	
	public String getMaxPrice()
	{
		return generator.getValueFor("Precio máximo");
	}
	
	public void setResults(List<ImsersoTravel> travels)
	{
		DefaultListModel<ImsersoTravel> model = new DefaultListModel<ImsersoTravel>();
		
		for(ImsersoTravel flight : travels)
			model.addElement(flight);
		
		travelList.setModel(model);
	}

	@Override
	public <V extends View> void setController(Controller<V> c)
	{
		btnBook.setActionCommand("Book");
		btnSearch.setActionCommand("Search");
		
		btnBook.addActionListener(c);
		btnSearch.addActionListener(c);
	}

	public ImsersoTravel getSelectedImsersoTravel()
	{
		return travelList.getSelectedValue();
	}
}
