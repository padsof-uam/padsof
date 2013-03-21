/**
 * 
 */
package padsof.csv;

/**
 * Invalid format exception.
 * @author Víctor de Juan Sanz - Guillermo Julián Moreno
 *
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
	 * @return the line where the error occurred.
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
