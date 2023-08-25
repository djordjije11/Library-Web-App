package com.djordjije11.libraryappapi.model;

import com.djordjije11.libraryappapi.exception.ModelInvalidException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AuthorTest {
    private Author author;

    @BeforeEach
    public void init() {
        author = new Author();
    }

    @Test
    public void testSetRowVersion() {
        long rowVersion = 10;
        author.setRowVersion(rowVersion);
        assertEquals(rowVersion, author.getRowVersion());
    }

    @Test
    public void testSetId() {
        long id = 1;
        author.setId(id);
        assertEquals(id, author.getId());
    }

    @Test
    void testSetFirstnameValid() {
        String firstname = "John";
        author.setFirstname(firstname);
        assertEquals(firstname, author.getFirstname());
    }

    @Test
    void testSetFirstnameTooLong() {
        String firstname = "frstnm".repeat(60);
        assertThrows(ModelInvalidException.class, () -> author.setFirstname(firstname));
    }

    @Test
    void testSetLastnameValid() {
        String lastname = "Doe";
        author.setLastname(lastname);
        assertEquals(lastname, author.getLastname());
    }

    @Test
    void testSetLastnameNull() {
        assertThrows(ModelInvalidException.class, () -> author.setLastname(null));
    }

    @Test
    void testSetLastnameTooLong() {
        String lastname = "lstnm".repeat(60);
        assertThrows(ModelInvalidException.class, () -> author.setLastname(lastname));
    }

    @Test
    void testSetBiography() {
        String biography = "A renowned author with a captivating storytelling style.";
        author.setBiography(biography);
        assertEquals(biography, author.getBiography());
    }

    @Test
    void testSetBooks() {
        Author author = new Author();
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L));
        books.add(new Book(2L));
        author.setBooks(books);
        assertEquals(books, author.getBooks());
    }
}
