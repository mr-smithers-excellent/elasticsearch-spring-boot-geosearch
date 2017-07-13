package com.demo;

import com.demo.data.StarbucksRepository;
import com.demo.models.Starbucks;
import com.demo.search.StarbucksSearch;
import com.google.common.collect.Lists;
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

	private static final int DATA_COUNT = 10843;

	private List<Starbucks> data;

	@Test
	public void contextLoads() {
	}

	@Test
	public void test1PopulateDatabase() {
		data = starbucksRepository.findAll();
		assertThat(data).isNotNull();
		assertThat(data.size()).isEqualTo(DATA_COUNT);
	}

	@Test
	public void test2PopulateElasticSearch() {
		starbucksSearch.save(data);
		Iterable<Starbucks> esData = starbucksSearch.findAll();
		assertThat(esData).isNotNull();
		List<Starbucks> esDataList = Lists.newArrayList(esData);
		assertThat(esDataList).isNotNull();
		assertThat(esDataList.size()).isEqualTo(DATA_COUNT);
	}

}
