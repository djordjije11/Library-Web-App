package com.djordjije11.libraryappapi.service.lending;

import com.djordjije11.libraryappapi.exception.lending.BookCopyNotAvailableForLendingException;
import com.djordjije11.libraryappapi.exception.lending.BookCopyNotInBuildingForLendingException;
import com.djordjije11.libraryappapi.exception.lending.LendingAlreadyReturnedException;
import com.djordjije11.libraryappapi.exception.lending.LendingReturnedNotByMemberException;
import com.djordjije11.libraryappapi.model.Lending;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LendingService {
    List<Lending> returnLendings(Iterable<Long> lendingsIds, Long memberId, Long buildingId) throws LendingAlreadyReturnedException, LendingReturnedNotByMemberException;

    List<Lending> createLendings(Iterable<Long> bookCopiesIds, Long memberId, Long buildingId) throws BookCopyNotAvailableForLendingException, BookCopyNotInBuildingForLendingException;

    Page<Lending> getLendingsByMember(Long memberId, String search, Pageable pageable);

    Page<Lending> getUnreturnedLendingsByMember(Long memberId, String search, Pageable pageable);
}
