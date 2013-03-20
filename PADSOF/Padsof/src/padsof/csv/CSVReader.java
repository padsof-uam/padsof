/**
 * 
 */
package padsof.csv;

import java.io.*;
import java.util.*;

/**
 * @author gjulianm
 */
public class CSVReader implements Closeable
{
	private final String CSVDELIM = ";";
	private String file;
	private Scanner scanner = null;

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

	public CSVReader(String file) throws FileNotFoundException
	{
		File f = new File(file);
		if (!f.exists())
			throw new FileNotFoundException(file);

		this.file = file;
	}

	public List<String> parseLine() throws NoSuchElementException, IOException
	{
		ArrayList<String> fields = new ArrayList<String>();
		String line = getScanner().nextLine();

		for (String token : line.split(CSVDELIM, -1))
			fields.add(token);

		return fields;
	}

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

	public void reset()
	{
		if (scanner != null)
			scanner.reset();
	}
}
