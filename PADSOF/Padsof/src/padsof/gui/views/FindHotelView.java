package padsof.gui.views;

import java.awt.*;
import java.rmi.NoSuchObjectException;

import javax.swing.*;
import javax.swing.event.*;

import padsof.gui.controllers.Controller;
import padsof.gui.utils.*;
import padsof.services.Hotel;

import java.util.*;
import java.util.List;

public class FindHotelView extends View
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4300658132199468882L;
	private JButton btnSearch;
	private JList<Hotel> hotelList;
	private JButton btnBook;
	private JButton btnSeeInfo;
	private FormGenerator generator;
	private JComboBox<Integer> simples;
	private JComboBox<Integer> dobles;
	private JComboBox<Integer> triples;

	public Integer getSimples()
	{
		return (Integer) simples.getSelectedItem();
	}

	public Integer getDobles()
	{
		return (Integer) dobles.getSelectedItem();
	}

	public Integer getTriples()
	{
		return (Integer) triples.getSelectedItem();
	}

	public String getCity()
	{
		return generator.getValueFor("Ciudad");
	}

	public String getCountry()
	{
		return generator.getValueFor("País");
	}

	public Date getStartDate()
	{
		return generator.getDateFor("Fecha inicial");
	}

	public Date getEndDate()
	{
		return generator.getDateFor("Fecha final");
	}

	public String getPrice()
	{
		return generator.getValueFor("Precio máximo");
	}

	public Hotel getSelectedHotel()
	{
		return hotelList.getSelectedValue();
	}

	@SuppressWarnings("unchecked")
	public FindHotelView() throws NoSuchObjectException
	{
		super("Buscar hotel");
		GroupLayoutHelper mainLayout = new GroupLayoutHelper();

		generator = new FormGenerator();

		GroupLayoutHelper midLayoutHelper = new GroupLayoutHelper();
		JPanel midPanel = new JPanel();

		generator.setTitle("Hoteles");
		generator.addFields("País", "Ciudad", "Fecha inicial", "Fecha final",
				"Precio máximo");

		btnSearch = new JButton("Buscar");

		hotelList = new JList<Hotel>();
		btnBook = new JButton("Reservar");
		btnBook.setEnabled(false);

		btnSeeInfo = new JButton("Ver");
		btnSeeInfo.setEnabled(false);

		hotelList.addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				btnBook.setEnabled(hotelList.getSelectedValue() != null);
				btnSeeInfo.setEnabled(hotelList.getSelectedValue() != null);
			}

		});

		simples = new JComboBox<Integer>();
		dobles = new JComboBox<Integer>();
		triples = new JComboBox<Integer>();

		for (int i = 0; i <= 10; ++i)
		{
			simples.addItem(i);
			dobles.addItem(i);
			triples.addItem(i);
		}

		JPanel form = generator.generateForm();
		setLayout(new FlowLayout());
		add(form);

		JLabel lblSimples = new JLabel("Simples");
		JLabel lblDobles = new JLabel("Dobles");
		JLabel lblTriples = new JLabel("Triples");
		midLayoutHelper.addColumn(
				generator.generateForm(),
				btnSearch,
				GroupLayoutHelper.fluidGenerateGroupLayout(
						Arrays.asList(lblSimples, simples),
						Arrays.asList(lblDobles, dobles),
						Arrays.asList(lblTriples, triples)).generatePanel());

		midLayoutHelper.addColumn(new JScrollPane(hotelList), btnBook,
				btnSeeInfo);
		midPanel.setLayout(midLayoutHelper.generateLayout(midPanel));

		mainLayout.addColumn(Box.createHorizontalStrut(10));
		mainLayout.addColumn(midPanel);
		mainLayout.addColumn(Box.createHorizontalStrut(10));

		mainLayout.setInnerMargins(10, 10, 10, 10);

		this.setMinimumSize(new Dimension(500, 500));
		this.setLayout(mainLayout.generateLayout(this));

	}

	public void setModel(List<Hotel> hotels)
	{
		DefaultListModel<Hotel> model = new DefaultListModel<Hotel>();

		for (Hotel hotel : hotels)
			model.addElement(hotel);

		hotelList.setModel(model);
	}

	@Override
	public <V extends View> void setController(Controller<V> c)
	{
		btnSearch.setActionCommand("Search");
		btnSearch.addActionListener(c);

		btnBook.setActionCommand("Book");
		btnBook.addActionListener(c);

		btnSeeInfo.setActionCommand("Ver");
		btnSeeInfo.addActionListener(c);
	}
}
