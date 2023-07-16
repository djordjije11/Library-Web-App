package com.djordjije11.libraryappapi.repository;

import com.djordjije11.libraryappapi.model.Building;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingRepository extends JpaRepository<Building, Long> {
}
