package padsof.tests;

import static padsof.tests.Assert.assertFieldWiseEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import padsof.db.feeder.DBFeeder;
import padsof.services.Hotel;
import padsof.services.ImsersoTravel;
import padsof.services.Travel;

public class DataBaseTester
{
	private DBFeeder DB;
	@Before
	public void setUp() throws Exception
	{
		DB = new DBFeeder();
	}

	@Test
	public void testHotelFeeder() throws Exception
	{
		String file = new String("utils/Hoteles.csv");
		Hotel h1 = new Hotel(
				"Espa�a",
				"Madrid",
				"NH Palacio de Tepa",
				"91 555555",
				"San Sebastian 2",
				"28012",
				"4",
				(double) 80,
				(double) 120,
				(double) 160,
				(double) 15,
				(double) 40,
				(double) 60,
				"Restaurante, Bar, Recepci�n 24 horas, Prensa , Habitaciones no fumadores, Adaptado personas de movilidad reducida, Ascensor, Registro de entrada y salida expr�s, Caja fuerte, Calefacci�n, Hotel de dise�o, Guardaequipaje, Aire acondicionado, Zona de fumadores, Restaurante (a la carta), free wifi");
		Hotel h2 = new Hotel(
				"Espa�a",
				"Madrid",
				"Hotel Lope de Vega",
				"91 444444",
				"Lope de Vega 49",
				"28014",
				"3",
				(double) 75,
				(double) 116,
				(double) 0,
				(double) 0,
				(double) 0,
				(double) 0,
				"Bar, Recepci�n 24 horas, Prensa ,  Habitaciones no fumadores, Adaptado personas de movilidad reducida, Ascensor, Caja fuerte, Calefacci�n, Guardaequipaje, Aire acondicionado, no wifi");

		DBFeeder feeder = new DBFeeder();
		List<Hotel> hotels = feeder.loadHotelData(file);
		
		assertFieldWiseEquals(h1, hotels.get(0), "id");
		assertFieldWiseEquals(h2, hotels.get(1), "id");
	}

	@Test
	public void testImsersoTravelFeeder() throws Exception
	{
		ImsersoTravel T1 = new ImsersoTravel("Mallorca-temporada baja",
				(double) 125, 8, 7, "1 al 17 de diciembre, 1 al 31 de Enero",
				"Madrid", "Mallorca",
				"Vuelo, Hotel 3* en r�gimen de PC y desplazamiento");
		ImsersoTravel T2 = new ImsersoTravel("Mallorca-temporada alta",
				(double) 235, 8, 7, "Lunes de Febrero a Noviembre", "Madrid",
				"Mallorca",
				"Vuelo, Hotel 3* en r�gimen de PC y desplazamiento");
		String file = new String("utils/ViajesIMSERSO.csv");
		List<ImsersoTravel> travels = DB.loadImsersoTravelData(file);
		
		assertFieldWiseEquals(T1, travels.get(0), "id");
		assertFieldWiseEquals(T2, travels.get(1), "id");
	}

	@Test
	public void testTravelFeeder() throws Exception
	{
		String file = new String("utils/ViajesOrganizados.csv");
		Travel T1 = new Travel(
				"Mallorca-1",
				"Halc�n Viajes",
				"91 218 21 28",
				(double) 190,
				3,
				2,
				"Todos los lunes de Enero-Mayo y Octubre-Diciembre. Todos los d�as de Junio a Septiembre",
				"Madrid", "Mallorca",
				"Hotel Be Live Punta Amer 4* en Media Pensi�n.");
		Travel T2 = new Travel(
				"Menorca",
				"Halc�n Viajes",
				"91 218 21 28",
				(double) 226,
				6,
				5,
				"Todos los lunes de Enero-Mayo y Octubre-Diciembre. Todos los d�as de Junio a Septiembre",
				"Madrid", "Menorca",
				"Hotel Platja Gran 3* sup en Alojamiento y Desayuno");
		List<Travel> travels = new DBFeeder().loadTravelData(file);
		
		assertFieldWiseEquals("T1", T1, travels.get(0), "id");
		assertFieldWiseEquals("T2", T2, travels.get(1), "id");
	}

}
