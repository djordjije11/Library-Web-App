package com.djordjije11.libraryappapi.controller.request;

import com.djordjije11.libraryappapi.exception.parser.SortDirectionNotValidException;
import com.djordjije11.libraryappapi.exception.parser.SortQueryNotValidException;
import com.djordjije11.libraryappapi.helper.string.util.StringExt;
import org.springframework.data.domain.Sort;

public final class RequestSortingParamsParser {
    private static final String SORT_QUERY_SPLIT_REGEX = ",";

    public static Sort parseSort(String sortQuery) {
        Sort sort = Sort.unsorted();
        if (StringExt.isNullOrBlank(sortQuery)) {
            return sort;
        }
        String[] sortTexts = sortQuery.split(SORT_QUERY_SPLIT_REGEX);
        for (String sortText :
                sortTexts) {
            try {
                sort = sort.and(parseOneSortOrder(sortText));
            } catch (SortQueryNotValidException ex) {
                throw new SortQueryNotValidException(sortQuery);
            }
        }
        return sort;
    }

    private static Sort parseOneSortOrder(String sortText) {
        int openParenthesisIndex = sortText.indexOf('(');
        if (openParenthesisIndex < 0) {
            throw new SortQueryNotValidException();
        }
        int closedParenthesisIndex = sortText.indexOf(')');
        if (closedParenthesisIndex < 0) {
            throw new SortQueryNotValidException();
        }
        String sortDirectionText = sortText.substring(0, openParenthesisIndex);
        Sort.Direction sortDirection = parseSortDirection(sortDirectionText);
        String sortProperty = sortText.substring(openParenthesisIndex + 1, closedParenthesisIndex);
        return Sort.by(sortDirection, sortProperty);
    }

    private static Sort.Direction parseSortDirection(String sortDirection) {
        try {
            return Sort.Direction.fromString(sortDirection);
        } catch (IllegalArgumentException ex) {
            throw new SortDirectionNotValidException(sortDirection);
        }
    }
}
