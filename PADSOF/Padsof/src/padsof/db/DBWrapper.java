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
 * @author gjulianm
 */
public class DBWrapper
{
	private static DBWrapper instance;

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
	 * 
	 * @param dbName
	 * @throws SQLException
	 */
	public DBWrapper(String dbName) throws SQLException
	{
		dbFile = dbName + ".sqlite";
		dataSource = new JdbcConnectionSource("jdbc:sqlite:" + dbFile);
		instance = this;
	}

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

	@SuppressWarnings("unchecked")
	public <T> CreateOrUpdateStatus save(T item) throws SQLException,
			IllegalArgumentException, IllegalAccessException
	{
		Dao<T, ?> dao = (Dao<T, ?>) getDaoFor(item.getClass());

		return dao.createOrUpdate(item);
	}

	public void close() throws SQLException
	{
		if (dataSource != null)
			dataSource.close();
	}

	public void delete()
	{
		File f = new File(dbFile);
		f.delete();
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getAll(Class<T> cls) throws SQLException
	{
		Dao<T, Long> dao = (Dao<T, Long>) getDaoFor(cls);

		return dao.queryForAll();
	}

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

	public void clear(Class<?> cls) throws SQLException
	{
		TableUtils.clearTable(dataSource, cls);
	}

	@SuppressWarnings("unchecked")
	public <T> void delete(T item) throws SQLException
	{
		Dao<T, Long> dao = (Dao<T, Long>) getDaoFor(item.getClass());
		dao.delete(item);
	}

	public void clear() throws ClassNotFoundException, SQLException
	{
		for (String cls : daos.keySet())
			clear(Class.forName(cls));
	}

	public <T> void save(Iterable<T> items) throws SQLException,
			IllegalArgumentException, IllegalAccessException
	{
		for (T item : items)
			save(item);
	}
}
