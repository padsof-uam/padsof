package padsof.system;

import padsof.db.DBObject;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author Víctor de Juan Sanz - Guillermo Julián Moreno
 */
@DatabaseTable
public class Client extends DBObject
{
	@DatabaseField
	private String name;

	@DatabaseField
	private String surname;

	@DatabaseField
	private String DNI;

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the surname
	 */
	public String getSurname()
	{
		return surname;
	}

	/**
	 * @param surname
	 *            the surname to set
	 */
	public void setSurname(String surname)
	{
		this.surname = surname;
	}

	/**
	 * @return the dNI
	 */
	public String getDNI()
	{
		return DNI;
	}

	/**
	 * @param dNI
	 *            the dNI to set
	 */
	public void setDNI(String dNI)
	{
		DNI = dNI;
	}

	@Override
	public String toString()
	{
		return name + " " + surname + ", DNI " + DNI;
	}
	
	
}
