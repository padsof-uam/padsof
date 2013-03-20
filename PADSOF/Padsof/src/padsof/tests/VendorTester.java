package padsof.tests;

import static org.junit.Assert.assertTrue;

import org.junit.*;

import padsof.db.DBWrapper;
import padsof.system.Vendor;

public class VendorTester
{
	private static Vendor testVendor;
	private static DBWrapper db;

	@BeforeClass
	public static void setUp() throws Exception
	{
		testVendor = new Vendor("Victor", "VicdeJuan", "qwerty");
		db = new DBWrapper("testVendor");
	}

	@AfterClass
	public static void tearDown() throws Exception
	{
		db.close();
		db.delete();
	}

	/**
	 * Comprueba que el hashing de contraseñas funcione correctamente.
	 * @throws Exception
	 */
	@Test
	public void testPasswordHashing() throws Exception
	{
		testVendor.setPassword("asdfgh");
		assertTrue(testVendor.checkPassword("asdfgh"));
	}

}
