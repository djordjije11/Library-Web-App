package com.djordjije11.libraryappapi.repository.city;

import com.djordjije11.libraryappapi.model.City;
import com.djordjije11.libraryappapi.repository.ReadonlyRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityReadonlyRepository extends ReadonlyRepository<City, Long> {
}
