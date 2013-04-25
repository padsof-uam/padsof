package padsof.gui.controllers;

import java.awt.*;
import java.util.List;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.*;

import javax.swing.*;

import padsof.bookings.Booking;
import padsof.db.DBWrapper;
import padsof.db.feeder.DBFeeder;
import padsof.gui.Application;
import padsof.gui.controllers.utils.Listener;
import padsof.gui.views.*;
import padsof.system.*;

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
		refreshMargin();
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

	@Listener("Change margin")
	public void changeMargin()
	{
		double margin = -1;

		try
		{
			String marginStr = view.getMargin();
			margin = Double.valueOf(marginStr);
		}
		catch (NumberFormatException e)
		{
			showError("Introduzca un número válido.");
			return;
		}

		if (margin < 0)
		{
			int option = JOptionPane.showConfirmDialog(view,
					"¿Está seguro de querer introducir un margen negativo?");
			if (option != JOptionPane.YES_OPTION)
				return;
		}

		Margin.getMargin().setMarginPoints(margin);
		try
		{
			Margin.saveMargin();
		}
		catch (Exception e)
		{
			showError("No se han podido guardar los márgenes: "
					+ e.getMessage());
			return;
		}

		refreshMargin();
		showMessage("Margen cambiado al " + margin + "%");
	}

	private void refreshMargin()
	{
		view.setModel(Margin.getMargin().getMarginPoints());
	}

	@Listener("PassChange")
	public void changePass()
	{
		Vendor vendor = view.getVendorToManage();
		String newPass = JOptionPane.showInputDialog("Nueva contraseña para "
				+ vendor.getName());

		if (newPass == null)
			return;

		try
		{
			vendor.setPassword(newPass);
			DBWrapper.getInstance().save(vendor);
			refreshVendors();
		}
		catch (Exception e)
		{
			showError("No se ha podido cambiar la contraseña del usuario "
					+ vendor.getName());
			return;
		}

		showMessage("Contraseña cambiada para " + vendor.getName());
	}

	private List<Vendor> getAdmins() throws SQLException
	{
		List<Vendor> vendors = DBWrapper.getInstance().getAll(Vendor.class);
		List<Vendor> admins = new ArrayList<Vendor>();

		for (Vendor v : vendors)
			if (v.IsAdmin())
				admins.add(v);

		return admins;
	}

	@Listener("DeleteVendor")
	public void deleteVendor()
	{
		Vendor vendor = view.getVendorToManage();
		List<Vendor> admins;

		try
		{
			admins = getAdmins();
		}
		catch (SQLException e)
		{
			showError("No se ha podido recuperar la lista de usuarios.");
			return;
		}

		if (vendor.IsAdmin() && admins.size() <= 1)
		{
			showError("No se puede eliminar el único administrador del sistema.");
			return;
		}

		int option = JOptionPane
				.showConfirmDialog(
						view,
						"¿Está seguro de querer borrar el usuario "
								+ vendor.getName()
								+ "?\n"
								+ "Todos los paquetes pasarán a propiedad del administrador.");

		if (option != JOptionPane.YES_OPTION)
			return;

		Vendor admin = admins.get(0);

		try
		{
			for (Booking b : vendor.getBookings())
				b.setVendorUser(admin);
			DBWrapper.getInstance().delete(vendor);
		}
		catch (Exception e)
		{
			showError("No se ha podido borrar el usuario: " + e.getMessage());
			return;
		}

		refreshVendors();
		showMessage("Usuario borrado.");
	}

	@Listener("AdminChange")
	public void changeAdmin()
	{
		Vendor vendor = view.getVendorToManage();
		String message;
		List<Vendor> admins;

		try
		{
			admins = getAdmins();
		}
		catch (SQLException e)
		{
			showError("No se ha podido recuperar la lsita de administradores: "
					+ e.getMessage());
			return;
		}

		if (admins.size() <= 1 && vendor.IsAdmin())
		{
			showError("No se pueden quitar los permisos al único administrador del sistema.\n Dé permisos a otro usuario antes.");
			return;
		}

		if (vendor.IsAdmin())
			message = "quitar";
		else
			message = "dar";

		int option = JOptionPane.showConfirmDialog(
				view,
				"¿Está seguro de que quiere " + message
						+ " permisos de administrador al usuario "
						+ vendor.getName() + "?");

		if (option != JOptionPane.YES_OPTION)
			return;

		vendor.setAdmin(!vendor.IsAdmin());

		try
		{
			DBWrapper.getInstance().save(vendor);
			refreshVendors();
		}
		catch (Exception e)
		{
			showError("No se han podido cambiar los permisos.");
			return;
		}

		showMessage("Permisos cambiados.");
	}
}
