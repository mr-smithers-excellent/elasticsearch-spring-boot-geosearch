package com.demo.search;

import com.demo.model.Starbucks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface StarbucksSearch extends ElasticsearchRepository<Starbucks, Long> {

    Page<Starbucks> findByCity(String city, Pageable pageable);

    Page<Starbucks> findByState(String state, Pageable pageable);

}
