/**
 * 
 */
package padsof.system;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author gjulianm
 *
 */

@DatabaseTable
public class ImsersoClient extends Client
{
	@DatabaseField
	private long ssNumber;
	
	@DatabaseField
	private Date birth;
	
	/**
	 * @return the ssNumber
	 */
	public long getSsNumber()
	{
		return ssNumber;
	}
	/**
	 * @param ssNumber the ssNumber to set
	 */
	public void setSsNumber(long ssNumber)
	{
		this.ssNumber = ssNumber;
	}
	/**
	 * @return the birth date
	 */
	public Date getBirth()
	{
		return birth;
	}
	/**
	 * @param date the birth to set
	 */
	public void setBirth(Date date)
	{
		this.birth = date;
	}
}
