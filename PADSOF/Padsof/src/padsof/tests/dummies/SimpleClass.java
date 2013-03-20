package padsof.tests.dummies;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class SimpleClass
{
	@DatabaseField(generatedId = true)
	private long id;

	@DatabaseField
	public String field;

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (obj.getClass() != getClass())
			return false;

		SimpleClass rhs = (SimpleClass) obj;

		return rhs.id == id;
	}
}
