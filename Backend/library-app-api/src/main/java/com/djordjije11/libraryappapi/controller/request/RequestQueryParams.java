package com.djordjije11.libraryappapi.controller.request;

import com.djordjije11.libraryappapi.helper.string.util.StringExt;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import jakarta.validation.constraints.*;

public record RequestQueryParams(
        @Min(value = 1, message = "A page number must not be lower than 1.")
        Integer pageNumber,
        @Min(value = 1, message = "A page size must not be lower than 1.")
        @Max(value = 100, message = "Max page size (entries per page) is 100.")
        Integer pageSize,
        String sortBy,
        String search
) {
    private static final int pageNumberDefault = 1;
    private static final int pageSizeDefault = 5;
    private static final String searchDefault = StringExt.EMPTY;

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

    @Override
    public String search() {
        if (search == null) {
            return searchDefault;
        }
        return search;
    }

    public Pageable createPageable() {
        return PageRequest.of(pageNumber() - 1, pageSize(), RequestParamParser.parseSort(sortBy()));
    }
}