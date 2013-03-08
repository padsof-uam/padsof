/**
 * 
 */
package padsof.system;

import java.util.Date;

/**
 * @author gjulianm
 *
 */
public class ImsersoClient extends Client
{
	private long ssNumber;
	private Date birth; // TODO: ¿Fecha de qué?
	
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
