package padsof.tests;

import static org.junit.Assert.*;
import static padsof.tests.Assert.*;
import org.junit.*;
import padsof.db.feeder.CSVReader;

import java.util.*;
import java.io.*;

public class CSVReaderTester
{
	static String file = "testCSVReaderTemp";
	static ArrayList<ArrayList<String>> fields;
	static final int rows = 10;
	static final int columns = 5;

	@BeforeClass
	public static void createFile() throws IOException
	{
		Random rand = new Random();
		BufferedWriter writer = null;
		String field;
		boolean emptyFieldAdded = false;
		writer = new BufferedWriter(new FileWriter(file));
		fields = new ArrayList<ArrayList<String>>();
		
		for (int i = 0; i < rows; i++)
		{
			fields.add(new ArrayList<String>());
			for (int j = 0; j < columns; j++)
			{
				if (i == 1 && (rand.nextInt(3) == 1 || (!emptyFieldAdded && j == columns - 1)))
				{
					field = ""; // Some empty fields in line 2 to ensure the parser
								// behaves well.
					emptyFieldAdded = true;
				}
				else
					field = Integer.toHexString(rand.nextInt(1276531));

				fields.get(i).add(field);
				writer.write(field + ";");
			}
			writer.write("\n");
		}

		writer.close();
	}

	@Test
	public void testParseLine() throws Exception
	{
		CSVReader reader = new CSVReader(file);

		assertListEquals(reader.parseLine(), fields.get(0));
	
		reader.close();
	}
	
	@Test
	public void testParseLineEmptyField() throws Exception
	{
		CSVReader reader = new CSVReader(file);

		reader.parseLine(); // Discard line 1.
		assertListEquals(reader.parseLine(), fields.get(1));
	
		reader.close();
	}

	@Test
	public void testParseFile() throws Exception
	{
		CSVReader reader = new CSVReader(file);

		List<List<String>> fs = reader.parseAll();

		assertEquals(fields.size(), fs.size());
		for (int i = 0; i < fs.size(); i++)
		{
			assertListEquals(fields.get(i), fs.get(i));
		}
		reader.close();

	}
}
