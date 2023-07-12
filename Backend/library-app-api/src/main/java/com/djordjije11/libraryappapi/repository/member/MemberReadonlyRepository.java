package com.djordjije11.libraryappapi.repository.member;

import com.djordjije11.libraryappapi.model.Member;
import com.djordjije11.libraryappapi.repository.ReadonlyRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberReadonlyRepository extends ReadonlyRepository<Member, Long> {
}
