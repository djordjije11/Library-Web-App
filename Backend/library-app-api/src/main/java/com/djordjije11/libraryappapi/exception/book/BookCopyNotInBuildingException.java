package com.djordjije11.libraryappapi.exception.book;

import com.djordjije11.libraryappapi.exception.RequestNotAuthorizedException;

public class BookCopyNotInBuildingException extends RequestNotAuthorizedException {
    public BookCopyNotInBuildingException() {
    }
    public BookCopyNotInBuildingException(Long id) {
        super(String.format("Unauthorized request. A BookCopy with Id: %d is not in your building.", id));
    }
    public BookCopyNotInBuildingException(String message) {
        super(message);
    }
}
