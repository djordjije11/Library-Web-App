package com.djordjije11.libraryappapi.controller.request.bookcopy;

import com.djordjije11.libraryappapi.controller.request.RequestSortingParamsParser;
import com.djordjije11.libraryappapi.model.*;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

@Component
@Scope(scopeName = BeanDefinition.SCOPE_SINGLETON)
public class BookCopyRequestSortingParamsParser extends RequestSortingParamsParser {
    private final Collection<String> properties;

    public BookCopyRequestSortingParamsParser() {
        properties = Arrays.asList(
                BookCopy_.ID,
                BookCopy_.ISBN,
                BookCopy_.STATUS,
                formatNestedObjectProperty(BookCopy_.BOOK, Book_.TITLE),
                formatNestedObjectProperty(BookCopy_.BOOK, Book_.PUBLISHER, Publisher_.NAME)
        );
    }

    @Override
    public Collection<String> getSortProperties() {
        return properties;
    }
}
