package com.demo.search;

import com.demo.model.Starbucks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StarbucksGeoSearch {

    Page<Starbucks> findByLocation(double lat, double lon, Pageable pageable);

    Page<Starbucks> findByLocation(double lat, double lon, int distanceInMiles, Pageable pageable);

}
