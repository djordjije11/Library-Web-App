package com.djordjije11.libraryappapi.specification.bookcopy;

import com.djordjije11.libraryappapi.helper.criteriabuilder.CriteriaBuilderHelper;
import com.djordjije11.libraryappapi.helper.string.util.StringExt;
import com.djordjije11.libraryappapi.model.*;
import org.springframework.data.jpa.domain.Specification;

public class BookCopySpecification {
    private BookCopySpecification() {
    }

    public static Specification<BookCopy> bySearch(String search){
        return (root, query, criteriaBuilder) -> {
            if(StringExt.isNullOrBlank(search)){
                return CriteriaBuilderHelper.alwaysTruePredicate(criteriaBuilder);
            }
            return criteriaBuilder.like(root.get(BookCopy_.ISBN), CriteriaBuilderHelper.containsAsSqlLike(search));
        };
    }

    public static Specification<BookCopy> byBook(Long bookId){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(BookCopy_.BOOK).get(Book_.ID), bookId);
    }

    public static Specification<BookCopy> byBuilding(Long buildingId){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(BookCopy_.BUILDING).get(Building_.ID), buildingId);
    }

    public static Specification<BookCopy> byStatus(BookCopyStatus status){
        return (root, query, criteriaBuilder) -> {
            if(status == null){
                return CriteriaBuilderHelper.alwaysTruePredicate(criteriaBuilder);
            }
            return criteriaBuilder.equal(root.get(BookCopy_.STATUS), status);
        };
    }
}
