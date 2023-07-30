package com.djordjije11.libraryappapi.exception.lending;

import com.djordjije11.libraryappapi.exception.RequestNotValidException;

public class BookCopyNotInBuildingForLendingException extends RequestNotValidException {
    public BookCopyNotInBuildingForLendingException(){
        super("A book copy is not in the building to be lent.");
    }
    public BookCopyNotInBuildingForLendingException(Long bookCopyId, Long buildingId){
        super(String.format("A book copy with Id: %d is not in the building with Id: %d to be lent.", bookCopyId, buildingId));
    }
}
