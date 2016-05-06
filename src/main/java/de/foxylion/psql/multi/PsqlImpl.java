package de.foxylion.psql.multi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PsqlImpl implements Psql {

	private final Connection connection;

	public PsqlImpl(String host, String db, String user, String pass) {
		try {
			connection = DriverManager.getConnection("jdbc:postgresql://" + host + "/" + db, user, pass);
		} catch (SQLException e) {
			throw new RuntimeException("Failed to open database connection: " + e.getMessage());
		}
	}

	public void execute(String query) {
		try (PreparedStatement stmt = connection.prepareCall(query)) {
			stmt.execute();
		} catch (SQLException e) {
			throw new RuntimeException("Failed to execute query: " + e.getMessage(), e);
		}
	}

	public List<Map<String, String>> fetch(String query) {
		List<Map<String, String>> resultList = new ArrayList<>();

		try (PreparedStatement stmt = connection.prepareCall(query)) {
			try (ResultSet result = stmt.executeQuery()) {
				while (result.next()) {
					Map<String, String> resultRow = new HashMap<>();
					for (int c = 1; c <= result.getMetaData().getColumnCount(); c++) {
						resultRow.put(result.getMetaData().getColumnName(c), result.getString(c));
					}
					resultList.add(resultRow);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Failed to execute query: " + e.getMessage(), e);
		}

		return resultList;
	}

	@Override
	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			throw new RuntimeException("Failed to close database connection: " + e.getMessage());
		}
	}
}
