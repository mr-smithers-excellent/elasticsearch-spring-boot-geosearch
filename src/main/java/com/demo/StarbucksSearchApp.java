package com.demo;

import com.demo.data.StarbucksRepository;
import com.demo.model.Starbucks;
import com.demo.search.StarbucksSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringBootApplication(exclude = {ElasticsearchAutoConfiguration.class, ElasticsearchDataAutoConfiguration.class})
@EnableJpaRepositories(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = ElasticsearchRepository.class))
@EnableElasticsearchRepositories(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = ElasticsearchRepository.class))
public class StarbucksSearchApp {

	@Autowired
	private StarbucksRepository starbucksRepository;

	@Autowired
	private StarbucksSearch starbucksSearch;

	/*
	 * Retrieve data from database and feed into Elasticsearch cluster
	 */
	@PostConstruct
	public void syncElasticsearch() {
		List<Starbucks> data = starbucksRepository.findAll();
		starbucksSearch.save(data);
	}

	public static void main(String[] args) {
		SpringApplication.run(StarbucksSearchApp.class, args);
	}

}
