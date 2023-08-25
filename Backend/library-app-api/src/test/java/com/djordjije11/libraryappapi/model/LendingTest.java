package com.djordjije11.libraryappapi.model;

import com.djordjije11.libraryappapi.exception.ModelInvalidException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LendingTest {
    private Lending lending;

    @BeforeEach
    public void init() {
        lending = new Lending();
    }

    @Test
    public void testSetRowVersion() {
        long rowVersion = 10;
        lending.setRowVersion(rowVersion);
        assertEquals(rowVersion, lending.getRowVersion());
    }

    @Test
    public void testSetId() {
        long id = 1;
        lending.setId(id);
        assertEquals(id, lending.getId());
    }

    @Test
    void testSetLendingDate() {
        LocalDate lendingDate = LocalDate.now();
        lending.setLendingDate(lendingDate);
        assertEquals(lendingDate, lending.getLendingDate());
    }

    @Test
    void testSetLendingDateNull() {
        assertThrows(ModelInvalidException.class, () -> lending.setLendingDate(null));
    }

    @Test
    void testSetReturnDate() {
        LocalDate returnDate = LocalDate.now();
        lending.setReturnDate(returnDate);
        assertEquals(returnDate, lending.getReturnDate());
    }

    @Test
    void testSetReturnDateNull() {
        LocalDate returnDate = null;
        lending.setReturnDate(returnDate);
        assertEquals(returnDate, lending.getReturnDate());
    }

    @Test
    void testSetBookCopy() {
        BookCopy bookCopy = new BookCopy();
        lending.setBookCopy(bookCopy);
        assertEquals(bookCopy, lending.getBookCopy());
    }

    @Test
    void testSetBookCopyNull() {
        assertThrows(ModelInvalidException.class, () -> lending.setBookCopy(null));
    }

    @Test
    void testSetMember() {
        Member member = new Member();
        lending.setMember(member);
        assertEquals(member, lending.getMember());
    }

    @Test
    void testSetMemberNull() {
        assertThrows(ModelInvalidException.class, () -> lending.setMember(null));
    }
}