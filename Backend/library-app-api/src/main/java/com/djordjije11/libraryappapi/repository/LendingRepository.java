package com.djordjije11.libraryappapi.repository;

import com.djordjije11.libraryappapi.model.Lending;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LendingRepository extends JpaRepository<Lending, Long>, JpaSpecificationExecutor<Lending> {
    boolean existsByMemberId(Long memberId);
}
