package com.djordjije11.libraryappapi.controller.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public record RequestPagingAndSortingParams(
        @Min(value = 1, message = "A page number must not be lower than 1.")
        Integer pageNumber,
        @Min(value = 1, message = "A page size must not be lower than 1.")
        @Max(value = 100, message = "Max page size (entries per page) is 100.")
        Integer pageSize,
        String sortBy
) {
    private static final int pageNumberDefault = 1;
    private static final int pageSizeDefault = 5;

    @Override
    public Integer pageNumber() {
        if (pageNumber == null) {
            return pageNumberDefault;
        }
        return pageNumber;
    }

    @Override
    public Integer pageSize() {
        if (pageSize == null) {
            return pageSizeDefault;
        }
        return pageSize;
    }

    public Pageable createPageable(RequestSortingParamsParser sortingParamsParser) {
        return PageRequest.of(pageNumber() - 1, pageSize(), sortingParamsParser.parseSort(sortBy()));
    }
}
