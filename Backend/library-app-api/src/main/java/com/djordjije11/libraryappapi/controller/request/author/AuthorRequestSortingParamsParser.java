package com.djordjije11.libraryappapi.controller.request.author;

import com.djordjije11.libraryappapi.controller.request.RequestSortingParamsParser;
import com.djordjije11.libraryappapi.model.Author_;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

@Component
@Scope(scopeName = BeanDefinition.SCOPE_SINGLETON)
public class AuthorRequestSortingParamsParser extends RequestSortingParamsParser {
    private final Collection<String> properties;

    public AuthorRequestSortingParamsParser() {
        properties = Arrays.asList(
                Author_.ID,
                Author_.FIRSTNAME,
                Author_.LASTNAME
        );
    }

    @Override
    public Collection<String> getSortProperties() {
        return properties;
    }
}
