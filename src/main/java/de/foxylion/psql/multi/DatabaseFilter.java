package de.foxylion.psql.multi;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DatabaseFilter {

	public List<String> filter(List<String> databases, String includePatternStr, String excludePatternStr) {
		Pattern includePattern = Pattern.compile(Optional.ofNullable(includePatternStr).orElse(".*"));
		Pattern excludePattern = Pattern.compile(Optional.ofNullable(excludePatternStr).orElse("^$"));
		
		return databases.stream() //
				.filter((db) -> !db.equals("postgres")) //
				.filter((db) -> includePattern.matcher(db).matches()) //
				.filter((db) -> !excludePattern.matcher(db).matches()) //
				.collect(Collectors.toList());
	}
}
