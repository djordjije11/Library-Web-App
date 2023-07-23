package com.djordjije11.libraryappapi.service.book;

import com.djordjije11.libraryappapi.exception.book.BookCopyIsbnNotUniqueException;
import com.djordjije11.libraryappapi.exception.book.BookCopyNotInBuildingException;
import com.djordjije11.libraryappapi.exception.book.BookWithCopiesDeleteException;
import com.djordjije11.libraryappapi.model.Book;
import com.djordjije11.libraryappapi.model.BookCopy;
import com.djordjije11.libraryappapi.model.BookCopyStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    Page<Book> get(Pageable pageable, String filter);

    Page<BookCopy> getAllCopies(Pageable pageable, Long bookId, BookCopyStatus status, String filter);
    Page<BookCopy> getAllCopiesInBuilding(Pageable pageable, Long bookId, Long buildingId, String filter);

    Book get(Long id);

    BookCopy getCopy(Long id);

    Book create(Book book);

    Book update(Book book);

    void delete(Long id) throws BookWithCopiesDeleteException;

    BookCopy createCopy(BookCopy bookCopy) throws BookCopyIsbnNotUniqueException;

    BookCopy updateCopy(BookCopy bookCopy) throws BookCopyIsbnNotUniqueException, BookCopyNotInBuildingException;

    void discardCopy(BookCopy bookCopy) throws BookCopyNotInBuildingException;

    Long getAvailableBookCopiesCount(Long bookId);
}
