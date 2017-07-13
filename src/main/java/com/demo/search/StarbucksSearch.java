package com.demo.search;

import com.demo.models.Starbucks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "/starbucks")
public interface StarbucksSearch extends ElasticsearchRepository<Starbucks, Long> {

    Page<Starbucks> findByCity(@Param("city") String city, Pageable pageable);

    Page<Starbucks> findByState(@Param("state") String state, Pageable pageable);
}
