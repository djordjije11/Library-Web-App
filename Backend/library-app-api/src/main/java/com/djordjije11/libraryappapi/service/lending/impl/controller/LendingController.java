package com.djordjije11.libraryappapi.service.lending.impl.controller;

import com.djordjije11.libraryappapi.dto.lending.LendingDto;
import com.djordjije11.libraryappapi.dto.lending.LendingsCreateDto;
import com.djordjije11.libraryappapi.dto.lending.LendingsReturnDto;
import com.djordjije11.libraryappapi.exception.lending.LendingAlreadyReturnedException;
import com.djordjije11.libraryappapi.mapper.lending.LendingMapper;
import com.djordjije11.libraryappapi.service.lending.LendingService;
import org.apache.coyote.Response;
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
    public ResponseEntity<Object> createLendings(@RequestBody LendingsCreateDto lendingsCreateDto){
        lendingService.createLendings(lendingsCreateDto.memberId(), lendingsCreateDto.bookCopiesIds());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/member/{memberId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<LendingDto>> getLendingsByMember(@PathVariable Long memberId){
        return ResponseEntity.ok(lendingService.getLendingsByMember(memberId).stream().map(mapper::map).toList());
    }

    @GetMapping("/member/{memberId}/unreturned")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<LendingDto>> getUnreturnedLendingsByMember(@PathVariable Long memberId){
        return ResponseEntity.ok(lendingService.getUnreturnedLendingsByMember(memberId).stream().map(mapper::map).toList());
    }
}
