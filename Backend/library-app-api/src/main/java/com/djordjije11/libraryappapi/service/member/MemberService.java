package com.djordjije11.libraryappapi.service.member;

import com.djordjije11.libraryappapi.exception.RecordNotCurrentVersionException;
import com.djordjije11.libraryappapi.exception.RecordNotFoundException;
import com.djordjije11.libraryappapi.exception.RequestNotValidException;
import com.djordjije11.libraryappapi.exception.member.MemberIdCardNotUniqueException;
import com.djordjije11.libraryappapi.exception.member.MemberWithLendingsDeleteException;
import com.djordjije11.libraryappapi.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Represents the service layer for a Member class.
 */
public interface MemberService {
    /**
     * Saves the member to the library system's database and returns the saved member.
     * Throws MemberIdCardNotUniqueException when idCardNumber of the member is not unique.
     * @param member a member to be saved.
     * @return saved member.
     * @throws MemberIdCardNotUniqueException when idCardNumber of the member is not unique in the library system's database.
     */
    Member create(Member member) throws MemberIdCardNotUniqueException;

    /**
     * Returns the member by id. Throws RecordNotFoundException when the record does not exist in the library system's database.
     * @param id the member's id.
     * @return member by id.
     * @throws RecordNotFoundException when the record does not exist in the library system's database.
     */
    Member get(Long id);

    /**
     * Deletes the member by id. Throws MemberWithLendingsDeleteException when the member already has a history of lendings in the library system.
     * @param id the member's id.
     * @throws MemberWithLendingsDeleteException when the member already has a history of lendings in the library system.
     */
    void delete(Long id) throws MemberWithLendingsDeleteException;

    /**
     * Updates the member in the library system's database and returns the updated member.
     * Throws:
     * RecordNotFoundException when the member with its id does not exist in the library system's database,
     * RecordNotCurrentVersionException when the record's rowVersion is not up-to-date,
     * MemberIdCardNotUniqueException when idCardNumber of the member is not unique in the library system's database.
     * @param member a member to be updated.
     * @return updated member.
     * @throws MemberIdCardNotUniqueException when idCardNumber of the member is not unique in the library system's database.
     * @throws RecordNotFoundException when the member with its id does not exist in the library system's database.
     * @throws RecordNotCurrentVersionException when the record's rowVersion is not up-to-date.
     */
    Member update(Member member) throws MemberIdCardNotUniqueException, RecordNotFoundException;

    /**
     * Returns the page of members filtered by search text and selected by options of Pageable instance.
     * The search text should contain member's idCardNumber, firstname, lastname or email.
     * @param search by member's idCardNumber, firstname, lastname or email.
     * @param pageable contains options for skipping and taking database records and sorting them.
     * @return page of members filtered by search text and selected by options of Pageable instance.
     */
    Page<Member> get(String search, Pageable pageable);
}
