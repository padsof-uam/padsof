package padsof.tests.db;

import static org.junit.Assert.assertEquals;
import static padsof.tests.Assert.assertAreSameCollection;

import java.sql.SQLException;
import java.util.*;

import org.junit.*;

import padsof.bookings.*;
import padsof.db.DBWrapper;
import padsof.services.*;
import padsof.system.*;

public class BookingsTester
{

	private static BookingFactory bf;
	private static Vendor vendor;
	private static DBWrapper db;
	private static Client client;
	private static Date start;
	private static Date end;

	@BeforeClass
	public static void setUp() throws Exception
	{
		vendor = new Vendor();
		vendor.setName("Me");
		vendor.setPassword("ASD456");
		vendor.setUser("me");

		bf = new BookingFactory(vendor);

		db = new DBWrapper("testdb");

		client = new Client();
		client.setDNI("7691001D");
		client.setName("Test client");
		client.setSurname("Client");

		Calendar calendar = new GregorianCalendar();
		start = calendar.getTime();

		calendar.add(Calendar.DAY_OF_MONTH, 1);
		end = calendar.getTime();
	}

	@Test
	public void testCreateSaveFlightBooking() throws SQLException,
			IllegalArgumentException, IllegalAccessException
	{
		Flight f = new Flight();
		f.setLocalizer("EF1920");
		f.setCost(53.1);
		f.setDescription("Flight test.");
		f.setName("Flight to N");
		f.setPrice(60);

		FlightBooking fb = (FlightBooking) bf.book(f, client, start, end);

		Passenger a = new Passenger();
		a.setDNI("ASDJKJ");
		a.setName("Mey");
		a.setSurname("jkjk");

		Passenger b = new Passenger();
		b.setName("JFK");
		b.setDNI("0");
		b.setSurname("Grillo");

		fb.addPassenger(a);
		fb.addPassenger(b);

		ArrayList<Passenger> passengers = new ArrayList<Passenger>();
		passengers.add(a);
		passengers.add(b);

		db.save(fb);

		FlightBooking retrieved = db.getAll(FlightBooking.class).get(0);
		assertEquals(fb, retrieved);
		assertEquals(f, retrieved.getAssociatedService());
		assertEquals(vendor, retrieved.getVendorUser());
		assertEquals(client, retrieved.getClient());
		assertAreSameCollection(passengers, retrieved.getPassengers());
	}

	@Test
	public void testCreateSaveHotelBooking() throws IllegalArgumentException,
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

		HotelBooking hb = (HotelBooking) bf.book(h, client, start, end);

		db.save(hb);

		HotelBooking retrieved = db.getAll(HotelBooking.class).get(0);
		assertEquals(hb, retrieved);
		assertEquals(h, retrieved.getAssociatedService());
		assertEquals(vendor, retrieved.getVendorUser());
		assertEquals(client, retrieved.getClient());
	}

	@Test
	public void testCreateSaveImsersoTravel() throws IllegalArgumentException,
			IllegalAccessException, SQLException
	{
		ImsersoTravel it = new ImsersoTravel("Mallorca-temporada baja", 125, 8,
				7, "1 al 17 de diciembre, 1 al 31 de Enero", "Madrid",
				"Mallorca", "Vuelo, Hotel 3* en régimen de PC y desplazamiento");

		ImsersoClient c = new ImsersoClient();
		c.setSsNumber(7167390);
		c.setSurname("Test");

		ImsersoTravelBooking itb = (ImsersoTravelBooking) bf.book(it, c, start,
				end);

		db.save(itb);

		ImsersoTravelBooking retrieved = db.getAll(ImsersoTravelBooking.class)
				.get(0);
		assertEquals(itb, retrieved);
		assertEquals(it, retrieved.getAssociatedService());
		assertEquals(vendor, retrieved.getVendorUser());
		assertEquals(c, retrieved.getClient());
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

		TravelBooking tb = (TravelBooking) bf.book(t, client, start, end);

		db.save(tb);

		TravelBooking retrieved = db.getAll(TravelBooking.class).get(0);
		assertEquals(tb, retrieved);
		assertEquals(t, retrieved.getAssociatedService());
		assertEquals(vendor, retrieved.getVendorUser());
		assertEquals(client, retrieved.getClient());
	}

	@AfterClass
	public static void tearDown() throws SQLException
	{
		db.close();
		db.delete();
	}
}

