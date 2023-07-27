package com.djordjije11.libraryappapi.service.lending.impl;

import com.djordjije11.libraryappapi.exception.RecordNotFoundException;
import com.djordjije11.libraryappapi.exception.lending.BookCopyNotAvailableForLendingException;
import com.djordjije11.libraryappapi.exception.lending.BookCopyNotInBuildingForLending;
import com.djordjije11.libraryappapi.exception.lending.LendingAlreadyReturnedException;
import com.djordjije11.libraryappapi.exception.lending.LendingReturnedNotByMemberException;
import com.djordjije11.libraryappapi.model.*;
import com.djordjije11.libraryappapi.repository.BookCopyRepository;
import com.djordjije11.libraryappapi.repository.BuildingRepository;
import com.djordjije11.libraryappapi.repository.LendingRepository;
import com.djordjije11.libraryappapi.repository.MemberRepository;
import com.djordjije11.libraryappapi.service.GlobalTransactional;
import com.djordjije11.libraryappapi.service.lending.LendingService;
import com.djordjije11.libraryappapi.specification.lending.LendingSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@GlobalTransactional
@Service
public class LendingServiceImpl implements LendingService {
    private final LendingRepository lendingRepository;
    private final BuildingRepository buildingRepository;
    private final MemberRepository memberRepository;
    private final BookCopyRepository bookCopyRepository;

    public LendingServiceImpl(LendingRepository lendingRepository, BuildingRepository buildingRepository, MemberRepository memberRepository, BookCopyRepository bookCopyRepository) {
        this.lendingRepository = lendingRepository;
        this.buildingRepository = buildingRepository;
        this.memberRepository = memberRepository;
        this.bookCopyRepository = bookCopyRepository;
    }

    @Override
    public List<Lending> returnLendings(Iterable<Long> lendingsIds, Long memberId, Long buildingId) throws LendingAlreadyReturnedException, LendingReturnedNotByMemberException {
        if (buildingRepository.existsById(buildingId) == false) {
            throw new RecordNotFoundException(Building.class, buildingId);
        }
        final var lendings = new LinkedList<Lending>();
        final Building building = buildingRepository.getReferenceById(buildingId);
        final LocalDate returnDate = LocalDate.now();
        for (Long lendingId :
                lendingsIds) {
            final Lending lending = lendingRepository.findById(lendingId)
                    .orElseThrow(() -> new RecordNotFoundException(Lending.class, lendingId));
            if(lending.getMember().getId().equals(memberId) == false){
                throw new LendingReturnedNotByMemberException(lendingId, memberId);
            }
            if (lending.getReturnDate() != null) {
                throw new LendingAlreadyReturnedException(lendingId);
            }
            lending.setReturnDate(returnDate);
            final BookCopy bookCopy = lending.getBookCopy();
            bookCopy.setStatus(BookCopyStatus.AVAILABLE);
            bookCopy.setBuilding(building);
            lendings.add(lendingRepository.save(lending));
        }
        return lendings;
    }

    @Override
    public List<Lending> createLendings(Iterable<Long> bookCopiesIds, Long memberId, Long buildingId) throws BookCopyNotAvailableForLendingException, BookCopyNotInBuildingForLending {
        if (memberRepository.existsById(memberId) == false) {
            throw new RecordNotFoundException(Member.class, memberId);
        }
        final var lendings = new LinkedList<Lending>();
        final Member member = memberRepository.getReferenceById(memberId);
        final LocalDate lendingDate = LocalDate.now();
        for (Long bookCopyId :
                bookCopiesIds) {
            final BookCopy bookCopy = bookCopyRepository.findById(bookCopyId)
                    .orElseThrow(() -> new RecordNotFoundException(BookCopy.class, bookCopyId));
            if(bookCopy.getBuilding().getId().equals(buildingId) == false){
                throw new BookCopyNotInBuildingForLending(bookCopyId, buildingId);
            }
            if (bookCopy.getStatus() != BookCopyStatus.AVAILABLE) {
                throw new BookCopyNotAvailableForLendingException(bookCopyId);
            }
            bookCopy.setStatus(BookCopyStatus.LENT);
            bookCopy.setBuilding(null);
            bookCopyRepository.save(bookCopy);
            lendings.add(lendingRepository.save(new Lending(lendingDate, bookCopy, member)));
        }
        return lendings;
    }

    @Override
    public Page<Lending> getLendingsByMember(Long memberId, String search, Pageable pageable) {
        return lendingRepository.findAll(LendingSpecification.byMember(memberId).and(LendingSpecification.bySearch(search)), pageable);
    }

    @Override
    public Page<Lending> getUnreturnedLendingsByMember(Long memberId, String search, Pageable pageable) {
        return lendingRepository.findAll(
                LendingSpecification.isUnreturned()
                        .and(LendingSpecification.byMember(memberId))
                        .and(LendingSpecification.bySearch(search)),
                pageable);
    }
}
