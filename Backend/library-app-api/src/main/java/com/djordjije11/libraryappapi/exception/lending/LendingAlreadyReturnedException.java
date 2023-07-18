package com.djordjije11.libraryappapi.exception.lending;

import com.djordjije11.libraryappapi.exception.RequestNotValidException;

public class LendingAlreadyReturnedException extends RequestNotValidException {
    public LendingAlreadyReturnedException(Long id) {
        super(String.format("A Lending with Id: %s is already returned.", id));
    }
    public LendingAlreadyReturnedException(String message) {
        super(message);
    }
}
