package com.djordjije11.libraryappapi.controller.response;

import org.springframework.http.HttpHeaders;

public final class ResponseHeaders {
    public static final String PAGINATION_CURRENT_PAGE = "X-Pagination-Current-Page";
    public static final String PAGINATION_PER_PAGE = "X-Pagination-Per-Page";
    public static final String PAGINATION_TOTAL_PAGES = "X-Pagination-Total-Pages";
    public static final String PAGINATION_TOTAL_ENTRIES = "X-Pagination-Total-Entries";

    private ResponseHeaders() {
    }

    public static HttpHeaders createWithPagination(int currentPage, int perPage, int totalPages, long totalEntries) {
        var httpHeaders = new HttpHeaders();
        return addPagination(httpHeaders, currentPage, perPage, totalPages, totalEntries);
    }

    public static HttpHeaders addPagination(HttpHeaders httpHeaders, int currentPage, int perPage, int totalPages, long totalEntries) {
        httpHeaders.add(ResponseHeaders.PAGINATION_PER_PAGE, String.valueOf(perPage));
        httpHeaders.add(ResponseHeaders.PAGINATION_CURRENT_PAGE, String.valueOf(currentPage));
        httpHeaders.add(ResponseHeaders.PAGINATION_TOTAL_PAGES, String.valueOf(totalPages));
        httpHeaders.add(ResponseHeaders.PAGINATION_TOTAL_ENTRIES, String.valueOf(totalEntries));
        return httpHeaders;
    }
}
