package padsof.gui.controllers;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.*;

import javax.swing.*;

import padsof.db.DBWrapper;
import padsof.db.feeder.DBFeeder;
import padsof.gui.Application;
import padsof.gui.controllers.utils.Listener;
import padsof.gui.views.*;
import padsof.system.Vendor;

public class AdminController extends Controller<AdminView>
{
	public void refreshVendors()
	{
		java.util.List<Vendor> vendors = new ArrayList<Vendor>();

		try
		{
			vendors.add(new Vendor("Todos", "--all--", "--none--"));
			vendors.addAll(DBWrapper.getInstance().getAll(Vendor.class));
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(view,
					"No se pueden recuperar los vendedores.");
			return;
		}

		view.setModel(vendors);
	}

	@Override
	public void setView(AdminView view)
	{
		super.setView(view);
		refreshVendors();
	}

	@Listener("CreateVendor")
	public void createVendor()
	{
		Vendor vendor = new Vendor();
		if (view.getNewVendorName().isEmpty()
				|| view.getNewVendorUser().isEmpty()
				|| view.getNewVendorPass().isEmpty())
		{
			JOptionPane
					.showMessageDialog(view,
							"Por favor, rellene todos los campos para crear un nuevo vendedor");
			return;
		}
		vendor.setName(view.getNewVendorName());
		try
		{
			vendor.setPassword(view.getNewVendorPass());
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(view,
					"Error estableciendo contraseña.");
			e.printStackTrace();
			return;
		}
		vendor.setUser(view.getNewVendorUser());

		try
		{
			if (!DBWrapper.getInstance()
					.get(Vendor.class, "user", vendor.getUser()).isEmpty())
			{
				JOptionPane.showMessageDialog(view, "Ese usuario ya existe");
				return;
			}
		}
		catch (SQLException e)
		{
			JOptionPane.showMessageDialog(view,
					"No se han podido buscar usuarios en la base de datos.");
			return;
		}

		try
		{
			DBWrapper.getInstance().save(vendor);
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(view,
					"No se ha podido guardar el usuario");
			return;
		}

		JOptionPane.showMessageDialog(view, "Usuario creado.");
		refreshVendors();
	}

	@Listener("ViewStats")
	public void viewStats()
	{
		StatsController.maxDate = view.getReportEndDate();
		StatsController.minDate = view.getReportStartDate();
		StatsController.vendor = view.getSelectedVendor();

		navigator.navigate(StatsView.class);
	}

	@Listener("Feed")
	public void feed()
	{
		final JProgressBar pb = new JProgressBar(0, 100);
		pb.setPreferredSize(new Dimension(175, 20));
		pb.setValue(0);
		JLabel label = new JLabel("Progress: ");

		final JDialog dialog = new JDialog(
				Application.getInstance().getFrame(), "Trabajando...");

		final JButton button = new JButton("Finalizado");
		button.setEnabled(false);
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				dialog.dispose();
			}
		});

		dialog.getContentPane().setLayout(new FlowLayout());
		dialog.getContentPane().add(label);
		dialog.getContentPane().add(pb);
		dialog.getContentPane().add(button);
		dialog.pack();
		dialog.setVisible(true);

		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				DBFeeder feeder = new DBFeeder();

				try
				{
					feeder.saveHotelData("utils/Hoteles.csv");
					pb.setValue(33);
					pb.repaint();

					feeder.saveTravelData("utils/ViajesOrganizados.csv");
					pb.setValue(66);
					pb.repaint();

					feeder.saveImsersoTravelData("utils/ViajesIMSERSO.csv");
					pb.setValue(100);
					pb.repaint();
				}
				catch (Exception e)
				{
					showError("Error cargando datos.");
				}

				button.setEnabled(true);
			}
		});
	}

}
