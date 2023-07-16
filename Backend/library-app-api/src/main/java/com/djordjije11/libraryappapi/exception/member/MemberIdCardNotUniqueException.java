package com.djordjije11.libraryappapi.exception.member;

import com.djordjije11.libraryappapi.exception.RequestNotValidException;

public class MemberIdCardNotUniqueException extends RequestNotValidException {
    public MemberIdCardNotUniqueException() {
    }
    public MemberIdCardNotUniqueException(String idCardNumber){
        super(String.format("A member with idCardNumber: %s already exists.", idCardNumber));
    }
}
