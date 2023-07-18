package com.djordjije11.libraryappapi.repository;

import com.djordjije11.libraryappapi.model.Lending;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LendingRepository extends JpaRepository<Lending, Long> {
    boolean existsByMemberId(Long memberId);
    List<Lending> findAllByMember_Id(Long memberId);
    List<Lending> findAllByMember_IdAndReturnDateIsNull(Long memberId);
}
