package de.foxylion.psql.multi;

public class PsqlFactory {

	public Psql getConnection(String host, String database, String user, String password) {
		return new PsqlImpl(host, database, user, password);
	}
}
