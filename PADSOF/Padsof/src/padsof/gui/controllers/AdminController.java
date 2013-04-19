package padsof.gui.controllers;

import java.sql.SQLException;
import java.util.*;

import javax.swing.JOptionPane;

import padsof.db.DBWrapper;
import padsof.gui.views.*;
import padsof.system.Vendor;


public class AdminController extends Controller<AdminView>
{
	public void refreshVendors()
	{
		List<Vendor> vendors = new ArrayList<Vendor>();
		
		
		try
		{
			vendors.add(new Vendor("Todos", "--all--", "--none--"));
			vendors.addAll(DBWrapper.getInstance().getAll(Vendor.class));
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(view, "No se pueden recuperar los vendedores.");
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
		vendor.setName(view.getNewVendorName());
		try
		{
			vendor.setPassword(view.getNewVendorPass());
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(view, "Error estableciendo contraseña.");
			e.printStackTrace();
			return;
		}
		vendor.setUser(view.getNewVendorUser());
		
		try
		{
			if(!DBWrapper.getInstance().get(Vendor.class, "user", vendor.getUser()).isEmpty())
			{
				JOptionPane.showMessageDialog(view, "Ese usuario ya existe");
				return;
			}
		}
		catch (SQLException e)
		{
			JOptionPane.showMessageDialog(view, "No se han podido buscar usuarios en la base de datos.");
			return;
		}
		
		try
		{
			DBWrapper.getInstance().save(vendor);
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(view, "No se ha podido guardar el usuario");
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
}
