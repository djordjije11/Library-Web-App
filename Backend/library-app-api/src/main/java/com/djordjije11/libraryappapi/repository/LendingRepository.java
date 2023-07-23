package com.djordjije11.libraryappapi.repository;

import com.djordjije11.libraryappapi.helper.string.util.StringExt;
import com.djordjije11.libraryappapi.model.Lending;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LendingRepository extends JpaRepository<Lending, Long> {
    boolean existsByMemberId(Long memberId);

    Page<Lending> findAllByMember_Id(Pageable pageable, Long memberId);

    Page<Lending> findAllByMember_IdAndBookCopy_IsbnContainsOrMember_IdAndBookCopy_Book_TitleContainsIgnoreCase(Pageable pageable, Long memberId1, String isbn, Long memberId2, String title);

    Page<Lending> findAllByMember_IdAndReturnDateIsNull(Pageable pageable, Long memberId);

    Page<Lending> findAllByMember_IdAndBookCopy_IsbnContainsAndReturnDateIsNullOrMember_IdAndBookCopy_Book_TitleContainsIgnoreCaseAndReturnDateIsNull(Pageable pageable, Long memberId1, String isbn, Long memberId2, String title);

    default Page<Lending> findAllLendingsByMember(Pageable pageable, Long memberId, String filter) {
        if (StringExt.isNullOrBlank(filter)) {
            return findAllByMember_Id(pageable, memberId);
        }
        return findAllByMember_IdAndBookCopy_IsbnContainsOrMember_IdAndBookCopy_Book_TitleContainsIgnoreCase(pageable, memberId, filter, memberId, filter);
    }

    default Page<Lending> findAllUnreturnedLendingsByMember(Pageable pageable, Long memberId, String filter) {
        if (StringExt.isNullOrBlank(filter)) {
            return findAllByMember_IdAndReturnDateIsNull(pageable, memberId);
        }
        return findAllByMember_IdAndBookCopy_IsbnContainsAndReturnDateIsNullOrMember_IdAndBookCopy_Book_TitleContainsIgnoreCaseAndReturnDateIsNull(pageable, memberId, filter, memberId, filter);
    }
}
