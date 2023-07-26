package com.djordjije11.libraryappapi.service.lending;

import com.djordjije11.libraryappapi.exception.lending.LendingAlreadyReturnedException;
import com.djordjije11.libraryappapi.model.Lending;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LendingService {
    void returnLendings(Iterable<Long> lendingsIds, Long buildingId) throws LendingAlreadyReturnedException;

    void createLendings(Long memberId, Iterable<Long> bookCopiesIds);

    Page<Lending> getLendingsByMember(Long memberId, String search, Pageable pageable);

    Page<Lending> getUnreturnedLendingsByMember(Long memberId, String search, Pageable pageable);
}
