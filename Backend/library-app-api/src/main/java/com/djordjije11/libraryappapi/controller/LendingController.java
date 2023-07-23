package com.djordjije11.libraryappapi.controller;

import com.djordjije11.libraryappapi.controller.request.RequestPagingAndSortingParams;
import com.djordjije11.libraryappapi.service.book.specification.lending.LendingsByMemberSpecification;
import com.djordjije11.libraryappapi.service.book.specification.lending.LendingsByMemberUnreturnedSpecification;
import com.djordjije11.libraryappapi.controller.response.ResponseHeadersFactory;
import com.djordjije11.libraryappapi.dto.lending.LendingByMemberDto;
import com.djordjije11.libraryappapi.dto.lending.LendingsCreateDto;
import com.djordjije11.libraryappapi.dto.lending.LendingsReturnDto;
import com.djordjije11.libraryappapi.exception.lending.LendingAlreadyReturnedException;
import com.djordjije11.libraryappapi.mapper.lending.LendingMapper;
import com.djordjije11.libraryappapi.model.Lending;
import com.djordjije11.libraryappapi.service.lending.LendingService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lending")
public class LendingController {
    private final LendingService lendingService;
    private final LendingMapper mapper;

    public LendingController(LendingService lendingService, LendingMapper mapper) {
        this.lendingService = lendingService;
        this.mapper = mapper;
    }

    @PutMapping("/return")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> returnLendings(@RequestBody LendingsReturnDto lendingsReturnDto) throws LendingAlreadyReturnedException {
        // TODO: 7/18/2023 Long buildingId = ...
        Long buildingId = 1L;
        lendingService.returnLendings(lendingsReturnDto.lendingsIds(), buildingId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createLendings(@RequestBody LendingsCreateDto lendingsCreateDto) {
        lendingService.createLendings(lendingsCreateDto.memberId(), lendingsCreateDto.bookCopiesIds());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/member/{memberId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<LendingByMemberDto>> getLendingsByMember(
            @PathVariable Long memberId,
            @Valid RequestPagingAndSortingParams pagingAndSortingParams,
            @RequestParam(required = false) String search
    ) {
        Page<Lending> page = lendingService.getLendings(LendingsByMemberSpecification.create(memberId, search), pagingAndSortingParams.createPageable());
        HttpHeaders httpHeaders = ResponseHeadersFactory.createWithPagination(pagingAndSortingParams.pageNumber(), pagingAndSortingParams.pageSize(), page.getTotalPages(), page.getTotalElements());
        List<LendingByMemberDto> lendingDtos = page.map(mapper::mapByMember).toList();
        return ResponseEntity.ok().headers(httpHeaders).body(lendingDtos);
    }

    @GetMapping("/member/{memberId}/unreturned")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<LendingByMemberDto>> getUnreturnedLendingsByMember(
            @PathVariable Long memberId,
            @Valid RequestPagingAndSortingParams pagingAndSortingParams,
            @RequestParam(required = false) String search
    ) {
        Page<Lending> page = lendingService.getLendings(LendingsByMemberUnreturnedSpecification.create(memberId, search), pagingAndSortingParams.createPageable());
        HttpHeaders httpHeaders = ResponseHeadersFactory.createWithPagination(pagingAndSortingParams.pageNumber(), pagingAndSortingParams.pageSize(), page.getTotalPages(), page.getTotalElements());
        List<LendingByMemberDto> lendingDtos = page.map(mapper::mapByMember).toList();
        return ResponseEntity.ok().headers(httpHeaders).body(lendingDtos);
    }
}
