package com.djordjije11.libraryappapi.controller.request;

import com.djordjije11.libraryappapi.exception.parser.SortDirectionNotValidException;
import com.djordjije11.libraryappapi.exception.parser.SortQueryNotValidException;
import com.djordjije11.libraryappapi.helper.string.util.StringExt;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON)
public final class RequestParamParser {
    public static final String PAGE_NUMBER = "page_number";
    public static final String PAGE_SIZE = "page_size";
    public static final String SORT = "sort_by";
    private static final String SORT_QUERY_SPLIT_REGEX = ",";


    public Pageable createPageable(int pageNumber, int pageSize, String sortBy) throws SortQueryNotValidException, SortDirectionNotValidException {
        return PageRequest.of(pageNumber - 1, pageSize, parseSort(sortBy));
    }

    public Sort parseSort(String sortQuery) throws SortQueryNotValidException, SortDirectionNotValidException {
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

    private Sort parseOneSortOrder(String sortText) throws SortQueryNotValidException, SortDirectionNotValidException {
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

    private Sort.Direction parseSortDirection(String sortDirection) throws SortDirectionNotValidException {
        try {
            return Sort.Direction.fromString(sortDirection);
        } catch (IllegalArgumentException ex) {
            throw new SortDirectionNotValidException(sortDirection);
        }
    }
}
