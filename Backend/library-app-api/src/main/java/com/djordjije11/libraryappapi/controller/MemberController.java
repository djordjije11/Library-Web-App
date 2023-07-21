package com.djordjije11.libraryappapi.controller;

import com.djordjije11.libraryappapi.controller.request.RequestParamParser;
import com.djordjije11.libraryappapi.controller.response.ResponseHeaders;
import com.djordjije11.libraryappapi.dto.member.MemberUpdateDto;
import com.djordjije11.libraryappapi.exception.parser.SortDirectionNotValidException;
import com.djordjije11.libraryappapi.exception.parser.SortQueryNotValidException;
import com.djordjije11.libraryappapi.mapper.member.MemberMapper;
import com.djordjije11.libraryappapi.dto.member.MemberCreateDto;
import com.djordjije11.libraryappapi.dto.member.MemberDto;
import com.djordjije11.libraryappapi.dto.member.MemberShortDto;
import com.djordjije11.libraryappapi.exception.RequestNotValidException;
import com.djordjije11.libraryappapi.model.Member;
import com.djordjije11.libraryappapi.service.member.MemberService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final RequestParamParser requestParamParser;

    public MemberController(MemberService memberService, MemberMapper mapper, RequestParamParser requestParamParser) {
        this.memberService = memberService;
        this.mapper = mapper;
        this.requestParamParser = requestParamParser;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<MemberShortDto>> get(
            @RequestParam(name = RequestParamParser.PAGE_NUMBER, defaultValue = "1") Integer pageNumber,
            @RequestParam(name = RequestParamParser.PAGE_SIZE, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, name = RequestParamParser.SORT) String sortBy
    ) throws SortQueryNotValidException, SortDirectionNotValidException {
        Pageable pageable = requestParamParser.createPageable(pageNumber, pageSize, sortBy);
        Page<Member> page = memberService.get(pageable);
        HttpHeaders httpHeaders = ResponseHeaders.createWithPagination(pageNumber, pageSize, page.getTotalPages(), page.getTotalElements());
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
