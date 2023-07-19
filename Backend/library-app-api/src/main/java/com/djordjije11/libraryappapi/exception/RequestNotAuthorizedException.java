package com.djordjije11.libraryappapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class RequestNotAuthorizedException extends Exception {
    public RequestNotAuthorizedException() {
        super("You are not authorized for requested action.");
    }
    public RequestNotAuthorizedException(String message) {
        super(message);
    }
}
