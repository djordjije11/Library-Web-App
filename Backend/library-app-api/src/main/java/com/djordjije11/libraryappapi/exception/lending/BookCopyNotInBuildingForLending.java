package com.djordjije11.libraryappapi.exception.lending;

import com.djordjije11.libraryappapi.exception.RequestNotValidException;

public class BookCopyNotInBuildingForLending extends RequestNotValidException {
    public BookCopyNotInBuildingForLending(){
        super("A book copy is not in the building to be lent.");
    }
    public BookCopyNotInBuildingForLending(Long bookCopyId, Long buildingId){
        super(String.format("A book copy with Id: %d is not in the building with Id: %d to be lent.", bookCopyId, buildingId));
    }
}
