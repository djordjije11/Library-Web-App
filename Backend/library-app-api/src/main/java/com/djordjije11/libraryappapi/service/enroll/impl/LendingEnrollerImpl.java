package com.djordjije11.libraryappapi.service.enroll.impl;

import com.djordjije11.libraryappapi.config.authentication.AuthClaimsHolder;
import com.djordjije11.libraryappapi.config.authentication.claim.BuildingClaim;
import com.djordjije11.libraryappapi.config.authentication.claim.EmployeeClaim;
import com.djordjije11.libraryappapi.model.*;
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
    private final Logger logger = LoggerFactory.getLogger(LendingEnrollerImpl.class);

    public LendingEnrollerImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void enrollLendings(AuthClaimsHolder authClaimsHolder, List<Lending> lendings, Long memberId) {
        writeLendingsEnrollment(authClaimsHolder, lendings, memberId, "LENDINGS ENROLLMENT", "LENT");
    }

    @Override
    public void enrollReturnedLendings(AuthClaimsHolder authClaimsHolder, List<Lending> lendings, Long memberId) {
        writeLendingsEnrollment(authClaimsHolder, lendings, memberId, "RETURNED LENDINGS ENROLLMENT", "RETURNED");
    }

    private void writeLendingsEnrollment(AuthClaimsHolder authClaimsHolder, List<Lending> lendings, Long memberId, String title, String action) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(title);
        stringBuilder.append(System.lineSeparator());
        writeEmployee(stringBuilder, authClaimsHolder.getEmployeeClaim());
        writeBuilding(stringBuilder, authClaimsHolder.getBuildingClaim());
        final boolean error = writeMember(stringBuilder, memberId);
        stringBuilder.append(String.format("On date: %s", LocalDate.now()));
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append(action);
        stringBuilder.append(System.lineSeparator());
        writeLendingsBooks(stringBuilder, lendings);
        if (error) {
            logger.error(stringBuilder.toString());
        } else {
            logger.info(stringBuilder.toString());
        }
    }

    private void writeEmployee(StringBuilder stringBuilder, EmployeeClaim employeeClaim) {
        stringBuilder.append("Employee:");
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append(String.format("%s %s, email: %s", employeeClaim.firstname(), employeeClaim.lastname(), employeeClaim.email()));
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append(
                String.format(
                        "ID: %d, ID card number: %s, User Profile ID: %d",
                        employeeClaim.id(), employeeClaim.idCardNumber(), employeeClaim.userProfileId()
                )
        );
        stringBuilder.append(System.lineSeparator());
    }

    private void writeBuilding(StringBuilder stringBuilder, BuildingClaim buildingClaim) {
        stringBuilder.append("Building:");
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append(String.format("%s, %s", buildingClaim.street(), buildingClaim.city()));
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append(String.format("ID: %d", buildingClaim.id()));
        stringBuilder.append(System.lineSeparator());
    }

    private boolean writeMember(StringBuilder stringBuilder, Long memberId) {
        boolean error = false;
        stringBuilder.append("Member:");
        stringBuilder.append(System.lineSeparator());
        Optional<Member> maybeMember = memberRepository.findById(memberId);
        if (maybeMember.isEmpty()) {
            error = true;
            stringBuilder.append(String.format("NOT IN THE DATABASE, ID: %d", memberId));
        } else {
            final Member member = maybeMember.get();
            stringBuilder.append(String.format("%s %s, email: %s", member.getFirstname(), member.getLastname(), member.getEmail()));
            stringBuilder.append(System.lineSeparator());
            stringBuilder.append(String.format("ID: %d, ID card number: %s", member.getId(), member.getIdCardNumber()));
        }
        stringBuilder.append(System.lineSeparator());
        return error;
    }

    private void writeLendingsBooks(StringBuilder stringBuilder, List<Lending> lendings) {
        stringBuilder.append("Books:");
        stringBuilder.append(System.lineSeparator());
        lendings.forEach(lending -> {
            final BookCopy bookCopy = lending.getBookCopy();
            final Book book = bookCopy.getBook();
            stringBuilder.append(String.format("ISBN: %s, %s, published by %s", bookCopy.getIsbn(), book.getTitle(), book.getPublisher().getName()));
            stringBuilder.append(System.lineSeparator());
        });
    }
}
