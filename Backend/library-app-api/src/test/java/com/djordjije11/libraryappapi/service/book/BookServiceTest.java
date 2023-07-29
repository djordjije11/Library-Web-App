package com.djordjije11.libraryappapi.service.book;

import com.djordjije11.libraryappapi.exception.RecordNotCurrentVersionException;
import com.djordjije11.libraryappapi.exception.RecordNotFoundException;
import com.djordjije11.libraryappapi.exception.book.BookCopyNotInBuildingException;
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
    private AddressRepository addressRepository;
    @Autowired
    private CityRepository cityRepository;
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

        bookService.create(book);
        var maybeDbBook = bookRepository.findById(book.getId());
        if (maybeDbBook.isEmpty()) {
            fail();
        }
        var dbBook = maybeDbBook.get();

        assertNotNull(dbBook);
        assertEquals(book.getId(), dbBook.getId());
        assertEquals(book.getTitle(), dbBook.getTitle());
        assertEquals(book.getDescription(), dbBook.getDescription());
        assertEquals(book.getImageUrl(), dbBook.getImageUrl());
        assertEquals(book.getPagesNumber(), dbBook.getPagesNumber());
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

        bookService.create(book);
        var maybeDbBook = bookRepository.findById(book.getId());
        if (maybeDbBook.isEmpty()) {
            fail();
        }
        var dbBook = maybeDbBook.get();

        assertNotNull(dbBook);
        assertEquals(book.getId(), dbBook.getId());
        assertEquals(book.getTitle(), dbBook.getTitle());
        assertEquals(book.getDescription(), dbBook.getDescription());
        assertEquals(book.getImageUrl(), dbBook.getImageUrl());
        assertEquals(book.getPagesNumber(), dbBook.getPagesNumber());
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
    public void create_when_Authors_exist() {
        var book = new Book("Title", "Description", null, 110);
        var author1 = new Author(1L, "Name 1", "Surname 1", "Biography 1 ");
        var author2 = new Author(2L, "Name 2", "Surname 2", "Biography 2 ");
        var authors = new ArrayList<Author>() {{
            add(author1);
            add(author2);
        }};

        var dbAuthors = authorRepository.saveAll(authors);

        book.setAuthors(dbAuthors);

        bookService.create(book);
        var maybeDbBook = bookRepository.findById(book.getId());
        if (maybeDbBook.isEmpty()) {
            fail();
        }
        var dbBook = maybeDbBook.get();

        assertNotNull(dbBook);
        assertEquals(book.getId(), dbBook.getId());
        assertEquals(book.getTitle(), dbBook.getTitle());
        assertEquals(book.getDescription(), dbBook.getDescription());
        assertEquals(book.getImageUrl(), dbBook.getImageUrl());
        assertEquals(book.getPagesNumber(), dbBook.getPagesNumber());
        assertEquals(dbAuthors, dbBook.getAuthors());
    }

    @Test
    public void delete_when_copies_do_not_exist() {
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
    public void delete_when_copies_do_exist() {
        var book = new Book("Title", "Description", null, 110);
        var dbBook = bookRepository.save(book);
        var bookCopy = new BookCopy("12346789", BookCopyStatus.LENT, dbBook, null);
        bookCopyRepository.save(bookCopy);

        assertThrows(BookWithCopiesDeleteException.class, () -> bookService.delete(dbBook.getId()));
    }

    @Test
    public void update() {
        var book = new Book("Title", "Description", null, 110);
        var dbBook = bookRepository.save(book);
        var updateBook = new Book("Title 2", "Description 2", "imageUrl", 220);
        updateBook.setId(dbBook.getId());

        bookService.update(updateBook);
        var maybeDbUpdatedBook = bookRepository.findById(dbBook.getId());
        if (maybeDbUpdatedBook.isEmpty()) {
            fail();
        }
        var dbUpdatedBook = maybeDbUpdatedBook.get();

        assertNotNull(dbUpdatedBook);
        assertEquals(dbBook.getId(), dbUpdatedBook.getId());
        assertEquals(dbUpdatedBook.getId(), updateBook.getId());
        assertEquals(dbUpdatedBook.getTitle(), updateBook.getTitle());
        assertEquals(dbUpdatedBook.getDescription(), updateBook.getDescription());
        assertEquals(dbUpdatedBook.getImageUrl(), updateBook.getImageUrl());
        assertEquals(dbUpdatedBook.getPagesNumber(), updateBook.getPagesNumber());
    }

    @Test
    public void update_when_book_does_not_exist_throws_RecordNotFoundException() {
        var updateBook = new Book("Title 2", "Description 2", "imageUrl", 220);
        updateBook.setId(1000L);
        assertThrows(RecordNotFoundException.class, () -> bookService.update(updateBook));
    }

    @Test
    public void update_when_RowVersion_is_not_valid_throws_RecordNotCurrentVersionException() {
        var book = new Book("Title", "Description", null, 110);
        var dbBook = bookRepository.save(book);
        var updateBook = new Book("Title 2", null, "imageUrl", 220);
        updateBook.setId(dbBook.getId());
        updateBook.setRowVersion(10L);

        assertThrows(RecordNotCurrentVersionException.class, () -> bookService.update(updateBook));
    }

    @Test
    public void update_when_Publisher_does_not_exist_throws_RecordNotFoundException() {
        var book = new Book("Title", "Description", null, 110);
        var dbBook = bookRepository.save(book);
        var publisher = new Publisher(1L, "Publisher");
        dbBook.setPublisher(publisher);

        assertThrows(RecordNotFoundException.class, () -> bookService.update(dbBook));
    }

    @Test
    public void update_when_Author_does_not_exist_throws_RecordNotFoundException() {
        var book = new Book("Title", "Description", null, 110);
        var dbBook = bookRepository.save(book);
        var author1 = new Author(1L, "Name 1", "Surname 1", "Biography 1 ");
        var author2 = new Author(2L, "Name 2", "Surname 2", "Biography 2 ");
        authorRepository.save(author1);
        var authors = new ArrayList<Author>() {{
            add(author1);
            add(author2);
        }};
        dbBook.setAuthors(authors);

        assertThrows(RecordNotFoundException.class, () -> bookService.update(dbBook));
    }

    @Test
    public void update_when_Publisher_and_Authors_exist() {
        var book = new Book("Title", "Description", null, 110);
        var dbBook = bookRepository.save(book);
        var publisher = new Publisher("Publisher");
        var author1 = new Author(1L, "Name 1", "Surname 1", "Biography 1 ");
        var author2 = new Author(2L, "Name 2", "Surname 2", "Biography 2 ");
        publisherRepository.save(publisher);
        authorRepository.save(author1);
        authorRepository.save(author2);
        var authors = new ArrayList<Author>() {{
            add(author1);
            add(author2);
        }};
        dbBook.setPublisher(publisher);
        dbBook.setAuthors(authors);

        bookService.update(dbBook);
        var maybeDbUpdatedBook = bookRepository.findById(dbBook.getId());
        if (maybeDbUpdatedBook.isEmpty()) {
            fail();
        }
        var dbUpdatedBook = maybeDbUpdatedBook.get();
        assertNotNull(dbUpdatedBook);
        assertEquals(dbBook.getPublisher(), dbUpdatedBook.getPublisher());
        assertEquals(dbBook.getAuthors(), dbUpdatedBook.getAuthors());
    }


    @Test
    public void getAvailableBookCopiesCount() {
        var book = new Book("Title", "Description", null, 110);
        var dbBook = bookRepository.save(book);
        var city1 = new City("City 1", "11000");
        var city2 = new City("City 2", "22000");
        cityRepository.save(city1);
        cityRepository.save(city2);
        var address1 = new Address("StreetName 1", 10, city1);
        var address2 = new Address("StreetName 2", 20, city2);
        addressRepository.save(address1);
        addressRepository.save(address2);
        var building1 = new Building(address1);
        var building2 = new Building(address2);
        buildingRepository.save(building1);
        buildingRepository.save(building2);
        var bookCopies = new ArrayList<BookCopy>() {{
            add(new BookCopy("123456780", BookCopyStatus.AVAILABLE, dbBook, building1));
            add(new BookCopy("123456781", BookCopyStatus.AVAILABLE, dbBook, building1));
            add(new BookCopy("123456782", BookCopyStatus.AVAILABLE, dbBook, building2));
        }};
        bookCopyRepository.saveAll(bookCopies);

        Long availableBookCopiesCount = bookService.getAvailableBookCopiesCount(dbBook.getId());

        assertEquals(bookCopies.size(), availableBookCopiesCount);
    }

    @Test
    public void discardCopy() {
        var book = new Book("Title", "Description", null, 110);
        var dbBook = bookRepository.save(book);
        var building = new Building(new Address("StreetName 1", 10, new City("City 1", "11000")));
        buildingRepository.save(building);
        var bookCopy = new BookCopy("123456780", BookCopyStatus.AVAILABLE, dbBook, building);
        bookCopyRepository.save(bookCopy);

        try {
            bookService.discardCopy(bookCopy);
        } catch (BookCopyNotInBuildingException e) {
            fail();
        }

        var maybeDbBookCopy = bookCopyRepository.findById(bookCopy.getId());
        if (maybeDbBookCopy.isEmpty()) {
            fail();
        }
        var dbBookCopy = maybeDbBookCopy.get();

        assertEquals(BookCopyStatus.LOST, dbBookCopy.getStatus());
    }

    @Test
    public void discardCopy_when_copy_does_not_exist(){
        fail();
    }

//    @Test
//    void createCopy() {
//    }
//
//    @Test
//    void updateCopy() {
//    }
}