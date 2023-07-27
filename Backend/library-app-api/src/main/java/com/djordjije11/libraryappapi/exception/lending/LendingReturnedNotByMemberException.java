package com.djordjije11.libraryappapi.exception.lending;

import com.djordjije11.libraryappapi.exception.RequestNotValidException;

public class LendingReturnedNotByMemberException extends RequestNotValidException {
    public LendingReturnedNotByMemberException(){
        super("A lending is not by member.");
    }
    public LendingReturnedNotByMemberException(Long lendingId, Long memberId){
        super(String.format("A lending with Id: %d is not by member with Id: %d.", lendingId, memberId));
    }
}
