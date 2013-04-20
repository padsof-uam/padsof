package padsof.db;

import java.util.*;

public class Query<O extends DBObject>
{
	private List<Restriction<?>> restrictions = new ArrayList<Restriction<?>>();
	
	static class Restriction<T>
	{
		public T min;
		public T max;
		public boolean hasMin = false;
		public boolean hasMax = false;
		public String field;
		
		public void setMin(T value) 
		{
			min = value;
			hasMin = true;
		}
		
		public void setMax(T value) 
		{
			max = value;
			hasMax = true;
		}
	}
	
	public <T> void addRestriction(String field, T min, T max)
	{
		Restriction<T> r = new Restriction<T>();
		r.field = field;
		r.setMax(max);
		r.setMin(min);
		
		restrictions.add(r);
	}
	
	public <T> void addMin(String field, T min)
	{
		Restriction<T> r = new Restriction<T>();
		r.field = field;
		r.setMin(min);
		
		restrictions.add(r);
	}
	
	public <T> void addMax(String field, T max)
	{
		Restriction<T> r = new Restriction<T>();
		r.field = field;
		r.setMax(max);
		
		restrictions.add(r);
	}
	
	public List<Restriction<?>> getRestrictions()
	{
		return restrictions;
	}
}
