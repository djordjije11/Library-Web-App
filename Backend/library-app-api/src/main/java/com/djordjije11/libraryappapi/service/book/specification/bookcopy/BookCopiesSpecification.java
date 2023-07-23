package com.djordjije11.libraryappapi.service.book.specification.bookcopy;

import com.djordjije11.libraryappapi.helper.criteriabuilder.CriteriaBuilderHelper;
import com.djordjije11.libraryappapi.helper.string.util.StringExt;
import com.djordjije11.libraryappapi.model.BookCopy;
import com.djordjije11.libraryappapi.model.BookCopyStatus;
import com.djordjije11.libraryappapi.model.BookCopy_;
import com.djordjije11.libraryappapi.model.Book_;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.LinkedList;

public class BookCopiesSpecification {
    private BookCopiesSpecification() {
    }

    public static Specification<BookCopy> create(Long bookId, BookCopyStatus status, String search) {
        return new Specification<BookCopy>() {
            @Override
            public Predicate toPredicate(Root<BookCopy> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                var predicates = new LinkedList<Predicate>();
                predicates.add(criteriaBuilder.equal(root.get(BookCopy_.BOOK).get(Book_.ID), bookId));
                if (StringExt.isNotNullNorBlank(search)) {
                    predicates.add(criteriaBuilder.like(root.get(BookCopy_.ISBN), CriteriaBuilderHelper.containsAsSqlLike(search)));
                }
                if (status != null) {
                    predicates.add(criteriaBuilder.equal(root.get(BookCopy_.STATUS), status));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
            }
        };
    }
}
