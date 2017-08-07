package com.demo;

import com.demo.domain.Starbucks;
import com.demo.domain.StarbucksRepository;
import com.demo.search.StarbucksSearchRepository;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = {ElasticsearchAutoConfiguration.class, ElasticsearchDataAutoConfiguration.class})
@EnableJpaRepositories(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = ElasticsearchCrudRepository.class))
@EnableElasticsearchRepositories(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = ElasticsearchCrudRepository.class))
public class StarbucksSearchApp {

	@Autowired
	private StarbucksRepository starbucksRepository;

	@Autowired
	private StarbucksSearchRepository starbucksSearchRepository;

	/*
	 * Retrieve data from database and feed into Elasticsearch cluster
	 */
	@PostConstruct
	public void syncElasticsearch() {
		starbucksRepository.flush();
		List<Starbucks> data = starbucksRepository.findAll();

		// delete Elasticsearch documents if already populated
		long documentCount = starbucksSearchRepository.count();
		if (documentCount > 0) {
			starbucksSearchRepository.deleteAll();
		}

		starbucksSearchRepository.save(data);
	}

	public static void main(String[] args) {
		SpringApplication.run(StarbucksSearchApp.class, args);
	}

}
