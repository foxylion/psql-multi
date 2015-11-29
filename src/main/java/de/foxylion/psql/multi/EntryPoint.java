package de.foxylion.psql.multi;

import java.sql.SQLException;

import com.beust.jcommander.JCommander;

@SuppressWarnings("nls")
public class EntryPoint {

	public static void main(String[] args) throws SQLException {
		CommandlineParams params = new CommandlineParams();
		try {
			new JCommander(params, args);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			params.help = true;
		}

		if (params.help) {
			new JCommander(params).usage();
			System.exit(1);
		}

		new PsqlMulti(new PsqlFactory(), new DatabaseFilter(), params).run();
	}
}
