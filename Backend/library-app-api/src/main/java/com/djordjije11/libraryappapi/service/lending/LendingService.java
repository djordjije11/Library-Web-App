package com.djordjije11.libraryappapi.service.lending;

import com.djordjije11.libraryappapi.exception.lending.BookCopyNotAvailableForLendingException;
import com.djordjije11.libraryappapi.exception.lending.BookCopyNotInBuildingForLendingException;
import com.djordjije11.libraryappapi.exception.lending.LendingAlreadyReturnedException;
import com.djordjije11.libraryappapi.exception.lending.LendingReturnedNotByMemberException;
import com.djordjije11.libraryappapi.model.Lending;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.djordjije11.libraryappapi.exception.RecordNotFoundException;

import java.util.List;

/**
 * Represents the service layer for a Lending class.
 */
public interface LendingService {
    /**
     * Saves the returnment of lent book copies by the member to the library system's database and returns the saved lendings.
     * Throws:
     * RecordNotFoundException when any of the lendings does not exist in the library system's database,
     * LendingAlreadyReturnedException when any of the lendings has already been returned,
     * LendingReturnedNotByMemberException when any of the lendings has not been lent by specified member.
     *
     * @param lendingsIds the ids of the lendings of the lent book copies to be returned.
     * @param memberId    the id of the member that returns the books.
     * @param buildingId  the id of the library building that the book copies are returned in.
     * @return the updated lendings.
     * @throws RecordNotFoundException             when any of the lendings or the building does not exist in the library system's database.
     * @throws LendingAlreadyReturnedException     when any of the lendings has already been returned.
     * @throws LendingReturnedNotByMemberException when any of the lendings has not been lent by specified member.
     */
    List<Lending> returnLendings(Iterable<Long> lendingsIds, Long memberId, Long buildingId) throws LendingAlreadyReturnedException, LendingReturnedNotByMemberException;

    /**
     * Saves the lendings of book copies by the member to the library system's database and returns the saved lendings.
     * Throws:
     * RecordNotFoundException when any of the book copies or the member does not exist in the library system's database,
     * BookCopyNotAvailableForLendingException when any of the book copies' status is not available for lending,
     * BookCopyNotInBuildingForLendingException when any of the book copies' building does not match with the specified buliding.
     *
     * @param bookCopiesIds the ids of the book copies to be lent.
     * @param memberId      the id of the member that lends the books.
     * @param buildingId    the id of the library building that the book copies are lent in.
     * @return the saved lendings.
     * @throws RecordNotFoundException                  when any of the book copies or the member does not exist in the library system's database.
     * @throws BookCopyNotAvailableForLendingException  when any of the book copies' status is not available for lending.
     * @throws BookCopyNotInBuildingForLendingException when any of the book copies' building does not match with the specified buliding.
     */
    List<Lending> createLendings(Iterable<Long> bookCopiesIds, Long memberId, Long buildingId) throws BookCopyNotAvailableForLendingException, BookCopyNotInBuildingForLendingException;

    /**
     * Returns the page of lendings by the member filtered by search text and selected by options of Pageable instance.
     *
     * @param memberId the id of the member which the lendings are.
     * @param search   by book's title or book copy's ISBN.
     * @param pageable contains options for skipping and taking database records and sorting them.
     * @return page of lendings by the member filtered by search text and selected by options of Pageable instance.
     */
    Page<Lending> getLendingsByMember(Long memberId, String search, Pageable pageable);

    /**
     * Returns the page of unreturned lendings by the member filtered by search text and selected by options of Pageable instance.
     *
     * @param memberId the id of the member which the lendings are.
     * @param search   by book's title or book copy's ISBN.
     * @param pageable contains options for skipping and taking database records and sorting them.
     * @return page of unreturned lendings by the member filtered by search text and selected by options of Pageable instance.
     */
    Page<Lending> getUnreturnedLendingsByMember(Long memberId, String search, Pageable pageable);
}
