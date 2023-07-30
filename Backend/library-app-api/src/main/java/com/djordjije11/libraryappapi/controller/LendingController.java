package com.djordjije11.libraryappapi.controller;

import com.djordjije11.libraryappapi.config.authentication.EmployeeClaim;
import com.djordjije11.libraryappapi.config.authentication.EmployeeClaimHolder;
import com.djordjije11.libraryappapi.controller.request.RequestPagingAndSortingParams;
import com.djordjije11.libraryappapi.controller.request.RequestSortingParamsParser;
import com.djordjije11.libraryappapi.controller.request.lending.LendingRequestSortingParser;
import com.djordjije11.libraryappapi.controller.response.ResponseHeadersFactory;
import com.djordjije11.libraryappapi.dto.lending.LendingByMemberDto;
import com.djordjije11.libraryappapi.dto.lending.LendingsCreateDto;
import com.djordjije11.libraryappapi.dto.lending.LendingsReturnDto;
import com.djordjije11.libraryappapi.exception.lending.BookCopyNotAvailableForLendingException;
import com.djordjije11.libraryappapi.exception.lending.BookCopyNotInBuildingForLendingException;
import com.djordjije11.libraryappapi.exception.lending.LendingAlreadyReturnedException;
import com.djordjije11.libraryappapi.exception.lending.LendingReturnedNotByMemberException;
import com.djordjije11.libraryappapi.mapper.lending.LendingMapper;
import com.djordjije11.libraryappapi.model.Lending;
import com.djordjije11.libraryappapi.service.lending.LendingService;
import com.djordjije11.libraryappapi.service.enroll.LendingEnroller;
import jakarta.validation.Valid;
import org.mapstruct.factory.Mappers;
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
    private final LendingEnroller lendingEnroller;
    private final LendingMapper mapper = Mappers.getMapper(LendingMapper.class);
    private final RequestSortingParamsParser sortingParamsParser;
    private final EmployeeClaimHolder employeeClaimHolder;

    public LendingController(LendingService lendingService, LendingEnroller lendingEnroller, LendingRequestSortingParser sortingParamsParser, EmployeeClaimHolder employeeClaimHolder) {
        this.lendingService = lendingService;
        this.lendingEnroller = lendingEnroller;
        this.sortingParamsParser = sortingParamsParser;
        this.employeeClaimHolder = employeeClaimHolder;
    }

    @PutMapping("/return")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> returnLendings(
            @RequestBody @Valid LendingsReturnDto lendingsReturnDto
    ) throws LendingAlreadyReturnedException, LendingReturnedNotByMemberException {
        final EmployeeClaim employeeClaim = employeeClaimHolder.getEmployeeClaim();
        final Long buildingId = employeeClaim.buildingId();
        final List<Lending> lendings = lendingService.returnLendings(lendingsReturnDto.lendingsIds(), lendingsReturnDto.memberId(), buildingId);
        lendingEnroller.enrollReturnedLendings(employeeClaim, lendings, lendingsReturnDto.memberId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createLendings(@RequestBody @Valid LendingsCreateDto lendingsCreateDto) throws BookCopyNotAvailableForLendingException, BookCopyNotInBuildingForLendingException {
        final EmployeeClaim employeeClaim = employeeClaimHolder.getEmployeeClaim();
        final List<Lending> lendings = lendingService.createLendings(lendingsCreateDto.bookCopiesIds(), lendingsCreateDto.memberId(), employeeClaim.buildingId());
        lendingEnroller.enrollLendings(employeeClaim, lendings, lendingsCreateDto.memberId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/member/{memberId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<LendingByMemberDto>> getLendingsByMember(
            @PathVariable Long memberId,
            @Valid RequestPagingAndSortingParams pagingAndSortingParams,
            @RequestParam(required = false) String search
    ) {
        final Page<Lending> page = lendingService.getLendingsByMember(memberId, search, pagingAndSortingParams.createPageable(sortingParamsParser));
        final HttpHeaders httpHeaders = ResponseHeadersFactory.createWithPagination(pagingAndSortingParams.pageNumber(), pagingAndSortingParams.pageSize(), page.getTotalPages(), page.getTotalElements());
        final List<LendingByMemberDto> lendingDtos = page.map(mapper::mapByMember).toList();
        return ResponseEntity.ok().headers(httpHeaders).body(lendingDtos);
    }

    @GetMapping("/member/{memberId}/unreturned")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<LendingByMemberDto>> getUnreturnedLendingsByMember(
            @PathVariable Long memberId,
            @Valid RequestPagingAndSortingParams pagingAndSortingParams,
            @RequestParam(required = false) String search
    ) {
        final Page<Lending> page = lendingService.getUnreturnedLendingsByMember(memberId, search, pagingAndSortingParams.createPageable(sortingParamsParser));
        final HttpHeaders httpHeaders = ResponseHeadersFactory.createWithPagination(pagingAndSortingParams.pageNumber(), pagingAndSortingParams.pageSize(), page.getTotalPages(), page.getTotalElements());
        final List<LendingByMemberDto> lendingDtos = page.map(mapper::mapByMember).toList();
        return ResponseEntity.ok().headers(httpHeaders).body(lendingDtos);
    }
}
