package com.djordjije11.libraryappapi.service.lending.impl;

import com.djordjije11.libraryappapi.exception.RecordNotFoundException;
import com.djordjije11.libraryappapi.exception.lending.LendingAlreadyReturnedException;
import com.djordjije11.libraryappapi.model.*;
import com.djordjije11.libraryappapi.repository.BookCopyRepository;
import com.djordjije11.libraryappapi.repository.BuildingRepository;
import com.djordjije11.libraryappapi.repository.LendingRepository;
import com.djordjije11.libraryappapi.repository.MemberRepository;
import com.djordjije11.libraryappapi.service.GlobalTransactional;
import com.djordjije11.libraryappapi.service.lending.LendingService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
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
    public void returnLendings(Iterable<Long> lendingsIds, Long buildingId) throws LendingAlreadyReturnedException {
        if (buildingRepository.existsById(buildingId) == false) {
            throw new RecordNotFoundException(Building.class, buildingId);
        }
        Building building = buildingRepository.getReferenceById(buildingId);
        LocalDate returnDate = LocalDate.now();
        for (Long lendingId :
                lendingsIds) {
            Lending lending = lendingRepository.findById(lendingId)
                    .orElseThrow(() -> new RecordNotFoundException(Lending.class, lendingId));
            if (lending.getReturnDate() != null) {
                throw new LendingAlreadyReturnedException(lendingId);
            }
            lending.setReturnDate(returnDate);
            BookCopy bookCopy = lending.getBookCopy();
            bookCopy.setStatus(BookCopyStatus.AVAILABLE);
            bookCopy.setBuilding(building);
            lendingRepository.save(lending);
        }
    }

    @Override
    public void createLendings(Long memberId, Iterable<Long> bookCopiesIds) {
        if (memberRepository.existsById(memberId) == false) {
            throw new RecordNotFoundException(Member.class, memberId);
        }
        Member member = memberRepository.getReferenceById(memberId);
        LocalDate lendingDate = LocalDate.now();
        bookCopiesIds.forEach(bcId -> {
            BookCopy bookCopy = bookCopyRepository.findById(bcId)
                    .orElseThrow(() -> new RecordNotFoundException(BookCopy.class, bcId));
            bookCopy.setStatus(BookCopyStatus.LENT);
            bookCopy.setBuilding(null);
            bookCopyRepository.save(bookCopy);
            lendingRepository.save(new Lending(lendingDate, bookCopy, member));
        });
    }

    @Override
    public List<Lending> getLendingsByMember(Long memberId) {
        if (memberRepository.existsById(memberId) == false) {
            throw new RecordNotFoundException(Member.class, memberId);
        }
        return lendingRepository.findAllByMember_Id(memberId);
    }

    @Override
    public List<Lending> getUnreturnedLendingsByMember(Long memberId) {
        if (memberRepository.existsById(memberId) == false) {
            throw new RecordNotFoundException(Member.class, memberId);
        }
        return lendingRepository.findAllByMember_IdAndReturnDateIsNull(memberId);
    }
}
