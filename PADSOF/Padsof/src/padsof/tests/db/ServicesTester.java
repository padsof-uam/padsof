package padsof.tests.db;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.*;

import padsof.db.DBWrapper;
import padsof.services.*;

public class ServicesTester
{
	private static DBWrapper db;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		db = new DBWrapper("testServiceDB");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
		db.close();
		db.delete();
	}

	@Test
	public void testCreateSaveFlight() throws SQLException,
			IllegalArgumentException, IllegalAccessException
	{
		Flight f = new Flight();
		f.setLocalizer("EF1920");
		f.setCost(53.1);
		f.setDescription("Flight test.");
		f.setName("Flight to N");
		f.setPrice(60);

		db.save(f);

		Flight retrieved = db.getAll(Flight.class).get(0);

		assertEquals(f, retrieved);
	}

	@Test
	public void testCreateSaveHotel() throws IllegalArgumentException,
			IllegalAccessException, SQLException
	{
		Hotel h = new Hotel(
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

		db.save(h);

		Hotel retrieved = db.getAll(Hotel.class).get(0);

		assertEquals(h, retrieved);
	}

	@Test
	public void testCreateSaveImsersoTravel() throws IllegalArgumentException,
			IllegalAccessException, SQLException
	{
		ImsersoTravel it = new ImsersoTravel("Mallorca-temporada baja", 125, 8,
				7, "1 al 17 de diciembre, 1 al 31 de Enero", "Madrid",
				"Mallorca", "Vuelo, Hotel 3* en régimen de PC y desplazamiento");

		db.save(it);

		ImsersoTravel retrieved = db.getAll(ImsersoTravel.class).get(0);

		assertEquals(it, retrieved);
	}

	@Test
	public void testCreateSaveTravel() throws IllegalArgumentException,
			IllegalAccessException, SQLException
	{
		Travel t = new Travel(
				"Mallorca-1",
				"Halc�n Viajes",
				"91 218 21 28",
				190,
				3,
				2,
				"Todos los lunes de Enero-Mayo y Octubre-Diciembre. Todos los d�as de Junio a Septiembre",
				"Madrid", "Mallorca",
				"Hotel Be Live Punta Amer 4* en Media Pensi�n.");

		db.save(t);

		Travel retrieved = db.getAll(Travel.class).get(0);
		assertTrue(t.equals(retrieved));
	}

}
