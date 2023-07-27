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

    @Override
    public String toString() {
        return String.format("Employee %s %s, email: %s", firstname, lastname, email) +
                System.lineSeparator() +
                String.format("ID: %d, ID card number: %s", id, idCardNumber) +
                System.lineSeparator() +
                String.format("UserProfile ID: %d, Building ID: %d", userProfileId, buildingId);
    }
}
