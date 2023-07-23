package com.djordjije11.libraryappapi.repository;

import com.djordjije11.libraryappapi.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MemberRepository extends JpaRepository<Member, Long>, JpaSpecificationExecutor<Member> {
    boolean existsByIdCardNumber(String idCardNumber);
    boolean existsByIdCardNumberAndIdIsNot(String idCardNumber, Long id);
}
