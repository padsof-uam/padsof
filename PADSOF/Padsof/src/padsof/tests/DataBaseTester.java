package padsof.tests;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import padsof.db.feeder.*;
import padsof.services.Hotel;
import padsof.services.ImsersoTravel;
import padsof.services.Travel;
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
		Hotel h1 = new Hotel("España","Madrid","NH Palacio de Tepa","91 555555",
				"San Sebastian 2","28012","4",(double)80,(double)120,
				(double)160,(double)15,(double)40,(double)60, "Restaurante, Bar, Recepción 24 horas, Prensa , Habitaciones no fumadores, Adaptado personas de movilidad reducida, Ascensor, Registro de entrada y salida exprés, Caja fuerte, Calefacción, Hotel de diseño, Guardaequipaje, Aire acondicionado, Zona de fumadores, Restaurante (a la carta), free wifi");
		Hotel h2 = new Hotel("España","Madrid","Hotel Lope de Vega","91 444444","Lope de Vega 49","28014","3",(double)75,(double)116,(double)0,(double)0,(double)0,(double)0,"Bar, Recepción 24 horas, Prensa ,  Habitaciones no fumadores, Adaptado personas de movilidad reducida, Ascensor, Caja fuerte, Calefacción, Guardaequipaje, Aire acondicionado, no wifi");
		
		DataBase DB2 = new DataBase();
		DB2.DBFeederHotel(file);
		//assertTrue(DB2.getHotels().contains(h1));
		//assertEquals(DB2.getHotels().indexOf(h1),);
		}
	@Test
	public void testImsersoTravelFeeder() throws Exception{
		ImsersoTravel T1 = new ImsersoTravel("Mallorca-temporada baja", (double)125,8,7,"1 al 17 de diciembre, 1 al 31 de Enero","Madrid","Mallorca","Vuelo, Hotel 3* en régimen de PC y desplazamiento");
		ImsersoTravel T2 = new ImsersoTravel("Mallorca-temporada alta",(double)235,8,7,"Lunes de Febrero a Noviembre","Madrid","Mallorca","Vuelo, Hotel 3* en régimen de PC y desplazamiento");
		String file = new String ("utils/ViajesIMSERSO.csv");
		DB.DBFeeder(file);
		//assertEquals(DB.getIMTravels().indexOf(T1),1);
		//assertEquals(DB.getIMTravels().indexOf(T2),2);
	}
	@Test
	public void testTravelFeeder() throws Exception{
		String file = new String("utils/ViajesOrganizados.csv");
		Travel T1 = new Travel ("Mallorca-1","Halcón Viajes","91 218 21 28",(double)190,3,2,"Todos los lunes de Enero-Mayo y Octubre-Diciembre. Todos los días de Junio a Septiembre","Madrid","Mallorca","Hotel Be Live Punta Amer 4* en Media Pensión.");
		Travel T2 = new Travel ("Menorca","Halcón Viajes","91 218 21 28",(double)226,6,5,"Todos los lunes de Enero-Mayo y Octubre-Diciembre. Todos los días de Junio a Septiembre","Madrid","Menorca","Hotel Platja Gran 3* sup en Alojamiento y Desayuno");
		DB.DBFeeder(file);
		Assert.assertListContains(DB.getTravels(),T1);
		//assertEquals(DB.getIMTravels().indexOf(T1),1);
		//assertEquals(DB.getIMTravels().indexOf(T2),2);
	}

	

}
