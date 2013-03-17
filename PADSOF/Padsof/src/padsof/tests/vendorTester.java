package padsof.tests;

import padsof.system.Vendor;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class vendorTester
{
	private Vendor testVendor;
	private Vendor testAdmin;
	/**
	 * 
	 */
	@Before
	public void setUp(){
		testVendor = new Vendor ("Victor","VicdeJuan","qwerty");
		testAdmin = new Vendor ("Victor","VicdeJuan","qwerty");
		testAdmin.becomeAdmin();
	}
	@Test (expected = UnsupportedOperationException.class)
	public void checkNotAdmin(){
		Vendor testVendor1 = testAdmin.createVendor("Victor","VicdeJuan","qwerty");
		testVendor1.createVendor(new String(), new String(), new String());		
	}
	
	@Test (expected = UnsupportedOperationException.class)
	public void checkChangePass(){
		testAdmin.changePassword(testVendor, "asdfgh");
		assertEquals(testVendor.getPassword(),"asdfgh");
		testVendor.changePassword(testAdmin, "asdfgh");
	}
	
	
}
