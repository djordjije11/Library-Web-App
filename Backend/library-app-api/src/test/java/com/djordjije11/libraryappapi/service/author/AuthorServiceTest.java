package com.djordjije11.libraryappapi.service.author;

import com.djordjije11.libraryappapi.repository.AuthorRepository;
import com.djordjije11.libraryappapi.specification.author.AuthorSpecification;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

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
//        var dbAuthors = authorRepository.findAll(AuthorSpecification.bySearch(search), pageable);
    }
}