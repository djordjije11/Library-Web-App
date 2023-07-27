package com.djordjije11.libraryappapi.service.enroll.impl;

import com.djordjije11.libraryappapi.config.authentication.EmployeeClaim;
import com.djordjije11.libraryappapi.model.*;
import com.djordjije11.libraryappapi.repository.BuildingRepository;
import com.djordjije11.libraryappapi.repository.MemberRepository;
import com.djordjije11.libraryappapi.service.enroll.LendingEnroller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LendingEnrollerImpl implements LendingEnroller {
    private final MemberRepository memberRepository;
    private final BuildingRepository buildingRepository;
    private final Logger logger = LoggerFactory.getLogger(LendingEnrollerImpl.class);

    public LendingEnrollerImpl(MemberRepository memberRepository, BuildingRepository buildingRepository) {
        this.memberRepository = memberRepository;
        this.buildingRepository = buildingRepository;
    }

    private boolean writeEmployeeMemberBuilding(StringBuilder stringBuilder, EmployeeClaim employeeClaim, Long memberId) {
        boolean error = false;
        stringBuilder.append("Employee:");
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append(employeeClaim.toString());
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append("Building:");
        stringBuilder.append(System.lineSeparator());
        Optional<Building> maybeBuilding = buildingRepository.findById(employeeClaim.buildingId());
        if (maybeBuilding.isEmpty()) {
            error = true;
            stringBuilder.append(String.format("NOT IN THE DATABASE, ID: %d", employeeClaim.buildingId()));
        } else {
            final Building building = maybeBuilding.get();
            final Address address = building.getAddress();
            final City city = address.getCity();
            stringBuilder.append(String.format("%s %s, %s, %s", address.getStreetName(), address.getStreetNumber(), city.getName(), city.getZipcode()));
        }
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append("Member:");
        stringBuilder.append(System.lineSeparator());
        Optional<Member> maybeMember = memberRepository.findById(memberId);
        if (maybeMember.isEmpty()) {
            error = true;
            stringBuilder.append(String.format("NOT IN THE DATABASE, ID: %d", memberId));
        } else {
            final Member member = maybeMember.get();
            stringBuilder.append(String.format("Member %s %s, email: %s", member.getFirstname(), member.getLastname(), member.getEmail()));
            stringBuilder.append(System.lineSeparator());
            stringBuilder.append(String.format("ID: %d, ID card number: %s", member.getId(), member.getIdCardNumber()));
        }
        stringBuilder.append(System.lineSeparator());
        return error;
    }

    private void writeLendings(StringBuilder stringBuilder, List<Lending> lendings) {
        lendings.forEach(lending -> {
            final BookCopy bookCopy = lending.getBookCopy();
            final Book book = bookCopy.getBook();
            stringBuilder.append(String.format("ISBN: %s, %s, published by %s", bookCopy.getIsbn(), book.getTitle(), book.getPublisher().getName()));
            stringBuilder.append(System.lineSeparator());
        });
    }

    @Override
    public void enrollLendings(EmployeeClaim employeeClaim, List<Lending> lendings, Long memberId) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("LENDINGS ENROLLMENT");
        stringBuilder.append(System.lineSeparator());
        final boolean error = writeEmployeeMemberBuilding(stringBuilder, employeeClaim, memberId);
        stringBuilder.append(String.format("On date %s lent books:", LocalDate.now()));
        stringBuilder.append(System.lineSeparator());
        writeLendings(stringBuilder, lendings);
        if (error) {
            logger.error(stringBuilder.toString());
        } else {
            logger.info(stringBuilder.toString());
        }
    }

    @Override
    public void enrollReturnedLendings(EmployeeClaim employeeClaim, List<Lending> lendings, Long memberId) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("RETURNED LENDINGS ENROLLMENT");
        stringBuilder.append(System.lineSeparator());
        final boolean error = writeEmployeeMemberBuilding(stringBuilder, employeeClaim, memberId);
        stringBuilder.append(String.format("On date %s returned lent books:", LocalDate.now()));
        stringBuilder.append(System.lineSeparator());
        writeLendings(stringBuilder, lendings);
        if (error) {
            logger.error(stringBuilder.toString());
        } else {
            logger.info(stringBuilder.toString());
        }
    }
}
