package com.djordjije11.libraryappapi.repository;

import com.djordjije11.libraryappapi.model.BookCopy;
import com.djordjije11.libraryappapi.model.BookCopyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {
    boolean existsByIsbn(String isbn);
    boolean existsByIsbnAndIdIsNot(String isbn, Long id);
    boolean existsByBook_Id(Long bookId);
    Long countByBook_IdAndStatus(Long bookId, BookCopyStatus status);
    List<BookCopy> findAllByBook_IdAndBuilding_Id(Long bookId, Long buildingId);
}
