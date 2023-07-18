package com.djordjije11.libraryappapi.service.book;

import com.djordjije11.libraryappapi.exception.book.BookCopyIsbnNotUniqueException;
import com.djordjije11.libraryappapi.model.Book;
import com.djordjije11.libraryappapi.model.BookCopy;
import java.util.List;

public interface BookService {
    List<Book> getAll();
    List<BookCopy> getAllCopies(Long bookId);
    BookCopy createCopy(BookCopy bookCopy) throws BookCopyIsbnNotUniqueException;
    BookCopy updateCopy(BookCopy bookCopy) throws BookCopyIsbnNotUniqueException;
    void discardCopy(Long id);
}
