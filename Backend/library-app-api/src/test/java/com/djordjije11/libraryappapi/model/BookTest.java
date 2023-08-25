package com.djordjije11.libraryappapi.model;

import com.djordjije11.libraryappapi.exception.ModelInvalidException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    private Book book;

    @BeforeEach
    public void init() {
        book = new Book();
    }

    @Test
    public void testSetRowVersion() {
        long rowVersion = 10;
        book.setRowVersion(rowVersion);
        assertEquals(rowVersion, book.getRowVersion());
    }

    @Test
    public void testSetId() {
        long id = 1;
        book.setId(id);
        assertEquals(id, book.getId());
    }

    @Test
    void testSetTitleValid() {
        String title = "Stepski vuk";
        book.setTitle(title);
        assertEquals(title, book.getTitle());
    }

    @Test
    void testSetTitleNull() {
        assertThrows(ModelInvalidException.class, () -> book.setTitle(null));
    }

    @Test
    void testSetImageUrlValid() {
        String imageUrl = "https://example.com/book-cover.jpg";
        book.setImageUrl(imageUrl);
        assertEquals(imageUrl, book.getImageUrl());
    }

    @Test
    void testSetImageUrlTooLong() {
        String imageUrl = "https://example.com/" + "a".repeat(2201);
        assertThrows(ModelInvalidException.class, () -> book.setImageUrl(imageUrl));
    }

    @Test
    void testSetPagesNumber() {
        int pagesNumber = 300;
        book.setPagesNumber(pagesNumber);
        assertEquals(pagesNumber, book.getPagesNumber());
    }

    @Test
    void testSetAuthors() {
        List<Author> authors = new ArrayList<>();
        authors.add(new Author(1L));
        authors.add(new Author(2L));
        book.setAuthors(authors);
        assertEquals(authors, book.getAuthors());
    }

    @Test
    void testSetDescription() {
        String description = "A gripping tale of mystery and adventure.";
        book.setDescription(description);
        assertEquals(description, book.getDescription());
    }

    @Test
    void testSetPublisher() {
        Publisher publisher = new Publisher(1L);
        book.setPublisher(publisher);
        assertEquals(publisher, book.getPublisher());
    }

    @Test
    void testSetBookCopies() {
        List<BookCopy> bookCopies = new ArrayList<>();
        bookCopies.add(new BookCopy(1L));
        bookCopies.add(new BookCopy(2L));
        book.setBookCopies(bookCopies);
        assertEquals(bookCopies, book.getBookCopies());
    }
}