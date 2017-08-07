package com.demo.search;

import com.demo.domain.Starbucks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "/starbucks-locations", collectionResourceRel = "/starbucks-locations")
public interface StarbucksSearchRepository extends ElasticsearchRepository<Starbucks, Long>, StarbucksSearchRepositoryCustom {

  Page<Starbucks> findByCity(@Param("city") String city, Pageable pageable);

  Page<Starbucks> findByState(@Param("state") String state, Pageable pageable);

  Page<Starbucks> findByLocationNear(@Param("location") Point point, @Param("distance") Distance distance, Pageable pageable);

  Page<Starbucks> findAll(Pageable pageable);

}
