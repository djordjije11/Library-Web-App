package com.djordjije11.libraryappapi.service.lending;

import com.djordjije11.libraryappapi.exception.lending.LendingAlreadyReturnedException;
import com.djordjije11.libraryappapi.model.Lending;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface LendingService {
    void returnLendings(Iterable<Long> lendingsIds, Long buildingId) throws LendingAlreadyReturnedException;
    void createLendings(Long memberId, Iterable<Long> bookCopiesIds);
    Page<Lending> getLendings(Specification<Lending> specification, Pageable pageable);
}
