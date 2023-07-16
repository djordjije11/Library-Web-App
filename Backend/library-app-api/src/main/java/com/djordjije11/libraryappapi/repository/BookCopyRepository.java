package com.djordjije11.libraryappapi.repository;

import com.djordjije11.libraryappapi.model.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {
}
