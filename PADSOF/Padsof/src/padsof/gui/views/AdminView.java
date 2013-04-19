package padsof.gui.views;

import java.rmi.NoSuchObjectException;
import java.util.*;

import javax.swing.*;

import padsof.gui.NavigateButton;
import padsof.gui.controllers.Controller;
import padsof.gui.utils.*;
import padsof.system.Vendor;

import com.toedter.calendar.JDateChooser;

public class AdminView extends View
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3957591110532891073L;

	private JButton createButton;
	private JComboBox<Vendor> vendorList;
	private ComboBoxModel<Vendor> vendors;
	private FormGenerator generator;
	private JDateChooser fromChooser;
	private JDateChooser toChooser;

	private JButton viewButton;
	
	@SuppressWarnings("unchecked")
	public AdminView() throws NoSuchObjectException
	{
		super("Admin");

		GroupLayoutHelper mainLayout = new GroupLayoutHelper();
		
		JLabel lblEstadisticas = new JLabel("Estadísticas");
		lblEstadisticas.setFont(lblEstadisticas.getFont().deriveFont((float) 20.0));
		
		JLabel lblVendors = new JLabel("Vendedores: ");
		
		vendorList = new JComboBox<Vendor>();
		
		fromChooser = new JDateChooser();
		toChooser = new JDateChooser();
		
		JLabel fromLabel = new JLabel("Desde: ");
		JLabel toLabel = new JLabel("Hasta: ");
		
		fromLabel.setLabelFor(fromChooser);
		toLabel.setLabelFor(toChooser);
		
		viewButton = new JButton("Ver");
		
		JPanel leftPanel = new JPanel();
		
		GroupLayoutHelper leftLayoutHelper = new GroupLayoutHelper();
		
		leftLayoutHelper.addColumn(
				lblEstadisticas,
				lblVendors,
				vendorList,
				GroupLayoutHelper.fluidGenerateGroupLayout(
						Arrays.asList(fromLabel, fromChooser),
						Arrays.asList(toLabel, toChooser)
						)
						.linkHorizontalSize(fromChooser, toChooser)
						.linkVerticalSize(fromChooser, toChooser)
						.setIsInnerPanel(true)
						.generatePanel()
				,
				viewButton
		);
		
		leftLayoutHelper.linkVerticalSize(viewButton, vendorList);
		leftLayoutHelper.linkHorizontalSize(vendorList);
		
		leftPanel.setLayout(leftLayoutHelper.generateLayout(leftPanel));
		
		generator = new FormGenerator();
		
		generator.addFields("Nombre", "Usuario", "Contraseña");
		generator.setTitle("Nuevo vendedor");
		generator.setIsInnerPanel(true);
		generator.setMinimumFieldWidth(200);
		
		createButton = new JButton("Crear");
		
		generator.addButton(createButton);
		
		JPanel rightPanel = generator.generateForm();
		NavigateButton vendorViewButton = new NavigateButton("Vista vendedor",VendorFirstView.class);
		
		mainLayout.addColumn(leftPanel, Box.createGlue());
		mainLayout.addColumn(Box.createHorizontalStrut(10), vendorViewButton);
		mainLayout.addColumn(rightPanel, Box.createGlue());
		
		mainLayout.setInnerMargins(10, 0, 10, 10);
		
		this.setLayout(mainLayout.generateLayout(this));
	}
	
	public void setModel(List<Vendor> vendors)
	{
		this.vendors = new DefaultComboBoxModel<Vendor>(vendors.toArray(new Vendor[vendors.size()]));
		vendorList.setModel(this.vendors);
	}

	public String getNewVendorName()
	{
		return generator.getValueFor("Nombre");
	}
	
	public String getNewVendorPass()
	{
		return generator.getValueFor("Contraseña");
	}
	
	public String getNewVendorUser()
	{
		return generator.getValueFor("Usuario");
	}
	
	public Vendor getSelectedVendor()
	{
		return (Vendor) vendors.getSelectedItem();
	}
	
	public Date getReportStartDate()
	{
		return fromChooser.getDate();
	}
	
	public Date getReportEndDate()
	{
		return toChooser.getDate();
	}
	
	@Override
	public <V extends View> void setController(Controller<V> c)
	{
		createButton.setActionCommand("CreateVendor");
		createButton.addActionListener(c);	
		
		viewButton.setActionCommand("ViewStats");
		viewButton.addActionListener(c);
	}
}
