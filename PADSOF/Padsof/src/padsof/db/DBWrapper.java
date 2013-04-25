/**
 * 
 */
package padsof.db;

import java.io.File;
import java.lang.reflect.*;
import java.sql.SQLException;
import java.util.*;

import padsof.utils.Reflection;

import com.j256.ormlite.dao.*;
import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.j256.ormlite.field.*;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.*;

/**
 * Wrapper object for accesing the database.
 * 
 * @author Víctor de Juan Sanz - Guillermo Julián Moreno
 */
public class DBWrapper
{
	private static DBWrapper instance;

	/**
	 * Gets the current DBWrapper instance running. If there isn't any, create a
	 * new one.
	 * 
	 * @return Instance.
	 * @throws SQLException
	 */
	public static DBWrapper getInstance() throws SQLException
	{
		if (instance == null)
			new DBWrapper("productionDB");

		return instance;
	}

	private HashMap<String, Dao<?, Long>> daos = new HashMap<String, Dao<?, Long>>();
	private ConnectionSource dataSource = null;
	private String dbFile;

	/**
	 * Constructor. Public for test purposes.
	 * Opens the connection to the database.
	 * 
	 * @param dbName
	 * @throws SQLException
	 */
	public DBWrapper(String dbName) throws SQLException
	{
		dbFile = dbName + ".sqlite";
		instance = this;
		open();
	}

	/**
	 * Open the connection to the database.
	 * 
	 * Note: It's not required (and not recommended) to call it after
	 *       instatiating the DBWrapper.
	 * @throws SQLException
	 */
	public void open() throws SQLException
	{
		dataSource = new JdbcConnectionSource("jdbc:sqlite:" + dbFile);
	}

	/**
	 * Gets the DAO for the class and creates the corresponding necessary tables
	 * if they don't exist.
	 * 
	 * @param cls
	 *            Class.
	 * @return DAO.
	 * @throws SQLException
	 */
	public Dao<?, Long> getDaoFor(Class<?> cls) throws SQLException
	{
		if (daos.containsKey(cls.getName()))
			return daos.get(cls.getName());

		Dao<?, Long> dao = DaoManager.createDao(dataSource, cls);
		daos.put(cls.getName(), dao);

		ensureTablesGeneratedFor(cls);

		return dao;
	}

	private List<Class<?>> classesWithCreatedTables = new ArrayList<Class<?>>();

	private void ensureTablesGeneratedFor(Class<?> cls) throws SQLException
	{
		Stack<Class<?>> pendingClasses = new Stack<Class<?>>();

		pendingClasses.push(cls);

		while (!pendingClasses.empty())
		{
			cls = pendingClasses.pop();

			if (classesWithCreatedTables.contains(cls))
				continue;

			for (Field f : Reflection.getAllFieldsFrom(cls))
			{
				Class<?> type = f.getType();
				if (type.isAnnotationPresent(DatabaseTable.class)
						&& !pendingClasses.contains(type))
					pendingClasses.push(type);
				else if (f.isAnnotationPresent(ForeignCollectionField.class)
						&& Collection.class.isAssignableFrom(type))
				{
					Class<?> genericType = (Class<?>) ((ParameterizedType) f
							.getGenericType()).getActualTypeArguments()[0];
					if (!pendingClasses.contains(genericType))
						pendingClasses.push(genericType);
				}
			}

			TableUtils.createTableIfNotExists(dataSource, cls);
			classesWithCreatedTables.add(cls);
		}
	}

	/**
	 * Saves an object in the database, either creating it or updating it.
	 * 
	 * @param item
	 *            Item to save.
	 * @return Status indicating the output of the operation.
	 * @throws SQLException
	 *             SQL error occurred during the operation.
	 * @throws IllegalArgumentException
	 *             An exception occurred during the exploration of the object to
	 *             create necessary related tables.
	 * @throws IllegalAccessException
	 *             The Java Security Manager doesn't allow accessing certain
	 *             fields of this object.
	 */
	@SuppressWarnings("unchecked")
	public <T> CreateOrUpdateStatus save(T item) throws SQLException,
			IllegalArgumentException, IllegalAccessException
	{
		Dao<T, ?> dao = (Dao<T, ?>) getDaoFor(item.getClass());

		return dao.createOrUpdate(item);
	}

