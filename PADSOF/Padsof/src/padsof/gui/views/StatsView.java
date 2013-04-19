package padsof.gui.views;

import javax.swing.JLabel;

import padsof.gui.controllers.Controller;
import padsof.gui.models.StatsModel;
import padsof.gui.utils.GroupLayoutHelper;

public class StatsView extends View
{
	private JLabel benefits;
	private JLabel servicesBooked;
	private JLabel servicesCanceled;
	private JLabel servicesSold;
	private JLabel moneyIn;
	private JLabel moneyOut;
	private JLabel servicesTotal;
	
	public StatsView()
	{
		super("Estadísticas");
		
		GroupLayoutHelper layout = new GroupLayoutHelper();
		layout.addColumn(
				new JLabel("Beneficios"),
				new JLabel("Dinero entrante"),
				new JLabel("Dinero saliente"),
				new JLabel("Total de servicios gestionados"),
				new JLabel("Servicios reservados"),
				new JLabel("Servicios cancelados"),
				new JLabel("Servicios vendidos")
				);
		
		benefits = new JLabel();
		servicesBooked = new JLabel();
		servicesSold = new JLabel();
		servicesCanceled = new JLabel();
		servicesTotal = new JLabel();
		moneyIn = new JLabel();
		moneyOut = new JLabel();
		
		layout.addColumn(benefits, moneyIn, moneyOut, servicesTotal, servicesBooked, servicesCanceled, servicesSold);
		
		this.add(layout.generateCenteredPanel());
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6670637234723070211L;

	public void setModel(StatsModel stats)
	{
		benefits.setText(stats.benefits + "");
		servicesBooked.setText(stats.servicesBooked + "");
		servicesCanceled.setText(stats.servicesCanceled + "");
		servicesSold.setText(stats.servicesSold + "");
		moneyIn.setText(stats.moneyIn + "");
		moneyOut.setText(stats.moneyOut + "");
		servicesTotal.setText(stats.services + "");;
	}
	
	@Override
	public <V extends View> void setController(Controller<V> c)
	{
	}

}
