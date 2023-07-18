package com.djordjije11.libraryappapi.exception.book;

public class BookCopyIsbnNotUniqueException extends Exception {
    public BookCopyIsbnNotUniqueException() {
    }

    public BookCopyIsbnNotUniqueException(String isbn) {
        super(String.format("A BookCopy with Isbn: %s already exists.", isbn));
    }
}
