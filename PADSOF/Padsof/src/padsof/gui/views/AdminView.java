package padsof.gui.views;

import java.rmi.NoSuchObjectException;
import java.util.Arrays;

import javax.swing.*;

import padsof.gui.NavigateButton;
import padsof.gui.controllers.Controller;
import padsof.gui.utils.*;

import com.toedter.calendar.JDateChooser;

public class AdminView extends View
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3957591110532891073L;

	JButton createButton;
	
	@SuppressWarnings("unchecked")
	public AdminView() throws NoSuchObjectException
	{
		super("Admin");

		GroupLayoutHelper mainLayout = new GroupLayoutHelper();
		
		JLabel lblEstadisticas = new JLabel("Estadísticas");
		lblEstadisticas.setFont(lblEstadisticas.getFont().deriveFont((float) 20.0));
		
		JLabel lblVendors = new JLabel("Vendedores: ");
		
		String[] data = { "test", "it", "darling" };
		JComboBox<String> vendors = new JComboBox<String>(data);
		
		JDateChooser fromChooser = new JDateChooser();
		JDateChooser toChooser = new JDateChooser();
		
		JLabel fromLabel = new JLabel("Desde: ");
		JLabel toLabel = new JLabel("Hasta: ");
		
		fromLabel.setLabelFor(fromChooser);
		toLabel.setLabelFor(toChooser);
		
		JButton viewButton = new JButton("Ver");
		
		JPanel leftPanel = new JPanel();
		
		GroupLayoutHelper leftLayoutHelper = new GroupLayoutHelper();
		
		leftLayoutHelper.addColumn(
				lblEstadisticas,
				lblVendors,
				vendors,
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
		
		leftLayoutHelper.linkVerticalSize(viewButton, vendors);
		leftLayoutHelper.linkHorizontalSize(vendors);
		
		leftPanel.setLayout(leftLayoutHelper.generateLayout(leftPanel));
		
		FormGenerator generator = new FormGenerator();
		
		generator.addFields("Nombre", "Usuario", "Contraseña");
		generator.setTitle("Nuevo vendedor");
		generator.setIsInnerPanel(true);
		generator.setMinimumFieldWidth(200);
		
		createButton = new JButton("Crear");
		
		generator.addButton(createButton);
		
		JPanel rightPanel = generator.generateForm();
		NavigateButton vendorViewButton = new NavigateButton("Vista de vendedor",VendorFirstView.class);
		
		mainLayout.addColumn(leftPanel, Box.createGlue());
		mainLayout.addColumn(Box.createHorizontalStrut(10), vendorViewButton);
		mainLayout.addColumn(rightPanel, Box.createGlue());
		
		mainLayout.setInnerMargins(10, 0, 10, 10);
		
		this.setLayout(mainLayout.generateLayout(this));
	}

	@Override
	public <V extends View> void setController(Controller<V> c)
	{
		// TODO Auto-generated method stub
		
	}
}
