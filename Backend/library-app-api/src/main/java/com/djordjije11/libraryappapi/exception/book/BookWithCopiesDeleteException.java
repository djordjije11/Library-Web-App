package com.djordjije11.libraryappapi.exception.book;

import com.djordjije11.libraryappapi.exception.RequestNotValidException;

public class BookWithCopiesDeleteException extends RequestNotValidException {
    public BookWithCopiesDeleteException() {
        super("A book with copies cannot be deleted.");
    }
}
