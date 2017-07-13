package com.demo;

import com.demo.data.StarbucksRepository;
import com.demo.models.Starbucks;
import com.demo.search.StarbucksSearch;
import org.h2.server.web.WebServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.*;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringBootApplication
@EnableJpaRepositories(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = ElasticsearchRepository.class))
@EnableElasticsearchRepositories(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = ElasticsearchRepository.class))
@Configuration
public class StarbucksSearchApp {

	@Autowired
	private StarbucksRepository starbucksRepository;

	@Autowired
	private StarbucksSearch starbucksSearch;

	/*
	 * Enable to H2 web console in default & local envs
	 */
	@Bean
	@Profile({"default", "local"})
	ServletRegistrationBean h2servletRegistration(){
		ServletRegistrationBean registrationBean = new ServletRegistrationBean( new WebServlet());
		registrationBean.addUrlMappings("/console/*");
		return registrationBean;
	}

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
