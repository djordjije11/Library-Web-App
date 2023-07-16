package com.djordjije11.libraryappapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException() {
    }
    public RecordNotFoundException(long id) {
        super(String.format("Resource with Id: %d is not found.", id));
    }
    public RecordNotFoundException(String message) {
        super(message);
    }
}
