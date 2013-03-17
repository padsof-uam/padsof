package padsof.tests;

import static org.junit.Assert.*;
import static padsof.tests.Assert.*;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.*;
import padsof.db.DBManager;
import padsof.tests.dummies.SampleSimpleClass;
import padsof.tests.dummies.SimpleClass;

public class DBManagerTester
{
	private static DBManager db;
	
	@BeforeClass
	public static void init() throws SQLException
	{
		db = new DBManager("testdb");
	}
	
	@Test
	public void testSaveRetrieve() throws SQLException
	{
		SampleSimpleClass a = new SampleSimpleClass();
		
		db.save(a);
		
		List<SampleSimpleClass> entities = db.getAll(SampleSimpleClass.class);
		
		assertEquals(a, entities.get(0));
	}
	
	@Test
	public void testBunch() throws SQLException
	{
		ArrayList<SampleSimpleClass> l = new ArrayList<SampleSimpleClass>();
		Random rand = new Random();
		
		for(int i = 0; i < 10; i++)
		{
			SampleSimpleClass t = new SampleSimpleClass();
			t.publicField = "" + rand.nextInt(100000);
			l.add(t);
			db.save(t);
		}
		
		List<SampleSimpleClass> entities = db.getAll(SampleSimpleClass.class);
		
		assertIsSubset(l, entities);
	}
	
	@Test
	public void testDelete() throws SQLException
	{
		List<SampleSimpleClass> entities = db.getAll(SampleSimpleClass.class);
		SampleSimpleClass t = new SampleSimpleClass();
		t.publicField = "GHHghasgd661";
		
		assertListDoesntContain(entities, t);
		
		db.save(t);
		
		entities = db.getAll(SampleSimpleClass.class);
		
		assertListContains(entities, t);
		
		db.delete(t);
		
		entities = db.getAll(SampleSimpleClass.class);
		
		assertListDoesntContain(entities, t);		
	}
	
	@Test
	public void testClear() throws SQLException
	{
		ArrayList<SampleSimpleClass> l = new ArrayList<SampleSimpleClass>();
		Random rand = new Random();
		
		for(int i = 0; i < 10; i++)
		{
			SampleSimpleClass t = new SampleSimpleClass();
			t.publicField = "" + rand.nextInt(100000);
			l.add(t);
			db.save(t);
		}
		
		List<SampleSimpleClass> entities = db.getAll(SampleSimpleClass.class);
		
		assertTrue(entities.size() > 0);
		
		db.clear(SampleSimpleClass.class);
		
		assertEquals(db.getAll(SampleSimpleClass.class).size(), 0);
	}
	
	@Test 
	public void testMultipleClasses() throws SQLException, ClassNotFoundException
	{
		db.clear();
		
		ArrayList<SampleSimpleClass> l = new ArrayList<SampleSimpleClass>();
		ArrayList<SimpleClass> s = new ArrayList<SimpleClass>();
		Random rand = new Random();
		
		for(int i = 0; i < 10; i++)
		{
			SampleSimpleClass t = new SampleSimpleClass();
			t.publicField = "" + rand.nextInt(100000);
			l.add(t);
			db.save(t);
			
			SimpleClass q = new SimpleClass();
			q.field = "" + rand.nextInt(1000000);
			s.add(q);
			db.save(q);
		}
		
		List<SampleSimpleClass> lDB = db.getAll(SampleSimpleClass.class);
		List<SimpleClass> sDB = db.getAll(SimpleClass.class);
		
		assertEquals(l.size(), lDB.size());
		assertEquals(s.size(), sDB.size());
		
		assertAreSameCollection(l, lDB);
		assertAreSameCollection(s, sDB);
	}
	
	@Test
	public void testGet() throws SQLException
	{
		SampleSimpleClass a = new SampleSimpleClass();
		a.publicField = "thisIsATest";
		
		db.save(a);
		
		List<SampleSimpleClass> entities = db.get(SampleSimpleClass.class, "publicField", "thisIsATest");
		
		assertListContains(entities, a);
	}	
	
	@AfterClass
	public static void destroy() throws SQLException
	{
		if(db != null)
			db.close();
		
		File f = new File("testdb.sqlite");
		if(f.exists())
			f.delete();
	}
}