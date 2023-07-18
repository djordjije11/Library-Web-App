package com.djordjije11.libraryappapi.service.lending;

import com.djordjije11.libraryappapi.exception.lending.LendingAlreadyReturnedException;
import com.djordjije11.libraryappapi.model.Lending;
import java.util.List;

public interface LendingService {
    void returnLendings(Iterable<Long> lendingsIds, Long buildingId) throws LendingAlreadyReturnedException;
    void createLendings(Long memberId, Iterable<Long> bookCopiesIds);
    List<Lending> getLendingsByMember(Long memberId);
    List<Lending> getUnreturnedLendingsByMember(Long memberId);
}
