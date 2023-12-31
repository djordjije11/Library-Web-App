package com.djordjije11.libraryappapi.specification.book;

import com.djordjije11.libraryappapi.helper.criteriabuilder.util.CriteriaBuilderUtil;
import com.djordjije11.libraryappapi.model.*;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public final class BookSpecification {
    private BookSpecification() {
    }

    public static Specification<Book> bySearch(String search) {
        return (root, query, criteriaBuilder) -> {
            if (StringUtils.isBlank(search)) {
                return CriteriaBuilderUtil.alwaysTruePredicate(criteriaBuilder);
            }
            final Join<Book, Author> bookAuthorJoin = root.join(Book_.AUTHORS);
            final String searchAsLike = CriteriaBuilderUtil.containsAsSqlLike(search);
            boolean withPagesNumber = true;
            int pagesNumber = -1;
            try {
                pagesNumber = Integer.parseInt(search);
            } catch(NumberFormatException ex){
                withPagesNumber = false;
            }
            Predicate specificationPredicate = criteriaBuilder.or(
                    criteriaBuilder.like(root.get(Book_.TITLE), searchAsLike),
                    criteriaBuilder.like(root.get(Book_.PUBLISHER).get(Publisher_.NAME), searchAsLike),
                    criteriaBuilder.like(bookAuthorJoin.get(Author_.FIRSTNAME), searchAsLike),
                    criteriaBuilder.like(bookAuthorJoin.get(Author_.LASTNAME), searchAsLike)
            );
            if(withPagesNumber){
                specificationPredicate = criteriaBuilder.or(specificationPredicate, criteriaBuilder.equal(root.get(Book_.PAGES_NUMBER), pagesNumber));
            }
            return specificationPredicate;
        };
    }
}