/*
 * java.sql.SQLException: Unable to run insert stmt on object
 * padsof.bookings.FlightBooking@58278ad3: INSERT INTO `flightbooking`
 * (`bookingLocalizer` ,`associatedFlight_id` ,`client_id` ,`state` ,`start`
 * ,`end` ,`vendorUser_id` ) VALUES (?,?,?,?,?,?,?)
 * at com.j256.ormlite.misc.SqlExceptionUtil.create(SqlExceptionUtil.java:22)
 * at com.j256.ormlite.stmt.mapped.MappedCreate.insert(MappedCreate.java:124)
 * at com.j256.ormlite.stmt.StatementExecutor.create(StatementExecutor.java:394)
 * at com.j256.ormlite.dao.BaseDaoImpl.create(BaseDaoImpl.java:308)
 * at com.j256.ormlite.dao.BaseDaoImpl.createOrUpdate(BaseDaoImpl.java:334)
 * at padsof.db.DBWrapper.save(DBWrapper.java:70)
 * at
 * padsof.tests.db.BookingsTester.testCreateSaveFlightBooking(BookingsTester.java
 * :96)
 * at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
 * at
 * sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:57)
 * at
 * sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl
 * .java:43)
 * at java.lang.reflect.Method.invoke(Method.java:601)
 * at
 * org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod
 * .java:45)
 * at
 * org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.
 * java:15)
 * at
 * org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java
 * :42)
 * at
 * org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java
 * :20)
 * at org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:263)
 * at
 * org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java
 * :68)
 * at
 * org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java
 * :47)
 * at org.junit.runners.ParentRunner$3.run(ParentRunner.java:231)
 * at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:60)
 * at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:229)
 * at org.junit.runners.ParentRunner.access$000(ParentRunner.java:50)
 * at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:222)
 * at
 * org.junit.internal.runners.statements.RunBefores.evaluate(RunBefores.java:28)
 * at
 * org.junit.internal.runners.statements.RunAfters.evaluate(RunAfters.java:30)
 * at org.junit.runners.ParentRunner.run(ParentRunner.java:300)
 * at org.eclipse.jdt.internal.junit4.runner.JUnit4TestReference.run(
 * JUnit4TestReference.java:50)
 * at
 * org.eclipse.jdt.internal.junit.runner.TestExecution.run(TestExecution.java:
 * 38)
 * at
 * org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner
 * .java:467)
 * at
 * org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner
 * .java:683)
 * at
 * org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.run(RemoteTestRunner
 * .java:390)
 * at
 * org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.main(RemoteTestRunner
 * .java:197)
 * Caused by: java.sql.SQLException: Unable to run insert stmt on object
 * padsof.system.Client@1467056e: INSERT INTO `client` (`name` ,`surname` ,`DNI`
 * ) VALUES (?,?,?)
 * at com.j256.ormlite.misc.SqlExceptionUtil.create(SqlExceptionUtil.java:22)
 * at com.j256.ormlite.stmt.mapped.MappedCreate.insert(MappedCreate.java:124)
 * at com.j256.ormlite.stmt.StatementExecutor.create(StatementExecutor.java:394)
 * at com.j256.ormlite.dao.BaseDaoImpl.create(BaseDaoImpl.java:308)
 * at com.j256.ormlite.field.FieldType.createWithForeignDao(FieldType.java:916)
 * at com.j256.ormlite.stmt.mapped.MappedCreate.insert(MappedCreate.java:74)
 * ... 30 more
 * Caused by: java.sql.SQLException: [SQLITE_ERROR] SQL error or missing
 * database (no such table: client)
 * at org.sqlite.DB.newSQLException(DB.java:383)
 * at org.sqlite.DB.newSQLException(DB.java:387)
 * at org.sqlite.DB.throwex(DB.java:374)
 * at org.sqlite.NestedDB.prepare(NestedDB.java:134)
 * at org.sqlite.DB.prepare(DB.java:123)
 * at org.sqlite.PrepStmt.<init>(PrepStmt.java:42)
 * at org.sqlite.Conn.prepareStatement(Conn.java:404)
 * at org.sqlite.Conn.prepareStatement(Conn.java:399)
 * at org.sqlite.Conn.prepareStatement(Conn.java:383)
 * at org.sqlite.Conn.prepareStatement(Conn.java:387)
 * at
 * com.j256.ormlite.jdbc.JdbcDatabaseConnection.insert(JdbcDatabaseConnection.
 * java:171)
 * at com.j256.ormlite.stmt.mapped.MappedCreate.insert(MappedCreate.java:89)
 * ... 34 more
 */