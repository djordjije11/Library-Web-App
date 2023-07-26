package com.djordjije11.libraryappapi.controller.request.member;

import com.djordjije11.libraryappapi.controller.request.RequestSortingParamsParser;
import com.djordjije11.libraryappapi.model.Member_;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

@Component
@Scope(scopeName = BeanDefinition.SCOPE_SINGLETON)
public class MemberRequestSortingParamsParser extends RequestSortingParamsParser {
    private final Collection<String> properties;

    public MemberRequestSortingParamsParser() {
        properties = Arrays.asList(
                Member_.ID,
                Member_.ID_CARD_NUMBER,
                Member_.FIRSTNAME,
                Member_.LASTNAME,
                Member_.EMAIL,
                Member_.GENDER,
                Member_.BIRTHDAY
        );
    }

    @Override
    public Collection<String> getSortProperties() {
        return properties;
    }
}
