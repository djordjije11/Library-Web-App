package com.djordjije11.libraryappapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RecordNotValidException extends RuntimeException {
    public RecordNotValidException() {
    }
    public RecordNotValidException(String message) {
        super(message);
    }
}
