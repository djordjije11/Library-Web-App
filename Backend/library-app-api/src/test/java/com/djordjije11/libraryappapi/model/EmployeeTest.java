package com.djordjije11.libraryappapi.model;

import com.djordjije11.libraryappapi.exception.ModelInvalidException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {
    private Employee employee;

    @BeforeEach
    public void init() {
        employee = new Employee();
    }

    @Test
    public void testSetRowVersion() {
        long rowVersion = 10;
        employee.setRowVersion(rowVersion);
        assertEquals(rowVersion, employee.getRowVersion());
    }

    @Test
    public void testSetId() {
        long id = 1;
        employee.setId(id);
        assertEquals(id, employee.getId());
    }

    @Test
    void testSetIdCardNumber() {
        String idCardNumber = "1234567890";
        employee.setIdCardNumber(idCardNumber);
        assertEquals(idCardNumber, employee.getIdCardNumber());
    }

    @Test
    void testSetIdCardNumberNull() {
        assertThrows(ModelInvalidException.class, () -> employee.setIdCardNumber(null));
    }

    @Test
    void testSetIdCardNumberTooShort() {
        String idCardNumber = "12345";
        assertThrows(ModelInvalidException.class, () -> employee.setIdCardNumber(idCardNumber));
    }

    @Test
    void testSetIdCardNumberTooLong() {
        String idCardNumber = "123456789012345678901";
        assertThrows(ModelInvalidException.class, () -> employee.setIdCardNumber(idCardNumber));
    }

    @Test
    void testSetIdCardNumberInvalidFormat() {
        String idCardNumber = "123abc";
        assertThrows(ModelInvalidException.class, () -> employee.setIdCardNumber(idCardNumber));
    }

    @Test
    void testSetFirstname() {
        String firstname = "John";
        employee.setFirstname(firstname);
        assertEquals(firstname, employee.getFirstname());
    }

    @Test
    void testSetFirstnameNull() {
        assertThrows(ModelInvalidException.class, () -> employee.setFirstname(null));
    }

    @Test
    void testSetFirstnameTooLong() {
        String firstname = "frstnm".repeat(60);
        assertThrows(ModelInvalidException.class, () -> employee.setFirstname(firstname));
    }

    @Test
    void testSetLastname() {
        String firstname = "Doe";
        employee.setLastname(firstname);
        assertEquals(firstname, employee.getLastname());
    }

    @Test
    void testSetLastnameNull() {
        assertThrows(ModelInvalidException.class, () -> employee.setLastname(null));
    }

    @Test
    void testSetLastnameTooLong() {
        String lastname = "lstnm".repeat(60);
        assertThrows(ModelInvalidException.class, () -> employee.setLastname(lastname));
    }

    @Test
    void testSetGender() {
        Gender gender = Gender.MALE;
        employee.setGender(gender);
        assertEquals(gender, employee.getGender());
    }

    @Test
    void testSetEmail() {
        String email = "test@example.com";
        employee.setEmail(email);
        assertEquals(email, employee.getEmail());
    }

    @Test
    void testSetEmailNull() {
        assertThrows(ModelInvalidException.class, () -> employee.setEmail(null));
    }

    @Test
    void testSetEmailTooLong() {
        String longEmail = "test@example" + "x".repeat(320) + ".com";
        assertThrows(ModelInvalidException.class, () -> employee.setEmail(longEmail));
    }

    @Test
    void testSetBuilding() {
        Building building = new Building();
        employee.setBuilding(building);
        assertEquals(building, employee.getBuilding());
    }

    @Test
    void testSetUserProfile() {
        UserProfile userProfile = new UserProfile();
        employee.setUserProfile(userProfile);
        assertEquals(userProfile, employee.getUserProfile());
    }
}