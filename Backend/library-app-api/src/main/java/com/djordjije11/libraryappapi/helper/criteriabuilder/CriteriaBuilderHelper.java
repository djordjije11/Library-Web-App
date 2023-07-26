package com.djordjije11.libraryappapi.helper.criteriabuilder;


import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;

public final class CriteriaBuilderHelper {
    private CriteriaBuilderHelper() {
    }

    public static String containsAsSqlLike(String value) {
        return String.format("%%%s%%", value);
    }

    public static Predicate alwaysTruePredicate(CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
    }
}
