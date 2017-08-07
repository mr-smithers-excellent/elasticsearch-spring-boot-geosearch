package com.demo.search;

import com.demo.domain.Starbucks;
import com.github.vanroy.springdata.jest.JestElasticsearchTemplate;
import io.searchbox.client.JestClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Repository;

@Repository
public class StarbucksSearchRepositoryImpl implements StarbucksSearchRepositoryCustom {

	private final JestClient jestClient;
	private final JestElasticsearchTemplate elasticsearchTemplate;

	public StarbucksSearchRepositoryImpl(JestClient jestClient) {
		this.jestClient = jestClient;
		this.elasticsearchTemplate = new JestElasticsearchTemplate(this.jestClient);
	}

	@Override
	public Page<Starbucks> findByLocationWithin(Point point, Distance distance, Pageable pageable) {
		return elasticsearchTemplate.queryForPage(getGeoQuery(point, distance, pageable), Starbucks.class);
	}

	private CriteriaQuery getGeoQuery(Point point, Distance distance, Pageable pageable) {
		return new CriteriaQuery(
				new Criteria("location").within(point, distance),
				pageable
		);
	}

}
