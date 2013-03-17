/**
 * 
 */
package padsof.db.feeder;

import java.io.*;
import java.util.*;

/**
 * @author gjulianm
 *
 */
public class CSVReader implements Closeable
{
	private final String CSVDELIM = ";";
	private String file;
	private Scanner scanner = null;
	
	private Scanner getScanner() throws FileNotFoundException
	{
		if(scanner == null)
			scanner = new Scanner(new BufferedReader(new FileReader(file)));
		
		return scanner;
	}
	
	
	/**
	 * @return the file
	 */
	public String getFile()
	{
		return file;
	}

	public CSVReader(String file)
	{
		this.file = file;
	}
	
	public List<String> parseLine() throws Exception
	{
		ArrayList<String> fields = new ArrayList<String>();
		String line = getScanner().nextLine();
		
		for(String token : line.split(CSVDELIM))
			fields.add(token);
			
		return fields;
	}
	
	public List<List<String>> parseAll() throws Exception
	{
		ArrayList<List<String>> rows = new ArrayList<List<String>>();
		boolean fileFinished = false;
		
		getScanner().reset();
		
		while(!fileFinished)
		{
			try
			{
				rows.add(parseLine());
			}
			catch(NoSuchElementException e)
			{
				fileFinished = true;
			}
		}
		
		return rows;
	}
	
	public void close()
	{
		if(scanner != null)
			scanner.close();
	}
	
	public void reset() 
	{
		if(scanner != null)
			scanner.reset();
	}
}
