package com.djordjije11.libraryappapi.repository;

import com.djordjije11.libraryappapi.helper.string.util.StringExt;
import com.djordjije11.libraryappapi.model.BookCopy;
import com.djordjije11.libraryappapi.model.BookCopyStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookCopyRepository extends JpaRepository<BookCopy, Long>, JpaSpecificationExecutor<BookCopy> {
    boolean existsByIsbn(String isbn);

    boolean existsByIsbnAndIdIsNot(String isbn, Long id);

    boolean existsByBook_Id(Long bookId);

    Long countByBook_IdAndStatus(Long bookId, BookCopyStatus status);

    Page<BookCopy> findAllByBook_Id(Pageable pageable, Long bookId);

    Page<BookCopy> findAllByBook_IdAndIsbnContains(Pageable pageable, Long bookId, String isbn);

    Page<BookCopy> findAllByBook_IdAndStatus(Pageable pageable, Long bookId, BookCopyStatus status);

    Page<BookCopy> findAllByBook_IdAndStatusAndIsbnContains(Pageable pageable, Long bookId, BookCopyStatus status, String isbn);

    Page<BookCopy> findAllByBook_IdAndBuilding_Id(Pageable pageable, Long bookId, Long buildingId);

    Page<BookCopy> findAllByBook_IdAndBuilding_IdAndIsbnContains(Pageable pageable, Long bookId, Long buildingId, String isbn);

    default Page<BookCopy> findAllBookCopies(Pageable pageable, Long bookId, BookCopyStatus status, String filter) {
        if (status == null && StringExt.isNullOrBlank(filter)) {
            return findAllByBook_Id(pageable, bookId);
        }
        if (status == null && StringExt.isNotNullNorBlank(filter)) {
            return findAllByBook_IdAndIsbnContains(pageable, bookId, filter);
        }
        if (status != null && StringExt.isNullOrBlank(filter)) {
            return findAllByBook_IdAndStatus(pageable, bookId, status);
        }

        return findAllByBook_IdAndStatusAndIsbnContains(pageable, bookId, status, filter);
    }

    default Page<BookCopy> findAllBookCopiesInBuilding(Pageable pageable, Long bookId, Long buildingId, String filter) {
        if (StringExt.isNullOrBlank(filter)) {
            return findAllByBook_IdAndBuilding_Id(pageable, bookId, buildingId);
        }

        return findAllByBook_IdAndBuilding_IdAndIsbnContains(pageable, bookId, buildingId, filter);
    }

}
