package com.djordjije11.libraryappapi.service.book.specification.bookcopy;

import com.djordjije11.libraryappapi.helper.criteriabuilder.CriteriaBuilderHelper;
import com.djordjije11.libraryappapi.helper.string.util.StringExt;
import com.djordjije11.libraryappapi.model.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.LinkedList;

public final class BookCopiesInBuildingSpecification {
    private BookCopiesInBuildingSpecification() {
    }

    public static Specification<BookCopy> create(Long bookId, Long buildingId, String search) {
        return new Specification<BookCopy>() {
            @Override
            public Predicate toPredicate(Root<BookCopy> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                var predicates = new LinkedList<Predicate>();
                predicates.add(
                        criteriaBuilder.and(
                                criteriaBuilder.equal(root.get(BookCopy_.BOOK).get(Book_.ID), bookId),
                                criteriaBuilder.equal(root.get(BookCopy_.BUILDING).get(Building_.ID), buildingId)
                        )
                );
                if (StringExt.isNotNullNorBlank(search)) {
                    predicates.add(criteriaBuilder.like(root.get(BookCopy_.ISBN), CriteriaBuilderHelper.containsAsSqlLike(search)));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
            }
        };
    }
}
