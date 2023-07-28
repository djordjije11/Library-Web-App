package com.djordjije11.libraryappapi.service.book;

import com.djordjije11.libraryappapi.exception.RecordNotFoundException;
import com.djordjije11.libraryappapi.exception.book.BookWithCopiesDeleteException;
import com.djordjije11.libraryappapi.model.*;
import com.djordjije11.libraryappapi.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public abstract class BookServiceTest {
    @Autowired
    protected BookRepository bookRepository;
    @Autowired
    protected PublisherRepository publisherRepository;
    @Autowired
    protected AuthorRepository authorRepository;
    @Autowired
    protected BookCopyRepository bookCopyRepository;
    @Autowired
    protected BuildingRepository buildingRepository;
    protected BookService bookService;

    @Test
    public void create_without_Publisher_and_Authors() {
        var book = new Book("Title", "Description", null, 110);

        Book dbBook = bookService.create(book);

        assertNotNull(dbBook);
        assertEquals(book, dbBook);
    }

    @Test
    public void create_when_Publisher_does_not_exist_throws_RecordNotFoundException() {
        var book = new Book("Title", "Description", null, 110);
        var publisher = new Publisher(1L, "Publisher");
        book.setPublisher(publisher);

        assertThrows(RecordNotFoundException.class, () -> bookService.create(book));
    }

    @Test
    public void create_when_Publisher_exists() {
        var book = new Book("Title", "Description", null, 110);
        var publisher = new Publisher("Publisher");
        var dbPublisher = publisherRepository.save(publisher);
        book.setPublisher(dbPublisher);

        Book dbBook = bookService.create(book);

        assertNotNull(dbBook);
        assertEquals(book, dbBook);
        assertEquals(dbPublisher, dbBook.getPublisher());
    }

    @Test
    public void create_when_Author_does_not_exist_throws_RecordNotFoundException() {
        var book = new Book("Title", "Description", null, 110);
        var author1 = new Author(1L, "Name 1", "Surname 1", "Biography 1 ");
        var author2 = new Author(2L, "Name 2", "Surname 2", "Biography 2 ");
        var authors = new ArrayList<Author>() {{
            add(author1);
            add(author2);
        }};

        authorRepository.save(author1);

        book.setAuthors(authors);

        assertThrows(RecordNotFoundException.class, () -> bookService.create(book));
    }

    @Test
    public void create_when_Authors_exist(){
        var book = new Book("Title", "Description", null, 110);
        var author1 = new Author(1L, "Name 1", "Surname 1", "Biography 1 ");
        var author2 = new Author(2L, "Name 2", "Surname 2", "Biography 2 ");
        var authors = new ArrayList<Author>() {{
            add(author1);
            add(author2);
        }};

        var dbAuthors = authorRepository.saveAll(authors);

        book.setAuthors(dbAuthors);

        Book dbBook = bookService.create(book);

        assertNotNull(dbBook);
        assertEquals(book, dbBook);
        assertEquals(dbAuthors, dbBook.getAuthors());
    }

    @Test
    public void delete_when_copies_do_not_exist(){
        var book = new Book("Title", "Description", null, 110);
        var dbBook = bookRepository.save(book);

        assertTrue(bookRepository.existsById(dbBook.getId()));

        try {
            bookService.delete(dbBook.getId());
        } catch (BookWithCopiesDeleteException e) {
            fail();
        }

        assertFalse(bookRepository.existsById(dbBook.getId()));
    }

    @Test
    public void delete_when_copies_do_exist(){
        var book = new Book("Title", "Description", null, 110);
        var dbBook = bookRepository.save(book);
        var bookCopy = new BookCopy("12346789", BookCopyStatus.LENT, dbBook, null);
        var dbBookCopy = bookCopyRepository.save(bookCopy);

        assertThrows(BookWithCopiesDeleteException.class, () -> bookService.delete(dbBook.getId()));
    }

//
//    @Test
//    void update() {
//    }
//
//    @Test
//    void delete() {
//    }
//
//    @Test
//    void createCopy() {
//    }
//
//    @Test
//    void updateCopy() {
//    }
//
//    @Test
//    void discardCopy() {
//    }
//
//    @Test
//    void getAvailableBookCopiesCount() {
//    }
}