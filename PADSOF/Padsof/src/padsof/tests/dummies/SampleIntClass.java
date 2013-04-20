package padsof.tests.dummies;

import padsof.db.DBObject;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class SampleIntClass extends DBObject
{
	@DatabaseField
	public int i;
}
