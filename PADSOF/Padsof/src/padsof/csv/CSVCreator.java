package padsof.csv;

import java.util.List;

/**
 * Interface with gives a method to parse from a CSV line to an object.
 * 
 * @author Víctor de Juan Sanz - Guillermo Julián Moreno
 * @param <T>
 *            Destination type.
 */
public interface CSVCreator<T>
{
	/**
	 * Parse a line to an object.
	 * 
	 * @param fields
	 *            List of fields.
	 * @return Object created.
	 */
	public T parseLine(List<String> fields);
}
