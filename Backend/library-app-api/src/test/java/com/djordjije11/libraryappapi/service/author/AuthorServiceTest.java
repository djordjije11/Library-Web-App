package com.djordjije11.libraryappapi.service.author;

import com.djordjije11.libraryappapi.model.Author;
import com.djordjije11.libraryappapi.repository.AuthorRepository;
import com.djordjije11.libraryappapi.specification.author.AuthorSpecification;
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
    public void get(){
        var authors = new ArrayList<Author>(){{
            add(new Author("A Firstname", "Lastname 3", "Biography 1"));
            add(new Author("Firstname", "Lastname 2", "Biography 2"));
            add(new Author("C Firstname", "Lastname 3", "Biography 3"));
            add(new Author("Firstname", "Lastname 4", "Biography 4"));
            add(new Author("B Firstname", "Lastname 3", "Biography 5"));
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
}