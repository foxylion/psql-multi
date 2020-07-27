package de.foxylion.psql.multi;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.base.Joiner;

public class PsqlMulti {
    private final PsqlFactory psqlFactory;
    private final DatabaseFilter dbFilter;
    private final CommandlineParams params;

    public PsqlMulti(PsqlFactory psqlFactory,
            DatabaseFilter dbFilter,
            CommandlineParams params) {
        this.psqlFactory = psqlFactory;
        this.dbFilter = dbFilter;
        this.params = params;
    }

    public void run() {
        List<String> databases = collectDatabases();
        databases = dbFilter.filter(databases, params.includePattern, params.excludePattern);

        int counter = 1;
        for (String db : databases) {
            System.out.println("Executing on " + db + " (" + (counter++) + " of " + databases.size() + ")...");
            runQuery(db);
        }
    }

    private void runQuery(String db) {
        try (Psql psql = psqlFactory.getConnection(params.host, db, params.user, params.pass, params.ssl, params.noSslVerify)) {
            if (params.showResults) {
                psql.fetch(params.command)
                        .forEach((row) -> System.out.println(Joiner.on(" | ")
                                .join(rowToColumns(row))));
                if (params.resultsToCsv != null) {
                    List<String> csvRows = psql.fetch(params.command)
                            .stream()
                            .map(row -> db + "\t" + Joiner.on("\t")
                                    .join(rowToColumns(row)))
                            .collect(Collectors.toList());
                    Files.write(Paths.get(params.resultsToCsv), csvRows, StandardCharsets.UTF_8, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
                }
            } else {
                psql.execute(params.command);
            }
        } catch (Exception e) {
            if (params.force) {
                e.printStackTrace();
            } else {
                throw new RuntimeException(e);
            }
        }
    }

    private List<String> rowToColumns(Map<String, String> row) {
        return row.values()
                .stream()
                .map((column) -> column != null ? column : "NULL")
                .collect(Collectors.toList());
    }

    private List<String> collectDatabases() {
        try (Psql psql = psqlFactory.getConnection(params.host, "postgres", params.user, params.pass, params.ssl, params.noSslVerify)) {
            return psql.fetch("SELECT datname FROM pg_database WHERE datistemplate = false;")
                    .stream()
                    .map((row) -> row.get("datname")) //
                    .collect(Collectors.toList());
        }
    }
}
