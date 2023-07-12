package com.djordjije11.libraryappapi.repository.member;

import com.djordjije11.libraryappapi.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
