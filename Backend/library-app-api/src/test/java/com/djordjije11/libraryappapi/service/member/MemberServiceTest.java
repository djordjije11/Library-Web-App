package com.djordjije11.libraryappapi.service.member;

import com.djordjije11.libraryappapi.exception.RecordNotCurrentVersionException;
import com.djordjije11.libraryappapi.exception.RecordNotFoundException;
import com.djordjije11.libraryappapi.exception.RequestNotValidException;
import com.djordjije11.libraryappapi.exception.member.MemberIdCardNotUniqueException;
import com.djordjije11.libraryappapi.exception.member.MemberWithLendingsDeleteException;
import com.djordjije11.libraryappapi.model.*;
import com.djordjije11.libraryappapi.repository.BookCopyRepository;
import com.djordjije11.libraryappapi.repository.BookRepository;
import com.djordjije11.libraryappapi.repository.LendingRepository;
import com.djordjije11.libraryappapi.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public abstract class MemberServiceTest {
    @Autowired
    protected MemberRepository memberRepository;
    @Autowired
    protected LendingRepository lendingRepository;
    protected MemberService memberService;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookCopyRepository bookCopyRepository;

    @Test
    public void create() {
        var member = new Member("123456789", "Firstname", "Lastname", Gender.MALE, "email@gmail.com", LocalDate.of(2001, Month.FEBRUARY, 7));
        try {
            memberService.create(member);
        } catch (MemberIdCardNotUniqueException e) {
            fail();
        }

        var maybeDbMember = memberRepository.findById(member.getId());
        if (maybeDbMember.isEmpty()) {
            fail();
        }
        var dbMember = maybeDbMember.get();

        assertNotNull(dbMember);
        assertEquals(member.getIdCardNumber(), dbMember.getIdCardNumber());
        assertEquals(member.getFirstname(), dbMember.getFirstname());
        assertEquals(member.getLastname(), dbMember.getLastname());
        assertEquals(member.getGender(), dbMember.getGender());
        assertEquals(member.getEmail(), dbMember.getEmail());
        assertEquals(member.getBirthday(), dbMember.getBirthday());
    }

    @Test
    public void create_when_idCardNumber_is_not_unique_throws_MemberIdCardNotUniqueException() {
        var member1 = new Member("123456789", "Firstname", "Lastname", Gender.MALE, "email@gmail.com", LocalDate.of(2001, Month.FEBRUARY, 7));
        memberRepository.save(member1);
        var member2 = new Member("123456789", "Firstname", "Lastname", Gender.FEMALE, "email@yahoo.com", LocalDate.of(2000, Month.AUGUST, 10));

        assertThrows(MemberIdCardNotUniqueException.class, () -> memberService.create(member2));
    }

    @Test
    public void delete() {
        var member = new Member("123456789", "Firstname", "Lastname", Gender.MALE, "email@gmail.com", LocalDate.of(2001, Month.FEBRUARY, 7));
        memberRepository.save(member);

        try {
            memberService.delete(member.getId());
        } catch (MemberWithLendingsDeleteException e) {
            fail();
        }

        assertFalse(memberRepository.existsById(member.getId()));
    }

    @Test
    public void delete_when_member_has_lendings_throws_MemberWithLendingsDeleteException() {
        var member = new Member("123456789", "Firstname", "Lastname", Gender.MALE, "email@gmail.com", LocalDate.of(2001, Month.FEBRUARY, 7));
        memberRepository.save(member);
        var book = new Book("Title", "Description", null, 110);
        bookRepository.save(book);
        var bookCopy = new BookCopy("123456789", BookCopyStatus.LENT, book, null);
        bookCopyRepository.save(bookCopy);
        var lending = new Lending(LocalDate.now(), bookCopy, member);
        lendingRepository.save(lending);

        assertThrows(MemberWithLendingsDeleteException.class, () -> memberService.delete(member.getId()));
    }

    @Test
    public void update() {
        var member = new Member("123456789", "Firstname", "Lastname", Gender.MALE, "email@gmail.com", LocalDate.of(2001, Month.FEBRUARY, 7));
        memberRepository.save(member);
        var updateMember = new Member("103456789", "Firstname 2", "Lastname 2", Gender.FEMALE, "emailll@gmail.com", LocalDate.of(2000, Month.FEBRUARY, 7));
        updateMember.setId(member.getId());

        try {
            memberService.update(updateMember);
        } catch (RequestNotValidException e) {
            fail();
        }

        var maybeDbMember = memberRepository.findById(updateMember.getId());
        if (maybeDbMember.isEmpty()) {
            fail();
        }
        var dbMember = maybeDbMember.get();

        assertEquals(dbMember.getIdCardNumber(), updateMember.getIdCardNumber());
        assertEquals(member.getFirstname(), updateMember.getFirstname());
        assertEquals(member.getLastname(), updateMember.getLastname());
        assertEquals(member.getGender(), updateMember.getGender());
        assertEquals(member.getEmail(), updateMember.getEmail());
        assertEquals(member.getBirthday(), updateMember.getBirthday());
    }

    @Test
    public void update_when_member_does_not_exist_throws_RecordNotFoundException() {
        var member = new Member("123456789", "Firstname", "Lastname", Gender.MALE, "email@gmail.com", LocalDate.of(2001, Month.FEBRUARY, 7));
        member.setId(10L);

        assertThrows(RecordNotFoundException.class, () -> memberService.update(member));
    }

    @Test
    public void update_when_RowVersion_is_not_valid_throws_RecordNotCurrentVersionException() {
        var member = new Member("123456789", "Firstname", "Lastname", Gender.MALE, "email@gmail.com", LocalDate.of(2001, Month.FEBRUARY, 7));
        memberRepository.save(member);
        var updateMember = new Member("103456789", "Firstname 2", "Lastname 2", Gender.FEMALE, "emailll@gmail.com", LocalDate.of(2000, Month.FEBRUARY, 7));
        updateMember.setId(member.getId());
        updateMember.setRowVersion(member.getRowVersion() - 1);

        assertThrows(RecordNotCurrentVersionException.class, () -> memberService.update(updateMember));
    }

    @Test
    public void update_when_idCardNumber_is_not_unique_throws_MemberIdCardNotUniqueException() {
        var member1 = new Member("123456789", "Firstname", "Lastname", Gender.MALE, "email@gmail.com", LocalDate.of(2001, Month.FEBRUARY, 7));
        var member2 = new Member("103456789", "Firstname 2", "Lastname 2", Gender.MALE, "email@gmail.com", LocalDate.of(2001, Month.SEPTEMBER, 25));
        memberRepository.save(member1);
        memberRepository.save(member2);
        var updateMember = new Member(member2.getIdCardNumber(), "Firstname 3", "Lastname 3", Gender.FEMALE, "emailll@gmail.com", LocalDate.of(2000, Month.FEBRUARY, 7));
        updateMember.setId(member1.getId());

        assertThrows(MemberIdCardNotUniqueException.class, () -> memberService.update(updateMember));
    }

}