package padsof.gui.controllers;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JOptionPane;

import padsof.db.DBWrapper;
import padsof.gui.Application;
import padsof.gui.views.*;
import padsof.system.Vendor;

public class LoginController extends Controller<LoginView>
{
	@Listener("login")
	public void login(ActionEvent e) throws HeadlessException, Exception
	{
		String user = view.getUser();
		String pass = view.getPassword();
		
		List<Vendor> vendors = DBWrapper.getInstance().get(Vendor.class, "user", user);
		
		if(vendors.isEmpty())
		{
			JOptionPane.showMessageDialog(view, "Ese usuario no existe", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		Vendor vendor = vendors.get(0);
		if(!vendor.checkPassword(pass))
		{
			JOptionPane.showMessageDialog(view, "Contraseña incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		Application.getInstance().setVendor(vendor);	
		
		if(vendor.IsAdmin())
			navigator.navigate(AdminView.class);
		else
			navigator.navigate(VendorFirstView.class);
	}
	
	@Listener("forgot")
	public void forgot(ActionEvent e)
	{
		JOptionPane.showMessageDialog(view, "Avise al administrador para que resetee su contraseña.");
	}
}
