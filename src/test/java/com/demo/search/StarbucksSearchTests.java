package com.demo.search;

import com.demo.model.Starbucks;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StarbucksSearchTests {

    @Autowired
    private StarbucksSearch starbucksSearch;

    // default page request
    private Pageable pageRequest = new PageRequest(0,10);

    /*
     * Test search by state
     */
    @Test
    public void test1FindByState() {
        Page<Starbucks> results = starbucksSearch.findByState("MD", pageRequest);
        assertThat(results).isNotNull();
        assertThat(results.getTotalElements()).isEqualTo(214);
    }

    /*
     * Test search by city
     */
    @Test
    public void test2FindByCity() {
        Page<Starbucks> results = starbucksSearch.findByCity("New York", pageRequest);
        assertThat(results).isNotNull();
        assertThat(results.getTotalElements()).isEqualTo(203);
    }

    /*
     * Test search by location
     */
    @Test
    public void test3FindByLocation() {
        Page<Starbucks> results = starbucksSearch.findByLocation(39.09, -94.58, pageRequest);  // Kansas City, MO
        assertThat(results).isNotEmpty();
        assertThat(results.getTotalElements()).isEqualTo(61);
    }

    /*
     * Test search by location within specified distance (in miles)
     */
    @Test
    public void test4FindByLocationAndDistance() {
        Page<Starbucks> results = starbucksSearch.findByLocationAndDistance(39.09, -94.58, 5, pageRequest);  // Kansas City, MO
        assertThat(results).isNotEmpty();
        assertThat(results.getTotalElements()).isEqualTo(5);
    }
}
