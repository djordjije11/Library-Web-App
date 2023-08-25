package com.djordjije11.libraryappapi.model;

import com.djordjije11.libraryappapi.exception.ModelInvalidException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {
    private Member member;

    @BeforeEach
    public void init() {
        member = new Member();
    }

    @Test
    public void testSetRowVersion() {
        long rowVersion = 10;
        member.setRowVersion(rowVersion);
        assertEquals(rowVersion, member.getRowVersion());
    }

    @Test
    public void testSetId() {
        long id = 1;
        member.setId(id);
        assertEquals(id, member.getId());
    }

    @Test
    void testSetIdCardNumber() {
        String idCardNumber = "1234567890";
        member.setIdCardNumber(idCardNumber);
        assertEquals(idCardNumber, member.getIdCardNumber());
    }

    @Test
    void testSetIdCardNumberInvalid() {
        assertThrows(ModelInvalidException.class, () -> member.setIdCardNumber("12345"));
        assertThrows(ModelInvalidException.class, () -> member.setIdCardNumber("123456789012345678901"));
        assertThrows(ModelInvalidException.class, () -> member.setIdCardNumber("abc1234567"));
    }

    @Test
    void testSetFirstname() {
        String firstname = "John";
        member.setFirstname(firstname);
        assertEquals(firstname, member.getFirstname());
    }

    @Test
    void testSetFirstnameTooLong() {
        String firstname = "a".repeat(256);
        assertThrows(ModelInvalidException.class, () -> member.setFirstname(firstname));
    }

    @Test
    void testSetLastname() {
        String lastname = "Doe";
        member.setLastname(lastname);
        assertEquals(lastname, member.getLastname());
    }

    @Test
    void testSetLastnameTooLong() {
        String lastname = "a".repeat(256);
        assertThrows(ModelInvalidException.class, () -> member.setLastname(lastname));
    }

    @Test
    void testSetGender() {
        Gender gender = Gender.MALE;
        member.setGender(gender);
        assertEquals(gender, member.getGender());
    }

    @Test
    void testSetEmail() {
        String email = "john@example.com";
        member.setEmail(email);
        assertEquals(email, member.getEmail());
    }

    @Test
    void testSetEmailTooLong() {
        String email = "a".repeat(321) + "@example.com";
        assertThrows(ModelInvalidException.class, () -> member.setEmail(email));
    }

    @Test
    void testSetBirthday() {
        LocalDate birthday = LocalDate.now().minusYears(20);
        member.setBirthday(birthday);
        assertEquals(birthday, member.getBirthday());
    }

    @Test
    void testSetBirthdayTooYoung() {
        LocalDate birthday = LocalDate.now().minusYears(15);
        assertThrows(ModelInvalidException.class, () -> member.setBirthday(birthday));
    }

    @Test
    void testSetLendings() {
        List<Lending> lendings = new ArrayList<>();
        member.setLendings(lendings);
        assertEquals(lendings, member.getLendings());
    }
}