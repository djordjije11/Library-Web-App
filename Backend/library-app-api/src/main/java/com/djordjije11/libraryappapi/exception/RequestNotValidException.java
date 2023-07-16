package com.djordjije11.libraryappapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequestNotValidException extends Exception {
    public RequestNotValidException() {
        super("Request is not valid.");
    }
    public RequestNotValidException(String message) {
        super(message);
    }
}
