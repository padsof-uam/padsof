package padsof.system;

import java.sql.SQLException;
import java.util.*;

import padsof.db.*;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Margin extends DBObject
{
	private static Margin instance;

	public Margin(double margin)
	{
		this.margin = margin;
	}

	public Margin()
	{
		this.margin = 0;
	}
	
	public static Margin getMargin()
	{
		if (instance == null)
		{
			List<Margin> margins;
			try
			{
				margins = DBWrapper.getInstance().getAll(Margin.class);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				margins = new ArrayList<Margin>();
			}
			
			if (margins.isEmpty())
				instance = new Margin(0);
			else
				instance = margins.get(0);
		}

		return instance;
	}

	public static void saveMargin() throws IllegalArgumentException, IllegalAccessException, SQLException
	{
		if (instance != null)
			DBWrapper.getInstance().save(instance);
	}

	@DatabaseField
	private double margin;

	public double getMarginPoints()
	{
		return margin;
	}
	
	public void setMarginPoints(double margin)
	{
		this.margin = margin;
	}
}
