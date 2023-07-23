package com.djordjije11.libraryappapi.service.book.specification.lending;

import com.djordjije11.libraryappapi.helper.criteriabuilder.CriteriaBuilderHelper;
import com.djordjije11.libraryappapi.helper.string.util.StringExt;
import com.djordjije11.libraryappapi.model.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.LinkedList;

public final class LendingsByMemberSpecification {
    private LendingsByMemberSpecification() {
    }

    public static Specification<Lending> create(Long memberId, String search) {
        return new Specification<Lending>() {
            @Override
            public Predicate toPredicate(Root<Lending> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                var predicates = new LinkedList<Predicate>();
                predicates.add(criteriaBuilder.equal(root.get(Lending_.MEMBER).get(Member_.ID), memberId));
                if (StringExt.isNotNullNorBlank(search)) {
                    predicates.add(
                            criteriaBuilder.or(
                                    criteriaBuilder.like(
                                            root.get(Lending_.BOOK_COPY).get(BookCopy_.ISBN),
                                            CriteriaBuilderHelper.containsAsSqlLike(search)
                                    ),
                                    criteriaBuilder.like(
                                            criteriaBuilder.upper(root.get(Lending_.BOOK_COPY).get(BookCopy_.BOOK).get(Book_.TITLE)),
                                            CriteriaBuilderHelper.containsAsSqlLike(search.toUpperCase())
                                    )
                            )
                    );
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
            }
        };
    }
}
