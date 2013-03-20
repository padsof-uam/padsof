package padsof.csv;

import java.util.List;

public interface CSVCreator<T>
{
	public T parseLine(List<String> fields);
}
