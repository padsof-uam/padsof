package padsof.tests;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import padsof.db.feeder.*;
import padsof.system.*;

public class DataBaseTester
{
	private DataBase DB;
	private Vendor testVendor;
	@Before
	public void setUp(){
		testVendor = new Vendor ("Victor","VicdeJuan","qwerty");
		DB = new DataBase();
	}
	@Test 
	public void testHotelFeeder() throws Exception{
		String file = new String("utils/Hoteles.csv");
		DB.DBFeeder(file);
		DataBase DB2 = new DataBase();
		DB2.DBFeederHotel(file);
		// TODO: alguna comprobacion mejor?
		assertEquals(DB2.getHotels().size(),DB.getHotels().size());
	}
	@Test
	public void testTravelFeeder() throws Exception{
		String file = new String("utils/ViajesOrganizados.csv");
		DB.DBFeeder(file);
		DataBase DB2 = new DataBase();
		DB2.DBFeederTravel(file);
		// TODO: alguna comprobacion mejor?
		assertEquals(DB2.getTravels().size(),DB.getTravels().size());
	
		// TODO: alguna comprobacion mejor?		
	}
	

}
