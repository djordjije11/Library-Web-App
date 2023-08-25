package com.djordjije11.libraryappapi.model;

import com.djordjije11.libraryappapi.exception.ModelInvalidException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookCopyTest {
    private BookCopy bookCopy;

    @BeforeEach
    public void init() {
        bookCopy = new BookCopy();
    }

    @Test
    public void testSetRowVersion() {
        long rowVersion = 10;
        bookCopy.setRowVersion(rowVersion);
        assertEquals(rowVersion, bookCopy.getRowVersion());
    }

    @Test
    public void testSetId() {
        long id = 1;
        bookCopy.setId(id);
        assertEquals(id, bookCopy.getId());
    }

    @Test
    void testSetIsbnValid() {
        String isbn = "978-13-4685-959-1";
        bookCopy.setIsbn(isbn);
        assertEquals(isbn, bookCopy.getIsbn());
    }

    @Test
    void testSetIsbnNull() {
        assertThrows(ModelInvalidException.class, () -> bookCopy.setIsbn(null));
    }

    @Test
    void testSetIsbnTooLong() {
        BookCopy bookCopy = new BookCopy();
        String invalidIsbn = "978-0-13-468599-1XZFS";
        assertThrows(ModelInvalidException.class, () -> bookCopy.setIsbn(invalidIsbn));
    }

    @Test
    void testSetIsbnInvalidFormat() {
        String invalidIsbn = "123-12-14-12-3-1";
        assertThrows(ModelInvalidException.class, () -> bookCopy.setIsbn(invalidIsbn));
    }

    @Test
    void testSetStatus() {
        BookCopyStatus status = BookCopyStatus.AVAILABLE;
        bookCopy.setStatus(status);
        assertEquals(status, bookCopy.getStatus());
    }

    @Test
    void testSetStatusNull() {
        assertThrows(ModelInvalidException.class, () -> bookCopy.setStatus(null));
    }

    @Test
    void testSetBook() {
        Book book = new Book(1L);
        bookCopy.setBook(book);
        assertEquals(book, bookCopy.getBook());
    }

    @Test
    void testSetBuilding() {
        Building building = new Building(1L);
        bookCopy.setBuilding(building);
        assertEquals(building, bookCopy.getBuilding());
    }

    @Test
    void testSetLendings() {
        List<Lending> lendings = new ArrayList<>();
        bookCopy.setLendings(lendings);
        assertEquals(lendings, bookCopy.getLendings());
    }
}