/**
 * 
 */
package padsof.csv;

/**
 * @author gjulianm
 */
public class InvalidFormatException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8431291711474774971L;
	private String file;
	private int line;

	/**
	 * @return the file
	 */
	public String getFile()
	{
		return file;
	}

	/**
	 * @return the line
	 */
	public int getLine()
	{
		return line;
	}

	public InvalidFormatException(String message, String file, int line)
	{
		super(message + " in " + file + ":" + line);
		this.file = file;
		this.line = line;
	}

}
