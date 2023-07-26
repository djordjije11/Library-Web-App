package com.djordjije11.libraryappapi.specification.book;

import com.djordjije11.libraryappapi.helper.criteriabuilder.CriteriaBuilderHelper;
import com.djordjije11.libraryappapi.helper.string.util.StringExt;
import com.djordjije11.libraryappapi.model.Book;
import com.djordjije11.libraryappapi.model.Book_;
import org.springframework.data.jpa.domain.Specification;

public final class BookSpecification {
    private BookSpecification() {

    }

    public static Specification<Book> bySearch(String search) {
        return (root, query, criteriaBuilder) -> {
            if (StringExt.isNullOrBlank(search)) {
                return CriteriaBuilderHelper.alwaysTruePredicate(criteriaBuilder);
            }
            return criteriaBuilder.like(root.get(Book_.TITLE), CriteriaBuilderHelper.containsAsSqlLike(search));
        };
    }
}
