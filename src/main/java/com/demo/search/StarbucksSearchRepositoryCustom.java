package com.demo.search;

import com.demo.domain.Starbucks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;

public interface StarbucksSearchRepositoryCustom {

	Page<Starbucks> findByLocationWithin(Point point, Distance distance, Pageable pageable);

}
