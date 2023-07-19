package com.djordjije11.libraryappapi.service.member;

import com.djordjije11.libraryappapi.exception.RecordNotFoundException;
import com.djordjije11.libraryappapi.exception.RequestNotValidException;
import com.djordjije11.libraryappapi.exception.member.MemberIdCardNotUniqueException;
import com.djordjije11.libraryappapi.exception.member.MemberWithLendingsDeleteException;
import com.djordjije11.libraryappapi.model.Member;
import java.util.List;
import java.util.Optional;

public interface MemberService {
    Member create(Member member) throws MemberIdCardNotUniqueException;
    Member get(Long id);
    void delete(Long id) throws MemberWithLendingsDeleteException;
    Member update(Member member) throws RequestNotValidException, RecordNotFoundException;

    List<Member> getAll();
}
