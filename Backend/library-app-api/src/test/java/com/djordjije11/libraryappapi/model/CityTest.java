package com.djordjije11.libraryappapi.model;

import com.djordjije11.libraryappapi.exception.ModelInvalidException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CityTest {
    private City city;

    @BeforeEach
    public void init() {
        city = new City();
    }

    @Test
    public void testSetRowVersion() {
        long rowVersion = 10;
        city.setRowVersion(rowVersion);
        assertEquals(rowVersion, city.getRowVersion());
    }

    @Test
    public void testSetId() {
        long id = 1;
        city.setId(id);
        assertEquals(id, city.getId());
    }

    @Test
    void testSetName() {
        String name = "New York";
        city.setName(name);
        assertEquals(name, city.getName());
    }

    @Test
    void testSetNameNull() {
        assertThrows(ModelInvalidException.class, () -> city.setName(null));
    }

    @Test
    void testSetNameTooLong() {
        String name = "This is a very long city name that exceeds the limit of 100 characters and should cause an exception.";
        assertThrows(ModelInvalidException.class, () -> city.setName(name));
    }

    @Test
    void testSetZipcode() {
        String zipcode = "12345";
        city.setZipcode(zipcode);
        assertEquals(zipcode, city.getZipcode());
    }

    @Test
    void testSetZipcodeNull() {
        assertThrows(ModelInvalidException.class, () -> city.setZipcode(null));
    }
}