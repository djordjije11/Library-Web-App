package com.djordjije11.libraryappapi.controller;

import com.djordjije11.libraryappapi.controller.request.RequestPagingAndSortingParams;
import com.djordjije11.libraryappapi.controller.request.RequestSortingParamsParser;
import com.djordjije11.libraryappapi.controller.request.publisher.PublisherRequestSortingParamsParser;
import com.djordjije11.libraryappapi.controller.response.ResponseHeadersFactory;
import com.djordjije11.libraryappapi.dto.publisher.PublisherShortDto;
import com.djordjije11.libraryappapi.mapper.publisher.PublisherMapper;
import com.djordjije11.libraryappapi.model.Publisher;
import com.djordjije11.libraryappapi.service.publisher.PublisherService;
import jakarta.validation.Valid;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publisher")
public class PublisherController {
    private final PublisherService publisherService;
    private final RequestSortingParamsParser sortingParamsParser;
    private final PublisherMapper publisherMapper = Mappers.getMapper(PublisherMapper.class);

    public PublisherController(PublisherService publisherService, PublisherRequestSortingParamsParser sortingParamsParser) {
        this.publisherService = publisherService;
        this.sortingParamsParser = sortingParamsParser;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PublisherShortDto>> get(
            @Valid RequestPagingAndSortingParams pagingAndSortingParams,
            @RequestParam(required = false) String search
    ) {
        Page<Publisher> page = publisherService.get(search, pagingAndSortingParams.createPageable(sortingParamsParser));
        HttpHeaders httpHeaders = ResponseHeadersFactory.createWithPagination(pagingAndSortingParams.pageNumber(), pagingAndSortingParams.pageSize(), page.getTotalPages(), page.getTotalElements());
        List<PublisherShortDto> publisherDtos = page.map(publisherMapper::mapShort).toList();
        return ResponseEntity.ok().headers(httpHeaders).body(publisherDtos);
    }
}
