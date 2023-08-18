package com.djordjije11.libraryappapi.exception.book;

import com.djordjije11.libraryappapi.exception.RequestNotValidException;

public class BookCopyIsbnNotUniqueException extends RequestNotValidException {
    public BookCopyIsbnNotUniqueException() {
    }

    public BookCopyIsbnNotUniqueException(String isbn) {
        super(String.format("A BookCopy with Isbn: %s already exists.", isbn));
    }
}
