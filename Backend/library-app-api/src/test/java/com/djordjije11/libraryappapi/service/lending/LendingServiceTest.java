package com.djordjije11.libraryappapi.service.lending;

import com.djordjije11.libraryappapi.exception.RecordNotFoundException;
import com.djordjije11.libraryappapi.exception.lending.BookCopyNotAvailableForLendingException;
import com.djordjije11.libraryappapi.exception.lending.BookCopyNotInBuildingForLendingException;
import com.djordjije11.libraryappapi.exception.lending.LendingAlreadyReturnedException;
import com.djordjije11.libraryappapi.exception.lending.LendingReturnedNotByMemberException;
import com.djordjije11.libraryappapi.model.*;
import com.djordjije11.libraryappapi.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public abstract class LendingServiceTest {
    @Autowired
    protected LendingRepository lendingRepository;
    @Autowired
    protected BuildingRepository buildingRepository;
    @Autowired
    protected MemberRepository memberRepository;
    @Autowired
    protected BookCopyRepository bookCopyRepository;
    protected LendingService lendingService;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private AddressRepository addressRepository;

    @Test
    public void createLendings() {
        var member = new Member("123456789", "Firstname", "Lastname", Gender.MALE, "email@gmail.com", LocalDate.of(2001, Month.FEBRUARY, 7));
        memberRepository.save(member);
        var book1 = new Book("Title", "Description", null, 110);
        var book2 = new Book("Title 2", "Description 2", "Image Url", 220);
        bookRepository.save(book1);
        bookRepository.save(book2);
        var city = new City("City", "11000");
        cityRepository.save(city);
        var address = new Address("StreetName", 10, city);
        addressRepository.save(address);
        var building = new Building(address);
        buildingRepository.save(building);
        var bookCopy1 = new BookCopy("123456789", BookCopyStatus.AVAILABLE, book1, building);
        var bookCopy2 = new BookCopy("123456789", BookCopyStatus.AVAILABLE, book1, building);
        bookCopyRepository.save(bookCopy1);
        bookCopyRepository.save(bookCopy2);
        var bookCopiesIds = List.of(bookCopy1.getId(), bookCopy2.getId());

        try {
            var lendings = lendingService.createLendings(bookCopiesIds, member.getId(), building.getId());
            for (var lending :
                    lendings) {
                var maybeDbLending = lendingRepository.findById(lending.getId());
                if (maybeDbLending.isEmpty()) {
                    fail();
                }
                var dbLending = maybeDbLending.get();
                assertNotNull(dbLending);
                assertEquals(lending.getLendingDate(), dbLending.getLendingDate());
                assertEquals(lending.getReturnDate(), dbLending.getReturnDate());
                assertEquals(lending.getMember().getId(), dbLending.getMember().getId());
                assertEquals(lending.getBookCopy().getId(), dbLending.getBookCopy().getId());
            }
        } catch (Exception e) {
            fail();
        }

        var maybeDbBookCopy1 = bookCopyRepository.findById(bookCopy1.getId());
        if (maybeDbBookCopy1.isEmpty()) {
            fail();
        }
        var dbBookCopy1 = maybeDbBookCopy1.get();
        var maybeDbBookCopy2 = bookCopyRepository.findById(bookCopy2.getId());
        if (maybeDbBookCopy2.isEmpty()) {
            fail();
        }
        var dbBookCopy2 = maybeDbBookCopy2.get();
        assertNull(dbBookCopy1.getBuilding());
        assertNull(dbBookCopy2.getBuilding());
        assertEquals(BookCopyStatus.LENT, dbBookCopy1.getStatus());
        assertEquals(BookCopyStatus.LENT, dbBookCopy2.getStatus());
    }

    @Test
    public void createLendings_when_member_does_not_exist_throws_RecordNotFoundException() {
        var member = new Member("123456789", "Firstname", "Lastname", Gender.MALE, "email@gmail.com", LocalDate.of(2001, Month.FEBRUARY, 7));
        member.setId(10L);
        var book1 = new Book("Title", "Description", null, 110);
        var book2 = new Book("Title 2", "Description 2", "Image Url", 220);
        bookRepository.save(book1);
        bookRepository.save(book2);
        var city = new City("City", "11000");
        cityRepository.save(city);
        var address = new Address("StreetName", 10, city);
        addressRepository.save(address);
        var building = new Building(address);
        buildingRepository.save(building);
        var bookCopy1 = new BookCopy("123456789", BookCopyStatus.AVAILABLE, book1, building);
        var bookCopy2 = new BookCopy("123456789", BookCopyStatus.AVAILABLE, book1, building);
        bookCopyRepository.save(bookCopy1);
        bookCopyRepository.save(bookCopy2);
        var bookCopiesIds = List.of(bookCopy1.getId(), bookCopy2.getId());

        assertThrows(RecordNotFoundException.class, () -> lendingService.createLendings(bookCopiesIds, member.getId(), building.getId()));
    }

    @Test
    public void createLendings_when_book_copy_does_not_exist_throws_RecordNotFoundException() {
        var member = new Member("123456789", "Firstname", "Lastname", Gender.MALE, "email@gmail.com", LocalDate.of(2001, Month.FEBRUARY, 7));
        memberRepository.save(member);
        var book1 = new Book("Title", "Description", null, 110);
        var book2 = new Book("Title 2", "Description 2", "Image Url", 220);
        bookRepository.save(book1);
        bookRepository.save(book2);
        var city = new City("City", "11000");
        cityRepository.save(city);
        var address = new Address("StreetName", 10, city);
        addressRepository.save(address);
        var building = new Building(address);
        buildingRepository.save(building);
        var bookCopy1 = new BookCopy("123456789", BookCopyStatus.AVAILABLE, book1, building);
        var bookCopy2 = new BookCopy("123456789", BookCopyStatus.AVAILABLE, book1, building);
        bookCopyRepository.save(bookCopy1);
        bookCopy2.setId(10L);
        var bookCopiesIds = List.of(bookCopy1.getId(), bookCopy2.getId());

        assertThrows(RecordNotFoundException.class, () -> lendingService.createLendings(bookCopiesIds, member.getId(), building.getId()));
    }

    @Test
    public void createLendings_when_book_copy_is_not_in_the_building_throws_BookCopyNotInBuildingForLendingException() {
        var member = new Member("123456789", "Firstname", "Lastname", Gender.MALE, "email@gmail.com", LocalDate.of(2001, Month.FEBRUARY, 7));
        memberRepository.save(member);
        var book1 = new Book("Title", "Description", null, 110);
        var book2 = new Book("Title 2", "Description 2", "Image Url", 220);
        bookRepository.save(book1);
        bookRepository.save(book2);
        var city = new City("City", "11000");
        cityRepository.save(city);
        var address = new Address("StreetName", 10, city);
        addressRepository.save(address);
        var building = new Building(address);
        buildingRepository.save(building);
        var bookCopy1 = new BookCopy("123456789", BookCopyStatus.AVAILABLE, book1, building);
        var bookCopy2 = new BookCopy("123456789", BookCopyStatus.AVAILABLE, book1, null);
        bookCopyRepository.save(bookCopy1);
        bookCopyRepository.save(bookCopy2);
        var bookCopiesIds = List.of(bookCopy1.getId(), bookCopy2.getId());

        assertThrows(BookCopyNotInBuildingForLendingException.class, () -> lendingService.createLendings(bookCopiesIds, member.getId(), building.getId()));
    }

    @Test
    public void createLendings_when_book_copy_is_not_available_throws_BookCopyNotAvailableForLendingException() {
        var member = new Member("123456789", "Firstname", "Lastname", Gender.MALE, "email@gmail.com", LocalDate.of(2001, Month.FEBRUARY, 7));
        memberRepository.save(member);
        var book1 = new Book("Title", "Description", null, 110);
        var book2 = new Book("Title 2", "Description 2", "Image Url", 220);
        bookRepository.save(book1);
        bookRepository.save(book2);
        var city = new City("City", "11000");
        cityRepository.save(city);
        var address = new Address("StreetName", 10, city);
        addressRepository.save(address);
        var building = new Building(address);
        buildingRepository.save(building);
        var bookCopy1 = new BookCopy("123456789", BookCopyStatus.AVAILABLE, book1, building);
        var bookCopy2 = new BookCopy("123456789", BookCopyStatus.LENT, book1, building);
        bookCopyRepository.save(bookCopy1);
        bookCopyRepository.save(bookCopy2);
        var bookCopiesIds = List.of(bookCopy1.getId(), bookCopy2.getId());

        assertThrows(BookCopyNotAvailableForLendingException.class, () -> lendingService.createLendings(bookCopiesIds, member.getId(), building.getId()));
    }

    @Test
    public void returnLendings() {
        var member = new Member("123456789", "Firstname", "Lastname", Gender.MALE, "email@gmail.com", LocalDate.of(2001, Month.FEBRUARY, 7));
        memberRepository.save(member);
        var book1 = new Book("Title", "Description", null, 110);
        var book2 = new Book("Title 2", "Description 2", "Image Url", 220);
        bookRepository.save(book1);
        bookRepository.save(book2);
        var city = new City("City", "11000");
        cityRepository.save(city);
        var address = new Address("StreetName", 10, city);
        addressRepository.save(address);
        var building = new Building(address);
        buildingRepository.save(building);
        var bookCopy1 = new BookCopy("123456789", BookCopyStatus.LENT, book1, null);
        var bookCopy2 = new BookCopy("123456789", BookCopyStatus.LENT, book1, null);
        bookCopyRepository.save(bookCopy1);
        bookCopyRepository.save(bookCopy2);
        var lending1 = new Lending(LocalDate.now(), bookCopy1, member);
        var lending2 = new Lending(LocalDate.now(), bookCopy2, member);
        lendingRepository.save(lending1);
        lendingRepository.save(lending2);
        var lendingsToReturnIds = List.of(lending1.getId(), lending2.getId());

        try {
            lendingService.returnLendings(lendingsToReturnIds, member.getId(), building.getId());
        } catch (Exception e) {
            fail();
        }

        for (var returnedLendingId :
                lendingsToReturnIds) {
            var maybeDbLending = lendingRepository.findById(returnedLendingId);
            if (maybeDbLending.isEmpty()) {
                fail();
            }
            var dbLending = maybeDbLending.get();
            assertNotNull(dbLending);
            assertNotNull(dbLending.getReturnDate());
            var bookCopy = dbLending.getBookCopy();
            assertEquals(BookCopyStatus.AVAILABLE, bookCopy.getStatus());
            assertEquals(building.getId(), bookCopy.getBuilding().getId());
        }
    }

    @Test
    public void returnLendings_when_building_does_not_exist_throws_RecordNotFoundException() {
        var member = new Member("123456789", "Firstname", "Lastname", Gender.MALE, "email@gmail.com", LocalDate.of(2001, Month.FEBRUARY, 7));
        memberRepository.save(member);
        var book1 = new Book("Title", "Description", null, 110);
        var book2 = new Book("Title 2", "Description 2", "Image Url", 220);
        bookRepository.save(book1);
        bookRepository.save(book2);
        var city = new City("City", "11000");
        cityRepository.save(city);
        var address = new Address("StreetName", 10, city);
        addressRepository.save(address);
        var building = new Building(address);
        building.setId(10L);
        var bookCopy1 = new BookCopy("123456789", BookCopyStatus.LENT, book1, null);
        var bookCopy2 = new BookCopy("123456789", BookCopyStatus.LENT, book1, null);
        bookCopyRepository.save(bookCopy1);
        bookCopyRepository.save(bookCopy2);
        var lending1 = new Lending(LocalDate.now(), bookCopy1, member);
        var lending2 = new Lending(LocalDate.now(), bookCopy2, member);
        lendingRepository.save(lending1);
        lendingRepository.save(lending2);
        var lendingsToReturnIds = List.of(lending1.getId(), lending2.getId());

        assertThrows(RecordNotFoundException.class, () -> lendingService.returnLendings(lendingsToReturnIds, member.getId(), building.getId()));
    }

    @Test
    public void returnLendings_when_lending_does_not_exist_throws_RecordNotFoundException() {
        var member = new Member("123456789", "Firstname", "Lastname", Gender.MALE, "email@gmail.com", LocalDate.of(2001, Month.FEBRUARY, 7));
        memberRepository.save(member);
        var book1 = new Book("Title", "Description", null, 110);
        var book2 = new Book("Title 2", "Description 2", "Image Url", 220);
        bookRepository.save(book1);
        bookRepository.save(book2);
        var city = new City("City", "11000");
        cityRepository.save(city);
        var address = new Address("StreetName", 10, city);
        addressRepository.save(address);
        var building = new Building(address);
        buildingRepository.save(building);
        var bookCopy1 = new BookCopy("123456789", BookCopyStatus.LENT, book1, null);
        var bookCopy2 = new BookCopy("123456789", BookCopyStatus.LENT, book1, null);
        bookCopyRepository.save(bookCopy1);
        bookCopyRepository.save(bookCopy2);
        var lending1 = new Lending(LocalDate.now(), bookCopy1, member);
        var lending2 = new Lending(LocalDate.now(), bookCopy2, member);
        lendingRepository.save(lending1);
        lending2.setId(10L);
        var lendingsToReturnIds = List.of(lending1.getId(), lending2.getId());

        assertThrows(RecordNotFoundException.class, () -> lendingService.returnLendings(lendingsToReturnIds, member.getId(), building.getId()));
    }

    @Test
    public void returnLendings_when_member_is_not_valid_throws_LendingReturnedNotByMemberException() {
        var member1 = new Member("123456789", "Firstname", "Lastname", Gender.MALE, "email@gmail.com", LocalDate.of(2001, Month.FEBRUARY, 7));
        var member2 = new Member("123456781", "Firstname", "Lastname", Gender.FEMALE, "email@yahooo.com", LocalDate.of(1997, Month.JULY, 7));
        memberRepository.save(member1);
        memberRepository.save(member2);
        var book1 = new Book("Title", "Description", null, 110);
        var book2 = new Book("Title 2", "Description 2", "Image Url", 220);
        bookRepository.save(book1);
        bookRepository.save(book2);
        var city = new City("City", "11000");
        cityRepository.save(city);
        var address = new Address("StreetName", 10, city);
        addressRepository.save(address);
        var building = new Building(address);
        buildingRepository.save(building);
        var bookCopy1 = new BookCopy("123456789", BookCopyStatus.LENT, book1, null);
        var bookCopy2 = new BookCopy("123456789", BookCopyStatus.LENT, book1, null);
        bookCopyRepository.save(bookCopy1);
        bookCopyRepository.save(bookCopy2);
        var lending1 = new Lending(LocalDate.now(), bookCopy1, member1);
        var lending2 = new Lending(LocalDate.now(), bookCopy2, member2);
        lendingRepository.save(lending1);
        lendingRepository.save(lending2);
        var lendingsToReturnIds = List.of(lending1.getId(), lending2.getId());

        assertThrows(LendingReturnedNotByMemberException.class, () -> lendingService.returnLendings(lendingsToReturnIds, member1.getId(), building.getId()));
    }

    @Test
    public void returnLendings_when_lending_is_already_returned_throws_LendingAlreadyReturnedException() {
        var member = new Member("123456789", "Firstname", "Lastname", Gender.MALE, "email@gmail.com", LocalDate.of(2001, Month.FEBRUARY, 7));
        memberRepository.save(member);
        var book1 = new Book("Title", "Description", null, 110);
        var book2 = new Book("Title 2", "Description 2", "Image Url", 220);
        bookRepository.save(book1);
        bookRepository.save(book2);
        var city = new City("City", "11000");
        cityRepository.save(city);
        var address = new Address("StreetName", 10, city);
        addressRepository.save(address);
        var building = new Building(address);
        buildingRepository.save(building);
        var bookCopy1 = new BookCopy("123456789", BookCopyStatus.LENT, book1, null);
        var bookCopy2 = new BookCopy("123456789", BookCopyStatus.AVAILABLE, book1, building);
        bookCopyRepository.save(bookCopy1);
        bookCopyRepository.save(bookCopy2);
        var lending1 = new Lending(LocalDate.now(), bookCopy1, member);
        var lending2 = new Lending(LocalDate.now().minusDays(1), LocalDate.now(), bookCopy2, member);
        lendingRepository.save(lending1);
        lendingRepository.save(lending2);
        var lendingsToReturnIds = List.of(lending1.getId(), lending2.getId());

        assertThrows(LendingAlreadyReturnedException.class, () -> lendingService.returnLendings(lendingsToReturnIds, member.getId(), building.getId()));
    }
}