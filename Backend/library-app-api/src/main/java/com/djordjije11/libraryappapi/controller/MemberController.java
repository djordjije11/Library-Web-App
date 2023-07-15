package com.djordjije11.libraryappapi.controller;

import com.djordjije11.libraryappapi.dto.mapper.EntityMapper;
import com.djordjije11.libraryappapi.dto.member.MemberDto;
import com.djordjije11.libraryappapi.dto.member.SaveMemberDto;
import com.djordjije11.libraryappapi.exception.RecordNotFoundException;
import com.djordjije11.libraryappapi.repository.member.MemberReadonlyRepository;
import com.djordjije11.libraryappapi.service.member.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/member")
public class MemberController {
    private final MemberReadonlyRepository memberReadonlyRepository;
    private final MemberService memberService;
    private final EntityMapper mapper;

    public MemberController(MemberReadonlyRepository memberReadonlyRepository, MemberService memberService, EntityMapper mapper) {
        this.memberReadonlyRepository = memberReadonlyRepository;
        this.memberService = memberService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<MemberDto>> getAll(){
        var memberDtos = memberReadonlyRepository.findAll().stream().map(mapper::map).toList();
        return ResponseEntity.ok(memberDtos);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MemberDto> getById(@PathVariable Long id){
        var member = memberReadonlyRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Member with Id: %d is not found.", id)));
        return ResponseEntity.ok(mapper.map(member));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MemberDto> save(@Valid @RequestBody SaveMemberDto memberDto){
        var member = memberService.save(mapper.map(memberDto));
        return new ResponseEntity<>(mapper.map(member), HttpStatus.CREATED);
    }
}
