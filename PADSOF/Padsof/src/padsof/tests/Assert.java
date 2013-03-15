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
}
