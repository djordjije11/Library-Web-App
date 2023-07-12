package com.djordjije11.libraryappapi.controller;

import com.djordjije11.libraryappapi.model.Member;
import com.djordjije11.libraryappapi.repository.member.MemberReadonlyRepository;
import com.djordjije11.libraryappapi.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/member")
public class MemberController {
    private final MemberReadonlyRepository memberReadonlyRepository;
    private final MemberService memberService;

    public MemberController(MemberReadonlyRepository memberReadonlyRepository, MemberService memberService) {
        this.memberReadonlyRepository = memberReadonlyRepository;
        this.memberService = memberService;
    }

    @GetMapping
    public List<Member> getAll(){
        return memberReadonlyRepository.findAll();
    }

    @GetMapping("/{id}")
    public Member getById(@PathVariable Long id){
        return memberReadonlyRepository.findById(id).orElseThrow();
    }

    @PostMapping
    public void save(@RequestBody Member member){
        memberService.save(member);
    }
}
