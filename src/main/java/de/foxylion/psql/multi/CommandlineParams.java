package de.foxylion.psql.multi;

import com.beust.jcommander.Parameter;

class CommandlineParams {

	@Parameter(names = { "--help" }, description = "Prints this help")
	boolean help;

	@Parameter(names = { "--query", "-q" }, required = true, description = "Command which should be executed")
	String command;

	@Parameter(names = { "--host", "-h" }, description = "Hostname including port")
	String host = "127.0.0.1:5432";

	@Parameter(names = { "--user", "-u" }, description = "User to authenticate")
	String user = "postgres";

	@Parameter(names = { "--pass", "-p" }, description = "Password to authenticate")
	String pass = "postgres";

	@Parameter(names = { "--include", "-i" }, description = "Include databases with the following regex pattern")
	String includePattern = null;

	@Parameter(names = { "--exclude", "-e" }, description = "Exclude databases with the following regex pattern")
	String excludePattern = null;

	@Parameter(names = { "--results",
			"-r" }, description = "When using a SELECT statement as the command results will be printed to console")
	boolean showResults = false;

	@Parameter(names = { "--force", "-f" }, description = "Force to continue when executed query fails on a database")
	boolean force = false;
}