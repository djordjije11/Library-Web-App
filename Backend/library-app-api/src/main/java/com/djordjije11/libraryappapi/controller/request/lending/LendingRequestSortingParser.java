package com.djordjije11.libraryappapi.controller.request.lending;

import com.djordjije11.libraryappapi.controller.request.RequestSortingParamsParser;
import com.djordjije11.libraryappapi.model.*;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

@Component
@Scope(scopeName = BeanDefinition.SCOPE_SINGLETON)
public class LendingRequestSortingParser extends RequestSortingParamsParser {
    private final Collection<String> properties;

    public LendingRequestSortingParser() {
        properties = Arrays.asList(
                Lending_.ID,
                Lending_.LENDING_DATE,
                Lending_.RETURN_DATE,
                formatNestedObjectProperty(Lending_.MEMBER, Member_.ID_CARD_NUMBER),
                formatNestedObjectProperty(Lending_.MEMBER, Member_.FIRSTNAME),
                formatNestedObjectProperty(Lending_.MEMBER, Member_.LASTNAME),
                formatNestedObjectProperty(Lending_.MEMBER, Member_.EMAIL),
                formatNestedObjectProperty(Lending_.BOOK_COPY, BookCopy_.ISBN),
                formatNestedObjectProperty(Lending_.BOOK_COPY, BookCopy_.BOOK, Book_.TITLE)
        );
    }

    @Override
    public Collection<String> getSortProperties() {
        return properties;
    }
}
