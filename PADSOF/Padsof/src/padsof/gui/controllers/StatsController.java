package padsof.gui.controllers;

import java.sql.SQLException;
import java.util.Date;

import javax.swing.JOptionPane;

import padsof.gui.models.StatsModel;
import padsof.gui.views.StatsView;
import padsof.system.*;

public class StatsController extends Controller<StatsView>
{
	public static Vendor vendor;
	public static Date minDate;
	public static Date maxDate;

	@Override
	public void setView(StatsView view)
	{
		super.setView(view);
		StatsModel model = new StatsModel();
		StatsReporter reporter = new StatsReporter();
		reporter.setMaxDate(maxDate);
		reporter.setMinDate(minDate);
		
		try
		{
			if (vendor.getUser().equals("--all--"))
				getGlobalStats(model, reporter);
			else
				getVendorStats(model, reporter);
		}
		catch (SQLException e)
		{
			JOptionPane.showMessageDialog(view, "No se han podido cargar las estadísticas.");
			return;
		}
		
		view.setModel(model);
		
	}

	private void getVendorStats(StatsModel model, StatsReporter reporter)
			throws SQLException
	{
		model.benefits = reporter.getBenefitsOf(vendor);
		model.moneyIn = reporter.getTotalMoneyInOf(vendor);
		model.moneyOut = reporter.getTotalMoneyInOf(vendor);
		model.services = reporter.getTotalServicesOf(vendor);
		model.servicesBooked = reporter.getServicesBookedOf(vendor);
		model.servicesCanceled = reporter.getServicesCanceledOf(vendor);
		model.servicesSold = reporter.getServicesSoldOf(vendor);
	}

	private void getGlobalStats(StatsModel model, StatsReporter reporter)
			throws SQLException
	{
		model.benefits = reporter.getBenefits();
		model.moneyIn = reporter.getTotalMoneyIn();
		model.moneyOut = reporter.getTotalMoneyOut();
		model.services = reporter.getTotalServices();
		model.servicesBooked = reporter.getServicesBooked();
		model.servicesCanceled = reporter.getServicesCanceled();
		model.servicesSold = reporter.getServicesSold();
	}
}
