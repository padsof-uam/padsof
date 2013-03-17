package padsof.tests;

import java.util.*;
import static org.junit.Assert.*;

public class Assert
{
	public static <T extends Comparable<T>> void assertListEquals(List<T> expected, List<T> actual)
	{
		assertEquals(expected.size(), actual.size());
		
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
}
