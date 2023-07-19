package com.djordjije11.libraryappapi.service.author;

import com.djordjije11.libraryappapi.model.Author;
import com.djordjije11.libraryappapi.model.Book;
import java.util.List;

public interface AuthorService {
    List<Author> getAll();
    List<Book> getAllBooksByAuthor(Long id);
}
