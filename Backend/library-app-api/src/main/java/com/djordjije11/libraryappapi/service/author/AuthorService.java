package com.djordjije11.libraryappapi.service.author;

import com.djordjije11.libraryappapi.model.Author;
import com.djordjije11.libraryappapi.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Represents the service layer for an Author class.
 */
public interface AuthorService {
    /**
     * Returns the page of authors filtered by search text and selected by options of Pageable instance.
     * The search text should contain author's firstname or lastname.
     * @param search by author's firstname or lastname.
     * @param pageable contains options for skipping and taking database records and sorting them.
     * @return page of authors filtered by search text and selected by options of Pageable instance.
     */
    Page<Author> get(String search, Pageable pageable);

//    List<Book> getAllBooksByAuthor(Long id);
}
