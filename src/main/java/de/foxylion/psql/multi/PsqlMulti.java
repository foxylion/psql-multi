package de.foxylion.psql.multi;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.Joiner;

public class PsqlMulti {
	private final PsqlFactory psqlFactory;
	private final DatabaseFilter dbFilter;
	private final CommandlineParams params;

	public PsqlMulti(PsqlFactory psqlFactory, DatabaseFilter dbFilter, CommandlineParams params) {
		this.psqlFactory = psqlFactory;
		this.dbFilter = dbFilter;
		this.params = params;
	}

	public void run() {
		List<String> databases = collectDatabases();
		databases = dbFilter.filter(databases, params.includePattern, params.excludePattern);

		for (String db : databases) {
			System.out.println("Executing on " + db + "...");
			runQuery(db);
		}
	}

	private void runQuery(String db) {
		try (Psql psql = psqlFactory.getConnection(params.host, db, params.user, params.pass)) {
			if (!params.showResults) {
				psql.execute(params.command);
			} else {
				psql.fetch(params.command).stream().forEach((row) -> {
					System.out.println(Joiner.on(" | ").join(row.values()));
				});
			}
		} catch (Exception e) {
			if (params.force) {
				e.printStackTrace();
			} else
				throw e;
		}
	}

	private List<String> collectDatabases() {
		try (Psql psql = psqlFactory.getConnection(params.host, "/postgres", params.user, params.pass)) {
			return psql.fetch("SELECT datname FROM pg_database WHERE datistemplate = false;").stream()
					.map((row) -> row.get("datname")) //
					.collect(Collectors.toList());
		}
	}
}
