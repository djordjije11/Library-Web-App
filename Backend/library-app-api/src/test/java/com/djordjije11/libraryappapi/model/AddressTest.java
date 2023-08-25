package com.djordjije11.libraryappapi.model;

import com.djordjije11.libraryappapi.exception.ModelInvalidException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {
    private Address address;

    @BeforeEach
    public void init() {
        address = new Address();
    }

    @Test
    public void testSetRowVersion() {
        long rowVersion = 10;
        address.setRowVersion(rowVersion);
        assertEquals(rowVersion, address.getRowVersion());
    }

    @Test
    public void testSetId() {
        long id = 1;
        address.setId(id);
        assertEquals(id, address.getId());
    }

    @Test
    void testSetStreetNameValid() {
        String streetName = "Sample Street";
        address.setStreetName(streetName);
        assertEquals(streetName, address.getStreetName());
    }

    @Test
    void testSetStreetNameNull() {
        assertThrows(ModelInvalidException.class, () -> address.setStreetName(null));
    }

    @Test
    void testSetStreetNameTooLong() {
        String streetName = "This is a very long street name that exceeds the maximum length allowed.";
        assertThrows(ModelInvalidException.class, () -> address.setStreetName(streetName));
    }

    @Test
    void testSetStreetNumber() {
        int streetNumber = 123;
        address.setStreetNumber(streetNumber);
        assertEquals(streetNumber, address.getStreetNumber());
    }

    @Test
    void testSetCity() {
        City city = new City("Belgrade", "11000");
        address.setCity(city);
        assertEquals(city, address.getCity());
    }

}