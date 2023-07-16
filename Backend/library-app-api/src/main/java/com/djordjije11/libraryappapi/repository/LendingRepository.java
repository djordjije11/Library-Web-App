package com.djordjije11.libraryappapi.repository;

import com.djordjije11.libraryappapi.model.Lending;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LendingRepository extends JpaRepository<Lending, Long> {
    boolean existsByMemberId(long memberId);
}
