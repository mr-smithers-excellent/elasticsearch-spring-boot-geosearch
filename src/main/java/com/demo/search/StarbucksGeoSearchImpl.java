package com.demo.search;

import com.demo.model.Starbucks;
import com.github.vanroy.springdata.jest.JestElasticsearchTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Service;

@Service
public class StarbucksGeoSearchImpl implements StarbucksGeoSearch {

    @Autowired
    private final JestElasticsearchTemplate elasticsearchTemplate;

    private static int DEFAULT_DISTANCE = 20;
    private static String DISTANCE_SUFFIX = "mi";

    public StarbucksGeoSearchImpl(JestElasticsearchTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    /*
     *  Find by location within default distance (20 miles)
     */
    @Override
    public Page<Starbucks> findByLocation(double lat, double lon, Pageable pageable) {
        String distance = String.valueOf(DEFAULT_DISTANCE) + DISTANCE_SUFFIX;
        return elasticsearchTemplate.queryForPage(getGeoQuery(lat, lon, distance, pageable), Starbucks.class);
    }

    /*
     *  Find by location within specified distance (in miles)
     */
    @Override
    public Page<Starbucks> findByLocation(double lat, double lon, int distanceInMiles, Pageable pageable) {
        String distance = String.valueOf(distanceInMiles) + DISTANCE_SUFFIX;
        return elasticsearchTemplate.queryForPage(getGeoQuery(lat, lon, distance, pageable), Starbucks.class);
    }

    private CriteriaQuery getGeoQuery(double lat, double lon, String distance, Pageable pageable) {
        return new CriteriaQuery(
                new Criteria("location").within(new GeoPoint(lat, lon), distance),
                pageable
        );
    }

}
