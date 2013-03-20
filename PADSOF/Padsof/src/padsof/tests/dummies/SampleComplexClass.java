package padsof.tests.dummies;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class SampleComplexClass
{
	@DatabaseField(generatedId = true)
	public long id;

	@DatabaseField(foreign = true, foreignAutoCreate = true,
			foreignAutoRefresh = true)
	public SampleSimpleClass child;

	@DatabaseField
	public double d;
	@DatabaseField
	public boolean b;
	@DatabaseField
	public int i;

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (obj.getClass() != getClass())
			return false;

		SampleComplexClass rhs = (SampleComplexClass) obj;

		return rhs.child.equals(child) && d == rhs.d && b == rhs.b
				&& i == rhs.i;

	}
}
