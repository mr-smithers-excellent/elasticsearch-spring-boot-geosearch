package com.demo.service;

import com.demo.model.Starbucks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StarbucksService {

    Page<Starbucks> findByCity(String city, Pageable pageable);

    Page<Starbucks> findByState(String state, Pageable pageable);

    Page<Starbucks> findByLocation(double lat, double lon, int distance, Pageable pageable);

    Page<Starbucks> findByLocation(double lat, double lon, Pageable pageable);

}
