package com.djordjije11.libraryappapi.repository;

import com.djordjije11.libraryappapi.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findAllByTitleContainsIgnoreCase(Pageable pageable, String title);
}
