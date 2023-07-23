package com.djordjije11.libraryappapi.repository;

import com.djordjije11.libraryappapi.model.BookCopy;
import com.djordjije11.libraryappapi.model.BookCopyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookCopyRepository extends JpaRepository<BookCopy, Long>, JpaSpecificationExecutor<BookCopy> {
    boolean existsByIsbn(String isbn);

    boolean existsByIsbnAndIdIsNot(String isbn, Long id);

    boolean existsByBook_Id(Long bookId);

    Long countByBook_IdAndStatus(Long bookId, BookCopyStatus status);
}
