package padsof.tests.dummies;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class SampleSimpleClass
{
	@DatabaseField(generatedId = true)
	private long id;

	@DatabaseField
	public String publicField;

	@DatabaseField
	private String privateField;

	public void setPrivateField(String s)
	{
		privateField = s;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (obj.getClass() != getClass())
			return false;

		SampleSimpleClass rhs = (SampleSimpleClass) obj;

		return rhs.id == id;
	}
}