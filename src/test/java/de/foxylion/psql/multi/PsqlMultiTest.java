package de.foxylion.psql.multi;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;

public class PsqlMultiTest {

	private PsqlFactory psqlFactory;
	private Map<String, Psql> psqlMocks;

	@Before
	void setup() {
		psqlMocks = new HashMap<>();
		
		psqlFactory = mock(PsqlFactory.class);
		when(psqlFactory.getConnection(any(), any(), any(), any(), anyBoolean(), anyBoolean())).then((a) -> {
			String dbName = a.getArgumentAt(2, String.class);
			if(!psqlMocks.containsKey(dbName)) {
				Psql psql = mock(Psql.class);
				psqlMocks.put(dbName, psql);
			}
			return psqlMocks.get(dbName); 
		});
	}
}
