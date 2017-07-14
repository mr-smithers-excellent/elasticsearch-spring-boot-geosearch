package com.demo.search;

import com.demo.model.Starbucks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "/starbucks")
public interface StarbucksSearch extends ElasticsearchRepository<Starbucks, Long> {

    Page<Starbucks> findByCity(@Param("city") String city, Pageable pageable);

    Page<Starbucks> findByState(@Param("state") String state, Pageable pageable);

    @Query("{\"query\": {\"filtered\": {\"filter\": {\"geo_distance\": {\"distance\": \"20mi\", \"location\": {\"lat\": \"?0\", \"lon\": \"?1\"}}}}}}")
    Page<Starbucks> findByLocation(@Param("lat") double lat, @Param("lon") double lon, Pageable pageable);

    @Query("{\"query\": {\"filtered\": {\"filter\": {\"geo_distance\": {\"distance\": \"?2mi\", \"location\": {\"lat\": \"?0\", \"lon\": \"?1\"}}}}}}")
    Page<Starbucks> findByLocationAndDistance(@Param("lat") double lat, @Param("lon") double lon, @Param("dist") int distanceInMiles, Pageable pageable);

}
