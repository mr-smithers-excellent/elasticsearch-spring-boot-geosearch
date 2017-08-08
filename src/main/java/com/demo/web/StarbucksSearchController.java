package com.demo.web;

import com.demo.domain.Starbucks;
import com.demo.search.StarbucksSearchRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.rest.webmvc.RepositorySearchesResource;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkBuilder;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/starbucks-locations/search")
public class StarbucksSearchController implements ResourceProcessor<RepositorySearchesResource>{

	private final StarbucksSearchRepository starbucksSearchRepository;
	private final EntityLinks entityLinks;

	public StarbucksSearchController(StarbucksSearchRepository starbucksSearchRepository, EntityLinks entityLinks) {
		this.starbucksSearchRepository = starbucksSearchRepository;
		this.entityLinks = entityLinks;
	}

	@RequestMapping(value = "/findByLocationWithin", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity searchByLocation(@RequestParam("location") Double[] location, @RequestParam("distance") Distance distance, Pageable pageable) {
		Point point = new Point(location[0], location[1]);
		return new ResponseEntity(starbucksSearchRepository.findByLocationWithin(point, distance, pageable), HttpStatus.OK);
	}

	@Override
	public RepositorySearchesResource process(RepositorySearchesResource resource) {
		LinkBuilder linkBuilder = entityLinks.linkFor(Starbucks.class, "starbucks-locations");
		resource.add(new Link(linkBuilder.toString() + "/search/findByLocationWithin{?location,distance,page,size,sort}", "findByLocationWithin"));
		return resource;
	}

}
