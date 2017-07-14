package com.demo;

import com.demo.data.StarbucksRepository;
import com.demo.model.Starbucks;
import com.demo.search.StarbucksSearch;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StarbucksSearchAppTests {

	@Autowired
	private StarbucksRepository starbucksRepository;

	@Autowired
	private StarbucksSearch starbucksSearch;

	private static final int DATA_COUNT = 8972;

	private List<Starbucks> data;

	@Before
	public void setUp() {

		// get all rows from DB
		data = starbucksRepository.findAll();
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void test1PopulateDatabase() {

		// test count matches SQL insert count (view resources/import.sql)
		assertThat(data).isNotNull();
		assertThat(data.size()).isEqualTo(DATA_COUNT);
	}

	@Test
	public void test2PopulateElasticSearch() {

		// retrieve data from ES cluster
		Iterable<Starbucks> esData = starbucksSearch.findAll();

		// test es data matches relational data count
		assertThat(esData).isNotNull();
		List<Starbucks> esDataList = Lists.newArrayList(esData);
		assertThat(esDataList).isNotNull();
		assertThat(esDataList.size()).isEqualTo(DATA_COUNT);
	}

	@Test
	public void test3MultipleSyncsWithNoDuplicates() {

		// sync with ES multiple times
		data = starbucksRepository.findAll();
		starbucksSearch.save(data);
		starbucksSearch.save(data);

		// test that duplicates were not allowed in ES cluster
		long count = starbucksSearch.count();
		assertThat(count).isEqualTo(DATA_COUNT);
	}

}
