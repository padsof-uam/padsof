package padsof.tests;

import static org.junit.Assert.assertEquals;
import static padsof.tests.Assert.assertListEquals;

import java.io.*;
import java.util.*;

import org.junit.*;

import padsof.csv.CSVReader;

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
		writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(file), "ISO-8859-1"));
		fields = new ArrayList<ArrayList<String>>();

		for (int i = 0; i < rows; i++)
		{
			fields.add(new ArrayList<String>());
			for (int j = 0; j < columns; j++)
			{
				if (i == 1 && (j % 2 == 0 || j == columns - 1))
					field = ""; // Some empty fields at start and end in line 2
								// to ensure the parser
								// behaves well.
				else
					field = Integer.toHexString(rand.nextInt(1276531));

				fields.get(i).add(field);

				if (j < columns - 1)
					field += ";";

				writer.write(field);
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
		assertListEquals(fields.get(1), reader.parseLine());

		reader.close();
	}

	@Test
	public void testParseFile() throws Exception
	{
		CSVReader reader = new CSVReader(file);

		List<List<String>> fs = reader.parseAll();

		assertEquals(fields.size(), fs.size());
		for (int i = 0; i < fs.size(); i++)
			assertListEquals(fields.get(i), fs.get(i));
		reader.close();

	}

	@AfterClass
	public static void shutdown()
	{
		File f = new File(file);
		if (f.exists())
			f.delete();
	}
}
