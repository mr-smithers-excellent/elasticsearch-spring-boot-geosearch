package com.demo.search;

import com.demo.model.Starbucks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class StarbucksSearchServiceImpl implements StarbucksSearchService {

    private final StarbucksSearch starbucksSearch;
    private final StarbucksGeoSearch starbucksGeoSearch;

    public StarbucksSearchServiceImpl(StarbucksSearch starbucksSearch, StarbucksGeoSearch starbucksGeoSearch) {
        this.starbucksSearch = starbucksSearch;
        this.starbucksGeoSearch = starbucksGeoSearch;
    }

    @Override
    public Page<Starbucks> findByCity(String city, Pageable pageable) {
        return starbucksSearch.findByCity(city, pageable);
    }

    @Override
    public Page<Starbucks> findByState(String state, Pageable pageable) {
        return starbucksSearch.findByState(state, pageable);
    }

    @Override
    public Page<Starbucks> findByLocation(double lat, double lon, int distance, Pageable pageable) {
        return starbucksGeoSearch.findByLocation(lat, lon, distance, pageable);
    }

    @Override
    public Page<Starbucks> findByLocation(double lat, double lon, Pageable pageable) {
        return starbucksGeoSearch.findByLocation(lat, lon, pageable);
    }

}
