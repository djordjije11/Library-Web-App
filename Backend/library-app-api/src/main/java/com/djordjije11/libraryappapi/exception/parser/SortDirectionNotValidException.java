package com.djordjije11.libraryappapi.exception.parser;

import com.djordjije11.libraryappapi.exception.RecordNotValidException;

public class SortDirectionNotValidException extends RecordNotValidException {
    public SortDirectionNotValidException() {
        super("A sort direction cannot be any other value than 'asc' or 'desc'.");
    }

    public SortDirectionNotValidException(String direction) {
        super(String.format("A sort direction: %s is not valid. A sort direction cannot be any other value than 'asc' or 'desc'.", direction));
    }
}
