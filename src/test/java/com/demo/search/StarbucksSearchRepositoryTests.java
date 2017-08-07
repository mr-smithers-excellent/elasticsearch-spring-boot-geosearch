package com.demo.search;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

import com.demo.domain.Starbucks;
import com.github.vanroy.springdata.jest.JestElasticsearchTemplate;
import io.searchbox.client.JestClient;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.ElasticsearchException;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class) @SpringBootTest @FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StarbucksSearchRepositoryTests {

	@Autowired
	private StarbucksSearchRepository starbucksSearch;

	@Autowired
	private JestClient jestClient;

	private JestElasticsearchTemplate elasticsearchTemplate;

	private Page<Starbucks> results;

	// default page request
	private Pageable pageRequest = new PageRequest(0, 10);

	@Before
	public void setUp() {
		this.elasticsearchTemplate = new JestElasticsearchTemplate(jestClient);
	}

	/**
	 * Test search by state
	 */
	@Test
	public void test1FindByState() {
		results = starbucksSearch.findByState("MD", pageRequest);
		assertThat(results).isNotNull();
		assertThat(results.getTotalElements()).isEqualTo(214);
	}

	/**
	 * Test search by city
	 */
	@Test
	public void test2FindByCity() {
		results = starbucksSearch.findByCity("New York", pageRequest);
		assertThat(results).isNotNull();
		assertThat(results.getTotalElements()).isEqualTo(203);
	}

	/**
	 * Test search by location within 20 miles
	 */
	@Test
	public void test3FindByLocationWithin20Miles() {
		results = starbucksSearch.findByLocationNear(new Point(39.09, -94.58), new Distance(20.0d, Metrics.MILES),
				pageRequest);  // Kansas City, MO
		assertThat(results).isNotEmpty();
		assertThat(results.getTotalElements()).isEqualTo(61);
	}

	/**
	 * Test search by location within 5 miles
	 */
	@Test
	public void test4FindByLocationWithin5Miles() {
		results = starbucksSearch
				.findByLocationNear(new Point(39.09, -94.58), new Distance(5.0d, Metrics.MILES), pageRequest);
		assertThat(results).isNotEmpty();
		assertThat(results.getTotalElements()).isEqualTo(5);
	}

	/**
	 * Test for an exception by switching longitude and latitude
	 */
	@Test(expected = ElasticsearchException.class)
	public void test5FindByLocationInvalidPoint() {
		results = starbucksSearch
				.findByLocationNear(new Point(-98.76, 25.12), new Distance(5.0d, Metrics.MILES), pageRequest);
	}

	/**
	 * Test search within 20 miles using locationNear method, expect same results as locationWithin
	 */
	@Test
	public void test6FindByLocationNear() {
		results = starbucksSearch
				.findByLocationWithin(new Point(39.09, -94.58), new Distance(20.0d, Metrics.MILES), pageRequest);
		assertThat(results).isNotEmpty();
		assertThat(results.getTotalElements()).isEqualTo(61);
	}

	/**
	 * Find all Starbucks locations with ownershipType of licensed using SearchQuery
	 */
	@Test
	public void test7FindUsingSearchQuery() {
		SearchQuery query = new NativeSearchQueryBuilder()
				.withQuery(matchQuery("ownershipType", "licensed"))
				.withPageable(pageRequest)
				.build();

		results = starbucksSearch.search(query);

		assertThat(results).isNotNull();
		assertThat(results).isNotEmpty();
		assertThat(results.getTotalElements()).isEqualTo(3441L);
	}

}
