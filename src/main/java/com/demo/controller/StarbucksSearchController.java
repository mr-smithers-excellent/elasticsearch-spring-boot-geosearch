package com.demo.controller;

import com.demo.search.StarbucksSearchService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/starbucks/search", produces = MediaType.APPLICATION_JSON_VALUE)
public class StarbucksSearchController {

    private final StarbucksSearchService starbucksSearchService;

    public StarbucksSearchController(StarbucksSearchService starbucksSearchService) {
        this.starbucksSearchService = starbucksSearchService;
    }

    @RequestMapping(value = "/searchByCity", method = RequestMethod.GET)
    public ResponseEntity searchByCity(@RequestParam("city") String city, Pageable pageable) {
        return new ResponseEntity(starbucksSearchService.findByCity(city, pageable), HttpStatus.OK);
    }

    @RequestMapping(value = "/searchByState", method = RequestMethod.GET)
    public ResponseEntity searchByState(@RequestParam("state") String city, Pageable pageable) {
        return new ResponseEntity(starbucksSearchService.findByState(city, pageable), HttpStatus.OK);
    }

    @RequestMapping(value = "/searchByLocation", method = RequestMethod.GET)
    public ResponseEntity searchByLocation(@RequestParam("lat") double lat, @RequestParam("lon") double lon, Integer distance, Pageable pageable) {
        if (distance != null) {
            return new ResponseEntity(starbucksSearchService.findByLocation(lat, lon, distance, pageable), HttpStatus.OK);
        }

        return new ResponseEntity(starbucksSearchService.findByLocation(lat, lon, pageable), HttpStatus.OK);
    }

}
