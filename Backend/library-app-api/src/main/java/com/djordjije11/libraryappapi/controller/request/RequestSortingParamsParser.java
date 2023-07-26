package com.djordjije11.libraryappapi.controller.request;

import com.djordjije11.libraryappapi.exception.sort.SortDirectionNotValidException;
import com.djordjije11.libraryappapi.exception.sort.SortPropertyNotValidException;
import com.djordjije11.libraryappapi.exception.sort.SortQueryNotValidException;
import com.djordjije11.libraryappapi.helper.string.util.StringExt;
import org.springframework.data.domain.Sort;

import java.util.Collection;

public abstract class RequestSortingParamsParser {
    private final String SORT_QUERY_SPLIT_REGEX = ",";

    protected abstract Collection<String> getSortProperties();

    public Sort parseSort(String sortQuery) {
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
            } catch (SortPropertyNotValidException ex) {
                throw new SortPropertyNotValidException(sortQuery, ex.getProperty());
            }
        }
        return sort;
    }

    protected String formatNestedObjectProperty(String nestedObject, String nestedObjectProperty) {
        return String.format("%s.%s", nestedObject, nestedObjectProperty);
    }

    protected String formatNestedObjectProperty(String... nestedProperties){
        if(nestedProperties == null){
            return null;
        }
        String result = nestedProperties[0];
        for (int i = 1; i < nestedProperties.length; i++){
            result = formatNestedObjectProperty(result, nestedProperties[i]);
        }
        return result;
    }

    private boolean sortPropertyExists(String property) {
        return getSortProperties().contains(property);
    }

    private Sort parseOneSortOrder(String sortText) {
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
        if (sortPropertyExists(sortProperty) == false) {
            throw new SortPropertyNotValidException(sortProperty);
        }
        return Sort.by(sortDirection, sortProperty);
    }

    private Sort.Direction parseSortDirection(String sortDirection) {
        try {
            return Sort.Direction.fromString(sortDirection);
        } catch (IllegalArgumentException ex) {
            throw new SortDirectionNotValidException(sortDirection);
        }
    }
}
