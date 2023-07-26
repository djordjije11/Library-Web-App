package com.djordjije11.libraryappapi.controller.request.book;

import com.djordjije11.libraryappapi.controller.request.RequestSortingParamsParser;
import com.djordjije11.libraryappapi.model.Book_;
import com.djordjije11.libraryappapi.model.Publisher_;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

@Component
@Scope(scopeName = BeanDefinition.SCOPE_SINGLETON)
public class BookRequestSortingParamsParser extends RequestSortingParamsParser {
    private final Collection<String> properties;

    public BookRequestSortingParamsParser() {
        properties = Arrays.asList(
                Book_.ID,
                Book_.TITLE,
                Book_.PAGES_NUMBER,
                formatNestedObjectProperty(Book_.PUBLISHER, Publisher_.NAME)
        );
    }

    @Override
    public Collection<String> getSortProperties() {
        return properties;
    }
}
