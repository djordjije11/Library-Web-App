package com.djordjije11.libraryappapi.specification.bookcopy;

import com.djordjije11.libraryappapi.helper.criteriabuilder.util.CriteriaBuilderUtil;
import com.djordjije11.libraryappapi.model.*;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class BookCopySpecification {
    private BookCopySpecification() {
    }

    public static Specification<BookCopy> byAllBooksSearch(String search) {
        return (root, query, criteriaBuilder) -> {
            if (StringUtils.isBlank(search)) {
                return CriteriaBuilderUtil.alwaysTruePredicate(criteriaBuilder);
            }
            final Join<BookCopy, Book> bookCopyBookJoin = root.join(BookCopy_.BOOK);
            final Join<Book, Author> bookAuthorJoin = bookCopyBookJoin.join(Book_.AUTHORS);
            final String searchAsLike = CriteriaBuilderUtil.containsAsSqlLike(search);
            return criteriaBuilder.or(
                    criteriaBuilder.like(root.get(BookCopy_.ISBN), searchAsLike),
                    criteriaBuilder.like(bookCopyBookJoin.get(Book_.TITLE), searchAsLike),
                    criteriaBuilder.like(bookCopyBookJoin.get(Book_.PUBLISHER).get(Publisher_.NAME), searchAsLike),
                    criteriaBuilder.like(bookAuthorJoin.get(Author_.FIRSTNAME), searchAsLike),
                    criteriaBuilder.like(bookAuthorJoin.get(Author_.LASTNAME), searchAsLike)
            );
        };
    }

    public static Specification<BookCopy> byOneBookSearch(String search) {
        return (root, query, criteriaBuilder) -> {
            if (StringUtils.isBlank(search)) {
                return CriteriaBuilderUtil.alwaysTruePredicate(criteriaBuilder);
            }
            query.distinct(true);
            final Join<BookCopy, Building> buildingJoin = root.join(BookCopy_.BUILDING, JoinType.LEFT);
            final Join<Building, Address> addressJoin = buildingJoin.join(Building_.ADDRESS, JoinType.LEFT);
            final Join<Address, City> cityJoin = addressJoin.join(Address_.CITY, JoinType.LEFT);
            final String searchAsSqlLike = CriteriaBuilderUtil.containsAsSqlLike(search);

            return criteriaBuilder.or(
                    criteriaBuilder.like(root.get(BookCopy_.ISBN), searchAsSqlLike),
                    criteriaBuilder.and(
                            criteriaBuilder.isNotNull(root.get(BookCopy_.BUILDING)),
                            criteriaBuilder.or(
                                    criteriaBuilder.like(
                                            criteriaBuilder.concat(
                                                    criteriaBuilder.concat(
                                                            addressJoin.get(Address_.STREET_NAME),
                                                            " "
                                                    ),
                                                    addressJoin.get(Address_.STREET_NUMBER)
                                            ),
                                            searchAsSqlLike
                                    ),
                                    criteriaBuilder.like(cityJoin.get(City_.NAME), searchAsSqlLike),
                                    criteriaBuilder.like(cityJoin.get(City_.ZIPCODE), searchAsSqlLike)
                            )
                    )
            );
        };
    }

    public static Specification<BookCopy> byBook(Long bookId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(BookCopy_.BOOK).get(Book_.ID), bookId);
    }

    public static Specification<BookCopy> byBuilding(Long buildingId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(BookCopy_.BUILDING).get(Building_.ID), buildingId);
    }

    public static Specification<BookCopy> byStatus(BookCopyStatus status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null) {
                return CriteriaBuilderUtil.alwaysTruePredicate(criteriaBuilder);
            }
            return criteriaBuilder.equal(root.get(BookCopy_.STATUS), status);
        };
    }
}
