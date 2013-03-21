package padsof.db;

import com.j256.ormlite.field.DatabaseField;

/**
 * Base object to implement DB behaviour to objects: including an ID field and
 * an equals method which compares the ID of two DBObject. Also includes a
 * toString method for better debugging.
 * 
 * @author Víctor de Juan Sanz - Guillermo Julián Moreno
 */
public abstract class DBObject
{
	@DatabaseField(generatedId = true)
	protected long id;

	@Override
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

	@Override
	public String toString()
	{
		return getClass().getName() + ":id-" + id;
	}
}
