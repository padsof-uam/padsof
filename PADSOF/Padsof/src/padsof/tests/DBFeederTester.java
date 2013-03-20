package padsof.tests;

import static padsof.tests.Assert.assertFieldWiseEquals;

import java.util.List;

import org.junit.*;

import padsof.db.feeder.DBFeeder;
import padsof.services.*;

public class DBFeederTester
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
				"España",
				"Madrid",
				"NH Palacio de Tepa",
				"91 555555",
				"San Sebastian 2",
				"28012",
				"4",
				80,
				120,
				160,
				15,
				40,
				60,
				"Restaurante, Bar, Recepción 24 horas, Prensa , Habitaciones no fumadores, Adaptado personas de movilidad reducida, Ascensor, Registro de entrada y salida exprés, Caja fuerte, Calefacción, Hotel de diseño, Guardaequipaje, Aire acondicionado, Zona de fumadores, Restaurante (a la carta), free wifi");
		Hotel h2 = new Hotel(
				"España",
				"Madrid",
				"Hotel Lope de Vega",
				"91 444444",
				"Lope de Vega 49",
				"28014",
				"3",
				75,
				116,
				0,
				0,
				0,
				0,
				"Bar, Recepción 24 horas, Prensa ,  Habitaciones no fumadores, Adaptado personas de movilidad reducida, Ascensor, Caja fuerte, Calefacción, Guardaequipaje, Aire acondicionado, no wifi");

		DBFeeder feeder = new DBFeeder();
		List<Hotel> hotels = feeder.loadHotelData(file);

		assertFieldWiseEquals(h1, hotels.get(0), "id");
		assertFieldWiseEquals(h2, hotels.get(1), "id");
	}

	@Test
	public void testImsersoTravelFeeder() throws Exception
	{
		ImsersoTravel T1 = new ImsersoTravel("Mallorca-temporada baja", 125, 8,
				7, "1 al 17 de diciembre, 1 al 31 de Enero", "Madrid",
				"Mallorca", "Vuelo, Hotel 3* en régimen de PC y desplazamiento");
		ImsersoTravel T2 = new ImsersoTravel("Mallorca-temporada alta", 235, 8,
				7, "Lunes de Febrero a Noviembre", "Madrid", "Mallorca",
				"Vuelo, Hotel 3* en régimen de PC y desplazamiento");
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
				"Halcón Viajes",
				"91 218 21 28",
				190,
				3,
				2,
				"Todos los lunes de Enero-Mayo y Octubre-Diciembre. Todos los días de Junio a Septiembre",
				"Madrid", "Mallorca",
				"Hotel Be Live Punta Amer 4* en Media Pensión.");
		Travel T2 = new Travel(
				"Menorca",
				"Halcón Viajes",
				"91 218 21 28",
				226,
				6,
				5,
				"Todos los lunes de Enero-Mayo y Octubre-Diciembre. Todos los días de Junio a Septiembre",
				"Madrid", "Menorca",
				"Hotel Platja Gran 3* sup en Alojamiento y Desayuno");
		List<Travel> travels = new DBFeeder().loadTravelData(file);

		assertFieldWiseEquals("T1", T1, travels.get(0), "id");
		assertFieldWiseEquals("T2", T2, travels.get(1), "id");
	}

}
