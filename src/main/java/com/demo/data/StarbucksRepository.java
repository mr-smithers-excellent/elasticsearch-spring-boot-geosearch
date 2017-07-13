package com.demo.data;

import com.demo.models.Starbucks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StarbucksRepository extends JpaRepository<Starbucks, Long> {
}
