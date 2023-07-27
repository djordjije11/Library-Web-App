package com.djordjije11.libraryappapi.config.authentication;

public class EmployeeClaimHolder {
    private EmployeeClaim employeeClaim;

    public EmployeeClaimHolder() {
    }

    public EmployeeClaim getEmployeeClaim() {
        return employeeClaim;
    }

    public void setEmployeeClaim(EmployeeClaim employeeClaim) {
        this.employeeClaim = employeeClaim;
    }
}
