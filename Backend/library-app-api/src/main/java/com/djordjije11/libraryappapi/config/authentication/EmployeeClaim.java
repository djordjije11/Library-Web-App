package com.djordjije11.libraryappapi.config.authentication;

public record EmployeeClaim(
        Long id,
        String idCardNumber,
        String firstname,
        String lastname,
        String email,
        Long buildingId,
        Long userProfileId
) {
}
