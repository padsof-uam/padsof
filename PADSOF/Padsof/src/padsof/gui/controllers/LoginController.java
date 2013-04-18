package padsof.gui.controllers;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import padsof.db.DBWrapper;
import padsof.gui.Application;
import padsof.gui.views.*;
import padsof.system.Vendor;

public class LoginController extends Controller<LoginView>
{
	@Listener
	public void event(ActionEvent e) throws HeadlessException, Exception
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
		navigator.navigate(AdminView.class);
	}
}
