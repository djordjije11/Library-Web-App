package com.djordjije11.libraryappapi.service.author;

import com.djordjije11.libraryappapi.model.Author;
import com.djordjije11.libraryappapi.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AuthorService {
    Page<Author> get(String search, Pageable pageable);

    List<Book> getAllBooksByAuthor(Long id);
}
