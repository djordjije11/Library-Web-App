package com.djordjije11.libraryappapi.helper.criteriabuilder;

public final class CriteriaBuilderHelper {
    private CriteriaBuilderHelper(){}

    public static String containsAsSqlLike(String value){
        return String.format("%%%s%%", value);
    }
}
