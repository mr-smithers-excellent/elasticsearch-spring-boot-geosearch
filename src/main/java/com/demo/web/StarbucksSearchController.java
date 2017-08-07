package com.demo.web;

import com.demo.search.StarbucksSearchRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/starbucks-locations/search")
public class StarbucksSearchController {

	private final StarbucksSearchRepository starbucksSearchRepository;

	public StarbucksSearchController(StarbucksSearchRepository starbucksSearchRepository) {
		this.starbucksSearchRepository = starbucksSearchRepository;
	}

	@RequestMapping(value = "/findByLocationWithin", method = RequestMethod.GET)
	public ResponseEntity searchByLocation(@Param("location") Double[] location, @Param("distance") Distance distance, Pageable pageable) {
		Point point = new Point(location[0], location[1]);
		return new ResponseEntity(starbucksSearchRepository.findByLocationWithin(point, distance, pageable), HttpStatus.OK);
	}

}
