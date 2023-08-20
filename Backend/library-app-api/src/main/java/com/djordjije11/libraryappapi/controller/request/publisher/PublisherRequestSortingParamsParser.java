package com.djordjije11.libraryappapi.controller.request.publisher;

import com.djordjije11.libraryappapi.controller.request.RequestSortingParamsParser;
import com.djordjije11.libraryappapi.model.Publisher_;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

@Component
@Scope(scopeName = BeanDefinition.SCOPE_SINGLETON)
public class PublisherRequestSortingParamsParser extends RequestSortingParamsParser {
    private final Collection<String> properties;

    public PublisherRequestSortingParamsParser() {
        properties = Arrays.asList(Publisher_.ID, Publisher_.NAME);
    }

    @Override
    protected Collection<String> getSortProperties() {
        return properties;
    }
}
