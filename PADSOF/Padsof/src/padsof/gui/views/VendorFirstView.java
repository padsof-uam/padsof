package padsof.gui.views;

import java.awt.Dimension;
import java.rmi.NoSuchObjectException;

import javax.swing.*;

import padsof.gui.NavigateButton;
import padsof.gui.controllers.Controller;
import padsof.gui.utils.GroupLayoutHelper;

public class VendorFirstView extends View
{
	private NavigateButton newClient;
	private NavigateButton existentClient;

	public VendorFirstView() throws NoSuchObjectException
	{
		super("Vendedor");

		JLabel titulo = new JLabel ("<html><p align=" + "justify"
				+ "><b><u>¿Con qué cliente </u></b><br><p align=" + "justify"
				+ "><b><u>desea trabajar?</html>");
		newClient = new NavigateButton("<html><p align=" + "justify"
				+ "><b>Nuevo</b></html>", RegisterClientView.class);
		existentClient = new NavigateButton("<html><p align=" + "justify"
				+ "><b>Existente</b><br></html>",
				SearchClient.class);

		GroupLayoutHelper mainLayout = new GroupLayoutHelper();
		JPanel mainpanel = new JPanel();

		mainLayout.addColumn(Box.createGlue(), Box.createGlue(),
				Box.createGlue());
		mainLayout.addColumn(titulo,newClient, existentClient);
		mainLayout.addColumn(Box.createGlue(), Box.createGlue(),
				Box.createGlue());

		mainLayout.linkVerticalSize(newClient, existentClient );
		mainLayout.linkHorizontalSize(newClient, existentClient );

		
		
		mainpanel.setLayout(mainLayout.generateLayout(mainpanel));

		mainLayout.setInnerMargins(10, 10, 10, 10);

		this.setMinimumSize(new Dimension(500, 500));
		this.setLayout(mainLayout.generateLayout(this));
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1993546369866479786L;

	@Override
	public <V extends View> void setController(Controller<V> c)
	{
		
	}

}
