package padsof.tests.db;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.Date;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import padsof.db.DBWrapper;
import padsof.system.Client;
import padsof.system.ImsersoClient;
import padsof.system.Vendor;

public class SystemObjectsTest
{

	private static DBWrapper db;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		db = new DBWrapper("testSystemDB");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
		db.close();
		db.delete();
	}

	@Test
	public void testSaveClient() throws IllegalArgumentException, IllegalAccessException, SQLException
	{
		Client client = new Client();
		client.setDNI("ASDkkk");
		client.setName("Myname");
		client.setSurname("Test");
		
		db.save(client);
		
		Client retrieved = db.getAll(Client.class).get(0);
		
		assertEquals(client, retrieved);
	}

	@Test
	public void testSaveImsersoClient() throws IllegalArgumentException, IllegalAccessException, SQLException
	{
		ImsersoClient client = new ImsersoClient();
		client.setDNI("ASDkkk");
		client.setName("Myname");
		client.setSurname("Test");
		client.setBirth(new Date());
		client.setSsNumber(1765770123);
			
		db.save(client);
		
		ImsersoClient retrieved = db.getAll(ImsersoClient.class).get(0);
		
		assertEquals(client, retrieved);
	}


	@Test
	public void testSaveVendor() throws Exception
	{
		Vendor vendor = new Vendor();
		vendor.setName("me");
		vendor.setPassword("test");
		vendor.setUser("asdasd");
		
		db.save(vendor);
		
		Vendor retrieved = db.getAll(Vendor.class).get(0);
		
		assertEquals(vendor, retrieved);
	}
}
