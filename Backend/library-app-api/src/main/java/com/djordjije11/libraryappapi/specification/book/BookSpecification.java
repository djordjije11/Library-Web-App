package com.djordjije11.libraryappapi.specification.book;

import com.djordjije11.libraryappapi.helper.criteriabuilder.CriteriaBuilderHelper;
import com.djordjije11.libraryappapi.model.*;
import jakarta.persistence.criteria.Join;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public final class BookSpecification {
    private BookSpecification() {
    }

    public static Specification<Book> bySearch(String search) {
        return (root, query, criteriaBuilder) -> {
            if (StringUtils.isBlank(search)) {
                return CriteriaBuilderHelper.alwaysTruePredicate(criteriaBuilder);
            }
            Join<Book, Author> bookAuthorJoin = root.join(Book_.AUTHORS);
            return criteriaBuilder.or(
                    criteriaBuilder.like(root.get(Book_.TITLE), CriteriaBuilderHelper.containsAsSqlLike(search)),
                    criteriaBuilder.like(root.get(Book_.PUBLISHER).get(Publisher_.NAME), CriteriaBuilderHelper.containsAsSqlLike(search)),
                    criteriaBuilder.like(bookAuthorJoin.get(Author_.FIRSTNAME), CriteriaBuilderHelper.containsAsSqlLike(search)),
                    criteriaBuilder.like(bookAuthorJoin.get(Author_.LASTNAME), CriteriaBuilderHelper.containsAsSqlLike(search))
            );
        };
    }
}
