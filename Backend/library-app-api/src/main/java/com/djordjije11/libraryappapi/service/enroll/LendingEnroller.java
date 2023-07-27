package com.djordjije11.libraryappapi.service.enroll;

import com.djordjije11.libraryappapi.config.authentication.EmployeeClaim;
import com.djordjije11.libraryappapi.model.Lending;

import java.util.List;

public interface LendingEnroller {
    void enrollLendings(EmployeeClaim employeeClaim, List<Lending> lendings, Long memberId);
    void enrollReturnedLendings(EmployeeClaim employeeClaim, List<Lending> lendings, Long memberId);
}
