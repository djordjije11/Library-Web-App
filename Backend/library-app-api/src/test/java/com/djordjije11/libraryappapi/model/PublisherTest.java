package com.djordjije11.libraryappapi.model;

import com.djordjije11.libraryappapi.exception.ModelInvalidException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PublisherTest {
    private Publisher publisher;

    @BeforeEach
    public void init() {
        publisher = new Publisher();
    }

    @Test
    public void testSetRowVersion() {
        long rowVersion = 10;
        publisher.setRowVersion(rowVersion);
        assertEquals(rowVersion, publisher.getRowVersion());
    }

    @Test
    public void testSetId() {
        long id = 1;
        publisher.setId(id);
        assertEquals(id, publisher.getId());
    }

    @Test
    void testSetName() {
        String name = "ABC Publishers";
        publisher.setName(name);
        assertEquals(name, publisher.getName());
    }

    @Test
    void testSetNameNull() {
        assertThrows(ModelInvalidException.class, () -> publisher.setName(null));
    }

    @Test
    void testSetNameTooLong() {
        String name = "a".repeat(61);
        assertThrows(ModelInvalidException.class, () -> publisher.setName(name));
    }
}