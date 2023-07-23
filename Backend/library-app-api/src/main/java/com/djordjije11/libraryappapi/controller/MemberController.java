package com.djordjije11.libraryappapi.controller;

import com.djordjije11.libraryappapi.controller.request.RequestPagingAndSortingParams;
import com.djordjije11.libraryappapi.service.book.specification.member.MembersSpecification;
import com.djordjije11.libraryappapi.controller.response.ResponseHeadersFactory;
import com.djordjije11.libraryappapi.dto.member.MemberUpdateDto;
import com.djordjije11.libraryappapi.mapper.member.MemberMapper;
import com.djordjije11.libraryappapi.dto.member.MemberCreateDto;
import com.djordjije11.libraryappapi.dto.member.MemberDto;
import com.djordjije11.libraryappapi.dto.member.MemberShortDto;
import com.djordjije11.libraryappapi.exception.RequestNotValidException;
import com.djordjije11.libraryappapi.model.Member;
import com.djordjije11.libraryappapi.service.member.MemberService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/member")
public class MemberController {
    private final MemberService memberService;
    private final MemberMapper mapper;

    public MemberController(MemberService memberService, MemberMapper mapper) {
        this.memberService = memberService;
        this.mapper = mapper;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<MemberShortDto>> get(
            @Valid RequestPagingAndSortingParams pagingAndSortingParams,
            @RequestParam(required = false) String search
    ) {
        Page<Member> page = memberService.get(MembersSpecification.create(search), pagingAndSortingParams.createPageable());
        HttpHeaders httpHeaders = ResponseHeadersFactory.createWithPagination(pagingAndSortingParams.pageNumber(), pagingAndSortingParams.pageSize(), page.getTotalPages(), page.getTotalElements());
        List<MemberShortDto> memberDtos = page.map(mapper::mapShort).toList();
        return ResponseEntity.ok().headers(httpHeaders).body(memberDtos);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MemberDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.mapDetail(memberService.get(id)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MemberDto> create(@Valid @RequestBody MemberCreateDto memberDto) throws RequestNotValidException {
        var member = memberService.create(mapper.map(memberDto));
        return new ResponseEntity<>(mapper.mapDetail(member), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MemberDto> update(
            @PathVariable Long id,
            @Valid @RequestBody MemberUpdateDto memberDto
    ) throws RequestNotValidException {
        var member = mapper.map(memberDto);
        member.setId(id);
        var dbMember = memberService.update(member);
        return ResponseEntity.ok(mapper.mapDetail(dbMember));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> delete(@PathVariable Long id) throws RequestNotValidException {
        memberService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
