package padsof.gui.views;

import java.awt.Dimension;
import java.util.Arrays;

import javax.swing.*;

import padsof.gui.controllers.Controller;
import padsof.gui.utils.GroupLayoutHelper;

public class SearchClient extends View
{

	private JButton search;
	private JTextField jtfDni;

	
	@SuppressWarnings("unchecked")
	public SearchClient()
	{
		super("Buscar cliente");
		JLabel titulo = new JLabel("<html><big>Buscar cliente</html>");
		search = new JButton("Buscar");
		jtfDni = new JTextField("dni");
		JLabel lblDni = new JLabel("Introduzca el dni del cliente: ");

		GroupLayoutHelper mainLayout = new GroupLayoutHelper();

		mainLayout.addColumn(Box.createGlue(), Box.createGlue(),
				Box.createGlue());

		mainLayout.addColumn(
				titulo,
				GroupLayoutHelper
						.fluidGenerateGroupLayout(Arrays.asList(lblDni),
								Arrays.asList(jtfDni))
						.linkHorizontalSize(lblDni, jtfDni)
						.linkVerticalSize(lblDni, jtfDni).setIsInnerPanel(true)
						.generatePanel(), search);

		mainLayout.addColumn(Box.createGlue(), Box.createGlue(),
				Box.createGlue());

		JPanel mainpanel = new JPanel();
		mainpanel.setLayout(mainLayout.generateLayout(mainpanel));

		mainLayout.setInnerMargins(10, 10, 10, 10);

		this.setMinimumSize(new Dimension(500, 500));
		this.setLayout(mainLayout.generateLayout(this));

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6665518031422413678L;

	@Override
	public <V extends View> void setController(Controller<V> c)
	{
		search.setActionCommand("Search");
		search.addActionListener(c);
	}

	public String getDni()
	{
		return this.jtfDni.getText();
	}

}
