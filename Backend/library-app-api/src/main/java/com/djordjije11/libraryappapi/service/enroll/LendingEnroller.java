package com.djordjije11.libraryappapi.service.enroll;

import com.djordjije11.libraryappapi.config.authentication.AuthClaimsHolder;
import com.djordjije11.libraryappapi.config.authentication.claim.EmployeeClaim;
import com.djordjije11.libraryappapi.model.Lending;

import java.util.List;

public interface LendingEnroller {
    void enrollLendings(AuthClaimsHolder authClaimsHolder, List<Lending> lendings, Long memberId);
    void enrollReturnedLendings(AuthClaimsHolder authClaimsHolder, List<Lending> lendings, Long memberId);
}
