package de.foxylion.psql.multi;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

public class DatabaseFilterTest {

	private DatabaseFilter filter;

	@Before
	public void setup() {
		filter = new DatabaseFilter();
	}

	@Test
	public void testIncludePattern() {
		List<String> result = filter.filter(Lists.newArrayList("pfx_20010", "pfx_20020", "20000"), "pfx_.*", null);

		assertThat(result).containsOnly("pfx_20010", "pfx_20020");
	}

	@Test
	public void excludeIncludePattern() {
		List<String> result = filter.filter(Lists.newArrayList("pfx_20010", "pfx_20020", "20000"), null, "pfx_.*");

		assertThat(result).containsOnly("20000");
	}

	@Test
	public void testExcludePostgresDatabase() {
		List<String> result = filter.filter(Lists.newArrayList("pfx_20020", "pfx_20030", "postgres"), null, null);

		assertThat(result).containsOnly("pfx_20020", "pfx_20030");
	}

	@Test
	public void testIntersectOfIncludeAndExclude() {
		List<String> result = filter.filter(Lists.newArrayList("pfx_20020", "pfx_20030", "20000"), "pfx_.*",
				".*20030.*");

		assertThat(result).containsOnly("pfx_20020");
	}
}
