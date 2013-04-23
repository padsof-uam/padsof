package padsof.gui.controllers;

import java.sql.SQLException;
import java.util.*;

import padsof.db.DBWrapper;
import padsof.gui.controllers.utils.Listener;
import padsof.gui.views.FindTravelView;
import padsof.services.Travel;

public class FindTravelController extends Controller<FindTravelView>
{
	@Listener("Search")
	public void search()
	{
		List<Travel> travels;
		try
		{
			travels = DBWrapper.getInstance().getAll(Travel.class);
		}
		catch (SQLException e)
		{
			showError("Error recuperando viajes");
			return;
		}
		
		List<Travel> filtered = new ArrayList<Travel>();
		
		double maxPrice = -1;
		
		try
		{
			String price = view.getMaxPrice();
			
			if(!price.isEmpty())
				maxPrice = Double.valueOf(price);
		}
		catch(NumberFormatException e)
		{
			showError("Formato de precio inválido.");
			return;
		}
		
		if(maxPrice != -1)
			for(Travel t: travels)
				if(t.getPrice() > maxPrice)
					filtered.add(t);
		
		for(Travel t: filtered)
			travels.remove(t);
		
		view.setResults(travels);
	}
}
