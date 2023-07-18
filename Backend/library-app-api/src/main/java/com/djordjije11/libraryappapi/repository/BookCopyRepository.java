package com.djordjije11.libraryappapi.repository;

import com.djordjije11.libraryappapi.model.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {
    boolean existsByIsbn(String isbn);
    boolean existsByIsbnAndIdIsNot(String isbn, Long id);
    List<BookCopy> findAllByBook_Id(Long bookId);
}
