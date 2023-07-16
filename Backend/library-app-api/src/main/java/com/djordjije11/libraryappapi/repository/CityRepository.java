package com.djordjije11.libraryappapi.repository;

import com.djordjije11.libraryappapi.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
}
