package com.djordjije11.libraryappapi.repository;

import com.djordjije11.libraryappapi.helper.string.util.StringExt;
import com.djordjije11.libraryappapi.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByIdCardNumber(String idCardNumber);
    boolean existsByIdCardNumberAndIdIsNot(String idCardNumber, Long id);
    Page<Member> findAllByIdCardNumberContainsOrEmailContainsIgnoreCaseOrFirstnameContainsIgnoreCaseOrLastnameContainsIgnoreCase(Pageable pageable, String idCardNumber, String email, String firstname, String lastname);
    Page<Member> findAllByFirstnameContainsIgnoreCaseAndLastnameContainsIgnoreCaseOrFirstnameContainsIgnoreCaseAndLastnameContainsIgnoreCase(Pageable pageable, String firstname1, String lastname1, String firstname2, String lastname2);

    default Page<Member> findAllMembersByFullName(Pageable pageable, String name1, String name2){
        return findAllByFirstnameContainsIgnoreCaseAndLastnameContainsIgnoreCaseOrFirstnameContainsIgnoreCaseAndLastnameContainsIgnoreCase(pageable, name1, name2, name2, name1);
    }

    default Page<Member> findAllMembersByAnyFilter(Pageable pageable, String filter){
        return findAllByIdCardNumberContainsOrEmailContainsIgnoreCaseOrFirstnameContainsIgnoreCaseOrLastnameContainsIgnoreCase(pageable, filter, filter, filter, filter);
    }

    default Page<Member> findAllMembers(Pageable pageable, String filter) {
        if (StringExt.isNullOrBlank(filter)) {
            return findAll(pageable);
        }
        String[] names = filter.split(StringExt.SPACE, 2);
        if (names.length > 1){
            return findAllMembersByFullName(pageable, names[0], names[1]);
        }
        return findAllMembersByAnyFilter(pageable, filter);
    }
}
