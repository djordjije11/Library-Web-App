package com.djordjije11.libraryappapi.service.member.impl;

import com.djordjije11.libraryappapi.model.Member;
import com.djordjije11.libraryappapi.repository.member.MemberRepository;
import com.djordjije11.libraryappapi.service.member.MemberService;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member save(Member member){
        return memberRepository.save(member);
    }
}
