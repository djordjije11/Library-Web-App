package com.djordjije11.libraryappapi.service.author;

import com.djordjije11.libraryappapi.model.Author;
import com.djordjije11.libraryappapi.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public abstract class AuthorServiceTest {
    @Autowired
    protected AuthorRepository authorRepository;
    protected AuthorService authorService;

    @Test
    public void get_search_by_biography() {
        var authors = new ArrayList<Author>() {{
            add(new Author("A-Firstname", "Lastname-3", "Biography 1"));
            add(new Author("Firstname", "Lastname-2", "Biography 2"));
            add(new Author("C-Firstname", "Lastname-3", "Biography 3"));
            add(new Author("Firstname", "Lastname-4", "Biography 4"));
            add(new Author("B-Firstname", "Lastname-3", "Biography 5"));
        }};
        authorRepository.saveAll(authors);
        Page<Author> authorPage = authorService.get("3", PageRequest.of(0, 2, Sort.by(Sort.Direction.ASC, "firstname")));
        var dbAuthors = authorPage.toList();

        assertNotNull(authorPage);
        assertNotNull(dbAuthors);
        assertEquals(2, dbAuthors.size());
        assertEquals("Biography 1", dbAuthors.get(0).getBiography());
        assertEquals("Biography 5", dbAuthors.get(1).getBiography());
    }

    @Test
    public void get_search_by_biography_when_there_is_no_searched_Author() {
        var authors = new ArrayList<Author>() {{
            add(new Author("A-Firstname", "Lastname-3", "Biography 1"));
            add(new Author("Firstname", "Lastname-2", "Biography 2"));
            add(new Author("C-Firstname", "Lastname-3", "Biography 3"));
            add(new Author("Firstname", "Lastname-4", "Biography 4"));
            add(new Author("B-Firstname", "Lastname-3", "Biography 5"));
        }};
        authorRepository.saveAll(authors);
        Page<Author> authorPage = authorService.get("Biography 999", PageRequest.of(0, 2, Sort.unsorted()));
        var dbAuthors = authorPage.toList();

        assertNotNull(authorPage);
        assertNotNull(dbAuthors);
        assertEquals(0, dbAuthors.size());
    }

    @Test
    public void get_search_by_firstname() {
        var authors = new ArrayList<Author>() {{
            add(new Author("A-Firstname", "Lastname-3", "Biography 1"));
            add(new Author("Firstname", "Lastname-2", "Biography 2"));
            add(new Author("C-Firstname-2", "Lastname-3", "Biography 3"));
            add(new Author("C-Firstname-1", "Lastname-4", "Biography 4"));
            add(new Author("B-Firstname", "Lastname-3", "Biography 5"));
        }};
        authorRepository.saveAll(authors);
        Page<Author> authorPage = authorService.get("C-First", PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "lastname")));
        var dbAuthors = authorPage.toList();

        assertNotNull(authorPage);
        assertNotNull(dbAuthors);
        assertEquals(2, dbAuthors.size());
        assertEquals("Lastname-4", dbAuthors.get(0).getLastname());
        assertEquals("Lastname-3", dbAuthors.get(1).getLastname());
    }

    @Test
    public void get_when_there_is_no_Authors_in_the_database() {
        Page<Author> authorPage = authorService.get("", PageRequest.of(0, 10, Sort.unsorted()));
        var dbAuthors = authorPage.toList();

        assertNotNull(authorPage);
        assertNotNull(dbAuthors);
        assertEquals(0, dbAuthors.size());
    }
}