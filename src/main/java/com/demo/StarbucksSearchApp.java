package com.demo;

import com.demo.data.StarbucksRepository;
import com.demo.models.Starbucks;
import com.demo.search.StarbucksSearch;
import org.h2.server.web.WebServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = "com.demo.search")
@Configuration
public class StarbucksSearchApp {

	@Autowired
	private StarbucksRepository starbucksRepository;

	@Autowired
	private StarbucksSearch starbucksSearch;

	@Bean
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
