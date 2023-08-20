package com.djordjije11.libraryappapi.specification.publisher;

import com.djordjije11.libraryappapi.helper.criteriabuilder.util.CriteriaBuilderUtil;
import com.djordjije11.libraryappapi.model.Publisher;
import com.djordjije11.libraryappapi.model.Publisher_;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class PublisherSpecification {
    private PublisherSpecification() {}

    public static Specification<Publisher> bySearch(String search) {
        return new Specification<Publisher>() {
            @Override
            public Predicate toPredicate(Root<Publisher> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(StringUtils.isBlank(search)){
                    return CriteriaBuilderUtil.alwaysTruePredicate(criteriaBuilder);
                }
                return criteriaBuilder.like(root.get(Publisher_.NAME), CriteriaBuilderUtil.containsAsSqlLike(search));
            }
        };
    }
}
