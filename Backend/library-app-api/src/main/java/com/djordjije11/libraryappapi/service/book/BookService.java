package com.djordjije11.libraryappapi.service.book;

import com.djordjije11.libraryappapi.exception.book.BookCopyIsbnNotUniqueException;
import com.djordjije11.libraryappapi.exception.book.BookCopyNotInBuildingException;
import com.djordjije11.libraryappapi.exception.book.BookWithCopiesDeleteException;
import com.djordjije11.libraryappapi.model.Book;
import com.djordjije11.libraryappapi.model.BookCopy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface BookService {
    Page<Book> get(Specification<Book> specification, Pageable pageable);

    Page<BookCopy> getCopies(Specification<BookCopy> specification, Pageable pageable);

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
