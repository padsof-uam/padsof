package padsof.tests;

import java.lang.reflect.Field;
import java.util.*;

import padsof.utils.Reflection;
import static org.junit.Assert.*;

public class Assert
{
	public static <T extends Comparable<T>> void assertListEquals(List<T> expected, List<T> actual)
	{
		assertEquals("Wrong size", expected.size(), actual.size());
		
		for(int i = 0; i < expected.size(); i++)
		{
			T e = expected.get(i);
			T a = actual.get(i);
			
			if(e.compareTo(a) != 0)
				fail("Position " + i + " expected " + e + ", got " + "a");
		}
	}
	
	public static <T> void assertListContains(Iterable<T> list, T object)
	{
		for(T item : list)
			if(item.equals(object))
				return;
		
		fail();
	}
	
	public static <T> void assertListDoesntContain(Iterable<T> list, T object)
	{
		for(T item : list)
			if(item.equals(object))
				fail();
	}
	
	public static <T> void assertIsSubset(Iterable<T> subset, Iterable<T> superset)
	{
		for(T item : subset)
			assertListContains(superset, item);
	}
	
	public static <T> void assertAreSameCollection(Iterable<T> expected, Iterable<T> actual)
	{
		assertIsSubset(expected, actual);
		assertIsSubset(actual, expected);
	}
	
	
	
	public static <T> void assertFieldWiseEquals(T expected, T actual, String...excludedFields) throws IllegalArgumentException, IllegalAccessException
	{
		assertFieldWiseEquals("", expected, actual, excludedFields);
	}
	
	public static <T> void assertFieldWiseEquals(String message, T expected, T actual, String... excludedFields) throws IllegalArgumentException, IllegalAccessException
	{
		List<String> excluded = new ArrayList<String>();
		for(String s : excludedFields)
			excluded.add(s);
		
		for(Field field : Reflection.getAllFieldsFrom(expected.getClass()))
		{
			field.setAccessible(true);
			if(!excluded.contains(field.getName()))
				assertEquals(message + " - failed field " + field.getName(), field.get(expected), field.get(actual));
		}
	}
}
