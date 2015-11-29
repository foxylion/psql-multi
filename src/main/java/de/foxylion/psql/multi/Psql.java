package de.foxylion.psql.multi;

import java.io.Closeable;
import java.util.List;
import java.util.Map;

public interface Psql extends Closeable {

	void execute(String query);

	List<Map<String, String>> fetch(String query);
	
	@Override
	void close();
}
