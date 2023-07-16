package com.djordjije11.libraryappapi.exception.member;

import com.djordjije11.libraryappapi.exception.RequestNotValidException;

public class MemberWithLendingsDeleteException extends RequestNotValidException {
    public MemberWithLendingsDeleteException() {
        super("A member with a history of lendings cannot be deleted.");
    }
}
