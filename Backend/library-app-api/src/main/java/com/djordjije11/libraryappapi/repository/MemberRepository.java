package com.djordjije11.libraryappapi.repository;

import com.djordjije11.libraryappapi.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByIdCardNumber(String idCardNumber);
    boolean existsByIdCardNumberAndIdIsNot(String idCardNumber, Long id);

    //@Query(value = "SELECT new Member(m.id, m.idCardNumber, m.firstname, m.lastname, m.email) FROM Member m where m.id = ?1")
    @Query(value = "SELECT new Member(m.id, m.idCardNumber, m.firstname, m.lastname, m.email) FROM Member m")
    List<Member> findAllByIdMemberShort();
}
