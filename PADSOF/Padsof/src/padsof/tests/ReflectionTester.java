package padsof.tests;

import java.lang.reflect.Field;
import java.util.*;

import org.junit.*;

import padsof.utils.Reflection;

public class ReflectionTester
{
	public class Dummy
	{
		public String dummy;
	}
	
	public class Dummy2 extends Dummy
	{
		public String dummy2;
		public String x;
		@SuppressWarnings("unused")
		private String y;
		protected String z;
		String w;
	}

	@Test
	public void testGetAllFields()
	{
		ArrayList<String> fields = new ArrayList<String>(Arrays.asList("dummy", "dummy2", "x", "y", "z", "w" ));
		ArrayList<String> gotFields = new ArrayList<String>();
		
		for(Field f : Reflection.getAllFieldsFrom(Dummy2.class))
			gotFields.add(f.getName());
	
		Assert.assertAreSameCollection(fields, gotFields);
	}

}
