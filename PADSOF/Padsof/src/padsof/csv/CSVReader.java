/**
 * 
 */
package padsof.csv;

import java.io.*;
import java.util.*;

/**
 * Class for reading CSV files.
 * 
 * @author Víctor de Juan Sanz - Guillermo Julián Moreno
 */
public class CSVReader implements Closeable
{
	private final String CSVDELIM = ";";
	private String file;
	private Scanner scanner = null;

	/**
	 * Gets the current scanner and creates it if it's not null.
	 * 
	 * @return Scanner.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private Scanner getScanner() throws FileNotFoundException, IOException
	{
		if (scanner == null)
			scanner = new Scanner(new BufferedReader(new InputStreamReader(
					new FileInputStream(file), "ISO-8859-1")));

		return scanner;
	}

	/**
	 * @return the file
	 */
	public String getFile()
	{
		return file;
	}

	/**
	 * Create CSV Reader
	 * 
	 * @param file
	 *            File to read.
	 * @throws FileNotFoundException
	 */
	public CSVReader(String file) throws FileNotFoundException
	{
		File f = new File(file);
		if (!f.exists())
			throw new FileNotFoundException(file);

		this.file = file;
	}

	/**
	 * Parse the next line in the file.
	 * 
	 * @return List of fields.
	 * @throws NoSuchElementException
	 *             There aren't any more lines in the file.
	 * @throws IOException
	 *             IO error.
	 */
	public List<String> parseLine() throws NoSuchElementException, IOException
	{
		ArrayList<String> fields = new ArrayList<String>();
		String line = getScanner().nextLine();

		for (String token : line.split(CSVDELIM, -1))
			fields.add(token);

		return fields;
	}

	/**
	 * Parses all lines in the file, starting from the beginning.
	 * 
	 * @return List of rows.
	 * @throws IOException
	 *             Error reading file.
	 */
	public List<List<String>> parseAll() throws IOException
	{
		ArrayList<List<String>> rows = new ArrayList<List<String>>();
		boolean fileFinished = false;

		getScanner().reset();

		while (!fileFinished)
			try
			{
				rows.add(parseLine());
			}
			catch (NoSuchElementException e)
			{
				fileFinished = true;
			}

		return rows;
	}

	/**
	 * Parses all lines to a list of objects.
	 * 
	 * @param creator
	 *            Class to create an object from a list of fields.
	 * @return List of objects.
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public <T> List<T> parseAll(CSVCreator<T> creator)
			throws InvalidFormatException, IOException
	{
		ArrayList<T> items = new ArrayList<T>();
		List<List<String>> lines = parseAll();
		int lineNumber = 1;
		lines.remove(0); // Header

		try
		{
			for (List<String> line : lines)
			{
				items.add(creator.parseLine(line));
				lineNumber++;
			}
		}
		catch (IndexOutOfBoundsException e)
		{
			throw new InvalidFormatException("Bad format", file, lineNumber);
		}

		return items;
	}

	@Override
	public void close()
	{
		if (scanner != null)
			scanner.close();
	}

	/**
	 * Set the internal scanner to the start of the file.
	 */
	public void reset()
	{
		if (scanner != null)
			scanner.reset();
	}
}
