package padsof.db;

import java.sql.SQLException;

import com.j256.ormlite.stmt.*;

public class Query<T>
{
	private QueryBuilder<T, Long> queryBuilder;
	private Where<T, Long> whereClause = null;
	Class<T> queryingClass;
	
	private Where<T, Long> getWhere()
	{
		if(whereClause == null)
			whereClause = queryBuilder.where();
		else
			whereClause.and();
		
		return whereClause;
	}
	
	Query(QueryBuilder<T, Long> queryBuilder, Class<T> queryingClass)
	{
		this.queryBuilder = queryBuilder;
		this.queryingClass = queryingClass;
	}
	
	public void setMin(String field, Object min) throws SQLException
	{
		Where<T, Long> where = getWhere();
		where.gt(field, min);
	}
	
	public void setMax(String field, Object max) throws SQLException
	{
		Where<T, Long> where = getWhere();
		where.lt(field, max);
	}
	
	public void setRange(String field, Object min, Object max) throws SQLException
	{
		Where<T, Long> where = getWhere();
		where.lt(field, max);
		where.and();
		where.gt(field, min);
	}
	
	public void setEquals(String field, Object value) throws SQLException
	{
		Where<T, Long> where = getWhere();
		where.eq(field, value);
	}

	public PreparedQuery<T> prepare() throws SQLException
	{
		return queryBuilder.prepare();
	}
}
