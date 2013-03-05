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
	private Date date; // TODO: ¿Fecha de qué?
	
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
	 * @return the date
	 */
	public Date getDate()
	{
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date)
	{
		this.date = date;
	}
}
