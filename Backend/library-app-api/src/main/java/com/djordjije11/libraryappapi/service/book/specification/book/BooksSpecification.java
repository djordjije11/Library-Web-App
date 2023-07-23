package com.djordjije11.libraryappapi.service.book.specification.book;

import com.djordjije11.libraryappapi.helper.criteriabuilder.CriteriaBuilderHelper;
import com.djordjije11.libraryappapi.helper.string.util.StringExt;
import com.djordjije11.libraryappapi.model.Book;
import com.djordjije11.libraryappapi.model.Book_;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public final class BooksSpecification {
    private BooksSpecification() {

    }

    public static Specification<Book> create(String search) {
        return new Specification<Book>() {
            @Override
            public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (StringExt.isNullOrBlank(search)) {
                    return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
                }
                return criteriaBuilder.like(root.get(Book_.TITLE), CriteriaBuilderHelper.containsAsSqlLike(search));
            }
        };
    }
}
