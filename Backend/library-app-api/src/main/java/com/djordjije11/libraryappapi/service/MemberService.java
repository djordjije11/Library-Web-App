package com.djordjije11.libraryappapi.service;

import com.djordjije11.libraryappapi.model.Member;
import com.djordjije11.libraryappapi.repository.member.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void save(Member member){
        memberRepository.save(member);
    }
}
