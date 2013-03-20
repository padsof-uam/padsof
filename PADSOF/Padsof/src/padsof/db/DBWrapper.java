/**
 * 
 */
package padsof.db;

import java.io.File;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

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

	private Dao<?, Long> getDaoFor(Class<?> cls) throws SQLException
	{
		if (daos.containsKey(cls.getName()))
			return daos.get(cls.getName());

		Dao<?, Long> dao = DaoManager.createDao(dataSource, cls);
		daos.put(cls.getName(), dao);

		TableUtils.createTableIfNotExists(dataSource, cls);

		return dao;
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
			fieldValues.put((String) values[i], values[i + 1]);

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
