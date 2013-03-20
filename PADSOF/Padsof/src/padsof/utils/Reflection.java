package padsof.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Reflection
{
	public static List<Field> getAllFieldsFrom(Class<?> cls)
	{
		ArrayList<Field> fields = new ArrayList<Field>();
		
		for(Field f : cls.getDeclaredFields())
			fields.add(f);
		
		Class<?> sup = cls.getSuperclass();
		if(sup != null)
			fields.addAll(getAllFieldsFrom(sup));
		
		return fields;
	}
}
