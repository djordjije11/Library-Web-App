package com.djordjije11.libraryappapi.service.book;

import com.djordjije11.libraryappapi.exception.book.BookCopyIsbnNotUniqueException;
import com.djordjije11.libraryappapi.exception.book.BookCopyNotInBuildingException;
import com.djordjije11.libraryappapi.exception.book.BookWithCopiesDeleteException;
import com.djordjije11.libraryappapi.model.Book;
import com.djordjije11.libraryappapi.model.BookCopy;
import java.util.List;

public interface BookService {
    List<Book> getAll();
    List<BookCopy> getAllCopies(Long bookId, Long buildingId);
    Book get(Long id);
    Book create(Book book);
    Book update(Book book);
    void delete(Long id) throws BookWithCopiesDeleteException;
    BookCopy createCopy(BookCopy bookCopy) throws BookCopyIsbnNotUniqueException;
    BookCopy updateCopy(BookCopy bookCopy) throws BookCopyIsbnNotUniqueException, BookCopyNotInBuildingException;
    void discardCopy(BookCopy bookCopy) throws BookCopyNotInBuildingException;
    Long getAvailableBookCopiesCount(Long bookId);
}
