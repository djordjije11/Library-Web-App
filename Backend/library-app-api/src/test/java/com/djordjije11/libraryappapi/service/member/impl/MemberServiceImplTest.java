package com.djordjije11.libraryappapi.service.member.impl;

import com.djordjije11.libraryappapi.service.member.MemberServiceTest;
import org.junit.jupiter.api.BeforeEach;

public class MemberServiceImplTest extends MemberServiceTest {

    @BeforeEach
    public void init(){
        memberService = new MemberServiceImpl(memberRepository, lendingRepository);
    }
}