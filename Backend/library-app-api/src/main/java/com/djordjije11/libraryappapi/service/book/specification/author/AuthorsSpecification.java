package com.djordjije11.libraryappapi.service.book.specification.author;

import com.djordjije11.libraryappapi.helper.criteriabuilder.CriteriaBuilderHelper;
import com.djordjije11.libraryappapi.helper.string.util.StringExt;
import com.djordjije11.libraryappapi.model.Author;
import com.djordjije11.libraryappapi.model.Author_;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

public class AuthorsSpecification {
    private AuthorsSpecification() {
    }

    public static Specification<Author> create(String search) {
        return new Specification<Author>() {
            @Override
            public Predicate toPredicate(Root<Author> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (StringExt.isNullOrBlank(search)) {
                    return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
                }
                String[] names = search.split(StringExt.SPACE, 2);
                Expression<String> authorsFirstname = criteriaBuilder.upper(root.get(Author_.FIRSTNAME));
                Expression<String> authorsLastname = criteriaBuilder.upper(root.get(Author_.LASTNAME));
                if (names.length == 2) {
                    String name1AsSqlLike = CriteriaBuilderHelper.containsAsSqlLike(names[0]);
                    String name2AsSqlLike = CriteriaBuilderHelper.containsAsSqlLike(names[1]);
                    return criteriaBuilder.or(
                            criteriaBuilder.and(
                                    criteriaBuilder.like(authorsFirstname, name1AsSqlLike),
                                    criteriaBuilder.like(authorsLastname, name2AsSqlLike)
                            ),
                            criteriaBuilder.and(
                                    criteriaBuilder.like(authorsFirstname, name2AsSqlLike),
                                    criteriaBuilder.like(authorsLastname, name1AsSqlLike)
                            )
                    );
                }
                String nameAsSqlLike = CriteriaBuilderHelper.containsAsSqlLike(names[0].toUpperCase());
                return criteriaBuilder.or(
                        criteriaBuilder.like(authorsFirstname, nameAsSqlLike),
                        criteriaBuilder.like(authorsLastname, nameAsSqlLike)
                );
            }
        };
    }
}
