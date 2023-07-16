package com.djordjije11.libraryappapi.repository;

import com.djordjije11.libraryappapi.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
}
