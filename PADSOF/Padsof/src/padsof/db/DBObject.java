package padsof.db;

import com.j256.ormlite.field.DatabaseField;

public abstract class DBObject
{
	@DatabaseField(generatedId = true)
	protected long id;
	
	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (obj.getClass() != getClass())
			return false;

		DBObject rhs = (DBObject) obj;

		return rhs.id == id;
	}
}
