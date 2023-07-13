package com.djordjije11.libraryappapi.controller;

import com.djordjije11.libraryappapi.exception.RecordNotFoundException;
import com.djordjije11.libraryappapi.model.Member;
import com.djordjije11.libraryappapi.repository.member.MemberReadonlyRepository;
import com.djordjije11.libraryappapi.service.member.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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
    @ResponseStatus(HttpStatus.OK)
    public Member getById(@PathVariable Long id){
        return memberReadonlyRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Member not found."));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody Member member){
        memberService.save(member);
    }
}
