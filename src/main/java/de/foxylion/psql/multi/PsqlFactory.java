package de.foxylion.psql.multi;

public class PsqlFactory {

	public Psql getConnection(String host, String database, String user, String password, boolean ssl, boolean verifySsl) {
		return new PsqlImpl(host, database, user, password, ssl, verifySsl);
	}
}
