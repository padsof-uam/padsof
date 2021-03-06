package padsof.gui.views;

import java.rmi.NoSuchObjectException;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;

import padsof.gui.*;
import padsof.gui.controllers.Controller;
import padsof.gui.utils.*;
import padsof.system.Client;

public class SelectClientView extends View
{
	private NavigateButton newClient;
	private JTextField dniField;
	private JList<Client> clients;
	private DefaultListModel<Client> listModel;
	private JButton selectClient;
	private JButton deleteClient;

	@SuppressWarnings("unchecked")
	public SelectClientView() throws NoSuchObjectException
	{
		super("Vendedor");

		JLabel titleLabel = new JLabel("Selección de cliente");
		GuiUtils.applyTitleStyle(titleLabel);
		
		clients = new JList<Client>();
		dniField = new JTextField();
		selectClient = new JButton("Seleccionar");
		newClient = new NavigateButton("Crear nuevo",
				RegisterClientView.class);
		deleteClient = new JButton("Eliminar cliente");
		
		deleteClient.setEnabled(false);
		selectClient.setEnabled(false);
		
		clients.addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				selectClient.setEnabled(clients.getSelectedValue() != null);
				deleteClient.setEnabled(Application.getInstance().getVendor().IsAdmin() && clients.getSelectedValue() != null);
				if(clients.getSelectedValue() != null)
					Application.getInstance().setDefaultButton(selectClient);
			}
		});
		
		GroupLayoutHelper layout = new GroupLayoutHelper();
		layout.addColumn(
				titleLabel,
				GroupLayoutHelper.fluidGenerateGroupLayout(
						Arrays.asList(new JLabel("Filtrar por DNI: ")),
						Arrays.asList(dniField)
						)
						.setIsInnerPanel(true)
						.linkVerticalSize(dniField)
						.generatePanel(),
				new JScrollPane(clients),
				GuiUtils.generateLinePanel(newClient, selectClient, deleteClient)
				);

		layout.setInnerMargins(10, 10, 10, 10);
		
		layout.setAsLayoutOf(this);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1993546369866479786L;
	
	public void setModel(List<Client> clients)
	{
		if(listModel == null)
		{
			listModel = new DefaultListModel<Client>();
			this.clients.setModel(listModel);
		}
		
		List<Client> inList = new ArrayList<Client>();
		
		for(int i = 0; i < listModel.size(); i++)
			inList.add(listModel.elementAt(i)); // Don't ask me why don't they implement Iterable or listModel.toList().
		
		for(Client c: inList)
			if(!clients.contains(c))
				listModel.removeElement(c);
		
		for(Client c: clients)
			if(!listModel.contains(c))
				listModel.addElement(c);
	}
	
	@Override
	public <V extends View> void setController(Controller<V> c)
	{
		dniField.getDocument().addDocumentListener(c);
		selectClient.setActionCommand("Select");
		selectClient.addActionListener(c);
		
		deleteClient.setActionCommand("Delete");
		deleteClient.addActionListener(c);
	}

	public String getDNI()
	{
		return dniField.getText();
	}

	public Client getSelectedClient()
	{
		return clients.getSelectedValue();
	}

}