	/**
	 * Close the connection to the DB.
	 * 
	 * @throws SQLException
	 */
	public void close() throws SQLException
	{
		if (dataSource != null)
			dataSource.close();
	}

	/**
	 * Deletes the DB file.
	 */
	public void delete()
	{
		File f = new File(dbFile);
		f.delete();
	}

	/**
	 * Gets all the objects in the DB of the given class.
	 * 
	 * @param cls
	 *            Class to retrieve.
	 * @return List of objects.
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getAll(Class<T> cls) throws SQLException
	{
		Dao<T, Long> dao = (Dao<T, Long>) getDaoFor(cls);

		return dao.queryForAll();
	}

	/**
	 * Query the database with the given filters. The method receives a pair of
	 * field names and their values.
	 * For example, for a class with a field {@code String name} the call to
	 * this method with parameters "name", "peter" will return all elements
	 * whose field name equals to peter.
	 * 
	 * @param cls
	 *            Class of the queried objects.
	 * @param values
	 *            Pair of class' field name and its wanted value.
	 * @return List of objects who match the query.
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> get(Class<T> cls, Object... values) throws SQLException
	{
		HashMap<String, Object> fieldValues = new HashMap<String, Object>();

		for (int i = 0; i < values.length - 1; i += 2)
		{
			String fieldName = (String) values[i];
			Object target = values[i + 1];

			Field f = Reflection.getField(cls, fieldName);

			if (f != null)
			{
				DatabaseField df = f.getAnnotation(DatabaseField.class);
				if (df != null && df.foreign()
						&& DBObject.class.isInstance(target))
				{
					fieldName += "_id";
					target = ((DBObject) target).id;
				}
			}

			fieldValues.put(fieldName, target);
		}

		Dao<T, Long> dao = (Dao<T, Long>) getDaoFor(cls);
		return dao.queryForFieldValues(fieldValues);
	}

	/**
	 * Clear the table for a given class.
	 * 
	 * @param cls
	 *            Class.
	 * @throws SQLException
	 */
	public void clear(Class<?> cls) throws SQLException
	{
		TableUtils.clearTable(dataSource, cls);
	}

	/**
	 * Deletes an item in the database.
	 * 
	 * @param item
	 *            Item.
	 * @throws SQLException
	 *             Error in operation.
	 */
	@SuppressWarnings("unchecked")
	public <T> void delete(T item) throws SQLException
	{
		Dao<T, Long> dao = (Dao<T, Long>) getDaoFor(item.getClass());
		dao.delete(item);
	}

	/**
	 * Clears all the database.
	 * 
	 * @throws SQLException
	 */
	public void clear() throws SQLException
	{
		close();
		delete();
		daos.clear();
		classesWithCreatedTables.clear();
		open();
	}

	/**
	 * Saves a collection of items using Save.
	 * 
	 * @param items
	 *            List of items.
	 * @throws SQLException
	 *             SQL error occurred during the operation.
	 * @throws IllegalArgumentException
	 *             An exception occurred during the exploration of the object to
	 *             create necessary related tables.
	 * @throws IllegalAccessException
	 *             The Java Security Manager doesn't allow accessing certain
	 *             fields of this object.
	 */
	public <T> void saveCollection(Iterable<T> items) throws SQLException,
			IllegalArgumentException, IllegalAccessException
	{
		for (T item : items)
			save(item);
	}

	public <T> Query<T> prepareQuery(Class<T> cls) throws SQLException
	{
		@SuppressWarnings("unchecked")
		Dao<T, Long> dao = (Dao<T, Long>) getDaoFor(cls);
		return new Query<T>(dao.queryBuilder(), cls);
	}

	public <T> List<T> executeQuery(Query<T> query) throws SQLException
	{
		@SuppressWarnings("unchecked")
		Dao<T, Long> dao = (Dao<T, Long>) getDaoFor(query.queryingClass);
		return dao.query(query.prepare());
	}
}
