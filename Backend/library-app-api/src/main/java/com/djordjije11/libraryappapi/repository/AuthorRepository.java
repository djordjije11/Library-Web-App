package com.djordjije11.libraryappapi.repository;

import com.djordjije11.libraryappapi.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
