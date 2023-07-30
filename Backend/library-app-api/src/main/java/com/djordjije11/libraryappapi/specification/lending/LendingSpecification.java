package com.djordjije11.libraryappapi.specification.lending;

import com.djordjije11.libraryappapi.helper.criteriabuilder.util.CriteriaBuilderUtil;
import com.djordjije11.libraryappapi.model.*;
import io.micrometer.common.util.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public final class LendingSpecification {
    private LendingSpecification() {
    }

    public static Specification<Lending> isUnreturned() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.get(Lending_.RETURN_DATE));
    }

    public static Specification<Lending> byMember(Long memberId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Lending_.MEMBER).get(Member_.ID), memberId);
    }

    public static Specification<Lending> bySearch(String search) {
        return (root, query, criteriaBuilder) -> {
            if (StringUtils.isBlank(search)) {
                return CriteriaBuilderUtil.alwaysTruePredicate(criteriaBuilder);
            }
            return criteriaBuilder.or(
                    criteriaBuilder.like(
                            root.get(Lending_.BOOK_COPY).get(BookCopy_.ISBN),
                            CriteriaBuilderUtil.containsAsSqlLike(search)
                    ),
                    criteriaBuilder.like(
                            criteriaBuilder.upper(root.get(Lending_.BOOK_COPY).get(BookCopy_.BOOK).get(Book_.TITLE)),
                            CriteriaBuilderUtil.containsAsSqlLike(search.toUpperCase())
                    )
            );
        };
    }
}
