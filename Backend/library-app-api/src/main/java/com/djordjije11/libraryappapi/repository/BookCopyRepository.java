package com.djordjije11.libraryappapi.repository;

import com.djordjije11.libraryappapi.helper.string.util.StringExt;
import com.djordjije11.libraryappapi.model.BookCopy;
import com.djordjije11.libraryappapi.model.BookCopyStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {
    boolean existsByIsbn(String isbn);

    boolean existsByIsbnAndIdIsNot(String isbn, Long id);

    boolean existsByBook_Id(Long bookId);

    Long countByBook_IdAndStatus(Long bookId, BookCopyStatus status);

    Page<BookCopy> findAllByBook_Id(Pageable pageable, Long bookId);

    Page<BookCopy> findAllByBook_IdAndIsbnContainsOrBook_IdAndBook_TitleContainsIgnoreCase(Pageable pageable, Long bookId1, String isbn, Long bookId2, String title);

    Page<BookCopy> findAllByBook_IdAndStatus(Pageable pageable, Long bookId, BookCopyStatus status);

    Page<BookCopy> findAllByBook_IdAndStatusAndIsbnContainsOrBook_IdAndStatusAndBook_TitleContainsIgnoreCase(Pageable pageable, Long bookId1, BookCopyStatus status1, String isbn, Long bookId2, BookCopyStatus status2, String title);

    Page<BookCopy> findAllByBook_IdAndBuilding_Id(Pageable pageable, Long bookId, Long buildingId);

    Page<BookCopy> findAllByBook_IdAndBuilding_IdAndIsbnContainsOrBook_IdAndBuilding_IdAndBook_TitleContainsIgnoreCase(Pageable pageable, Long bookId1, Long buildingId1, String isbn, Long bookId2, Long buildingId2, String title);

    default Page<BookCopy> findAllBookCopies(Pageable pageable, Long bookId, BookCopyStatus status, String filter) {
        if (status == null && StringExt.isNullOrBlank(filter)) {
            return findAllByBook_Id(pageable, bookId);
        }
        if (status == null && StringExt.isNotNullNorBlank(filter)) {
            return findAllByBook_IdAndIsbnContainsOrBook_IdAndBook_TitleContainsIgnoreCase(pageable, bookId, filter, bookId, filter);
        }
        if (status != null && StringExt.isNullOrBlank(filter)) {
            return findAllByBook_IdAndStatus(pageable, bookId, status);
        }

        return findAllByBook_IdAndStatusAndIsbnContainsOrBook_IdAndStatusAndBook_TitleContainsIgnoreCase(pageable, bookId, status, filter, bookId, status, filter);
    }

    default Page<BookCopy> findAllBookCopiesInBuilding(Pageable pageable, Long bookId, Long buildingId, String filter) {
        if (StringExt.isNullOrBlank(filter)) {
            return findAllByBook_IdAndBuilding_Id(pageable, bookId, buildingId);
        }

        return findAllByBook_IdAndBuilding_IdAndIsbnContainsOrBook_IdAndBuilding_IdAndBook_TitleContainsIgnoreCase(pageable, bookId, buildingId, filter, bookId, buildingId, filter);
    }

}
