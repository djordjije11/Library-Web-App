package com.djordjije11.libraryappapi.exception.lending;

import com.djordjije11.libraryappapi.exception.RequestNotValidException;

public class BookCopyNotAvailableForLendingException extends RequestNotValidException {
    public BookCopyNotAvailableForLendingException(){
        super("A book copy is not available to be lent.");
    }
    public BookCopyNotAvailableForLendingException(Long id){
        super(String.format("A book copy with Id: %d is not available to be lent.", id));
    }
}
