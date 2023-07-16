package com.djordjije11.libraryappapi.controller;

import com.djordjije11.libraryappapi.dto.mapper.EntityMapper;
import com.djordjije11.libraryappapi.dto.member.CreateMemberDto;
import com.djordjije11.libraryappapi.dto.member.MemberDto;
import com.djordjije11.libraryappapi.exception.RecordNotFoundException;
import com.djordjije11.libraryappapi.exception.RequestNotValidException;
import com.djordjije11.libraryappapi.service.member.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/member")
public class MemberController {
    private final MemberService memberService;
    private final EntityMapper mapper;

    public MemberController(MemberService memberService, EntityMapper mapper) {
        this.memberService = memberService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<MemberDto>> getAll() {
        var memberDtos = memberService.getAll().stream().map(mapper::map).toList();
        return ResponseEntity.ok(memberDtos);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MemberDto> getById(@PathVariable Long id) {
        var member = memberService.get(id)
                .orElseThrow(() -> new RecordNotFoundException(id));
        return ResponseEntity.ok(mapper.map(member));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MemberDto> create(@Valid @RequestBody CreateMemberDto memberDto) throws RequestNotValidException {
        var member = memberService.create(mapper.map(memberDto));
        return new ResponseEntity<>(mapper.map(member), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MemberDto> update(@PathVariable Long id, @Valid @RequestBody MemberDto memberDto) throws RequestNotValidException {
        var member = mapper.map(memberDto);
        member.setId(id);
        var dbMember = memberService.update(member);
        return new ResponseEntity<>(mapper.map(dbMember), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> delete(@PathVariable Long id) throws RequestNotValidException {
        memberService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
