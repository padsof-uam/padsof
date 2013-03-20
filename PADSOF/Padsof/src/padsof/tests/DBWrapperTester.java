package padsof.tests;

import static org.junit.Assert.*;
import static padsof.tests.Assert.*;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.*;
import padsof.db.DBWrapper;
import padsof.tests.dummies.SampleComplexClass;
import padsof.tests.dummies.SampleSimpleClass;
import padsof.tests.dummies.SimpleClass;

public class DBWrapperTester
{
	private static DBWrapper db;
	
	@BeforeClass
	public static void init() throws SQLException
	{
		db = new DBWrapper("testdb");
	}
	
	@Test
	public void testSaveRetrieve() throws SQLException, IllegalArgumentException, IllegalAccessException
	{
		SampleSimpleClass a = new SampleSimpleClass();
		
		db.save(a);
		
		List<SampleSimpleClass> entities = db.getAll(SampleSimpleClass.class);
		
		assertEquals(a, entities.get(0));
	}
	
	@Test
	public void testComplexClass() throws SQLException, IllegalArgumentException, IllegalAccessException
	{
		SampleComplexClass a = new SampleComplexClass();
		a.child = new SampleSimpleClass();
		a.child.publicField = "Test";
		a.b = false;
		a.d = 3.6;
		a.i = 5;
		
		db.save(a);
		
		List<SampleComplexClass> entities = db.getAll(SampleComplexClass.class);
		
		assertEquals(a, entities.get(0));
	}
	
	@Test
	public void testBunch() throws SQLException, IllegalArgumentException, IllegalAccessException
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
	public void testDelete() throws SQLException, IllegalArgumentException, IllegalAccessException
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
	public void testClear() throws SQLException, IllegalArgumentException, IllegalAccessException
	{
		ArrayList<SampleSimpleClass> l = new ArrayList<SampleSimpleClass>();
		Random rand = new Random();
		
		for(int i = 0; i < 10; i++)
		{
			SampleSimpleClass t = new SampleSimpleClass();
			t.publicField = "" + rand.nextInt(100000);
			l.add(t);
			//db.save(t);
		}
		
		List<SampleSimpleClass> entities = db.getAll(SampleSimpleClass.class);
		
		assertTrue(entities.size() > 0);
		
		db.clear(SampleSimpleClass.class);
		
		assertEquals(db.getAll(SampleSimpleClass.class).size(), 0);
	}
	
	@Test 
	public void testMultipleClasses() throws SQLException, ClassNotFoundException, IllegalArgumentException, IllegalAccessException
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
	public void testGet() throws SQLException, IllegalArgumentException, IllegalAccessException
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
