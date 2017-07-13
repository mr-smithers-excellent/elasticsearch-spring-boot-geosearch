package com.demo.search;

import com.demo.models.Starbucks;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StarbucksSearch extends ElasticsearchCrudRepository<Starbucks, Long> {
}
