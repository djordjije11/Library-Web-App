package com.djordjije11.libraryappapi.specification.member;

import com.djordjije11.libraryappapi.helper.criteriabuilder.CriteriaBuilderHelper;
import com.djordjije11.libraryappapi.model.Member;
import com.djordjije11.libraryappapi.model.Member_;
import org.apache.commons.lang3.StringUtils;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

public class MemberSpecification {
    private MemberSpecification(){}

    public static Specification<Member> bySearch(String search){
        return new Specification<Member>() {
            @Override
            public Predicate toPredicate(Root<Member> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(StringUtils.isBlank(search)){
                    return CriteriaBuilderHelper.alwaysTruePredicate(criteriaBuilder);
                }
                String[] names = search.split(StringUtils.SPACE, 2);
                Expression<String> membersFirstname = criteriaBuilder.upper(root.get(Member_.FIRSTNAME));
                Expression<String> membersLastname = criteriaBuilder.upper(root.get(Member_.LASTNAME));
                if (names.length == 2) {
                    String name1AsSqlLike = CriteriaBuilderHelper.containsAsSqlLike(names[0]);
                    String name2AsSqlLike = CriteriaBuilderHelper.containsAsSqlLike(names[1]);
                    return criteriaBuilder.or(
                            criteriaBuilder.and(
                                    criteriaBuilder.like(membersFirstname, name1AsSqlLike),
                                    criteriaBuilder.like(membersLastname, name2AsSqlLike)
                            ),
                            criteriaBuilder.and(
                                    criteriaBuilder.like(membersFirstname, name2AsSqlLike),
                                    criteriaBuilder.like(membersLastname, name1AsSqlLike)
                            )
                    );
                }
                String nameAsSqlLike = CriteriaBuilderHelper.containsAsSqlLike(names[0].toUpperCase());
                Expression<String> membersIdCardNumber = criteriaBuilder.upper(root.get(Member_.ID_CARD_NUMBER));
                Expression<String> membersEmail = criteriaBuilder.upper(root.get(Member_.EMAIL));
                return criteriaBuilder.or(
                        criteriaBuilder.like(membersIdCardNumber, nameAsSqlLike),
                        criteriaBuilder.like(membersEmail, nameAsSqlLike),
                        criteriaBuilder.like(membersFirstname, nameAsSqlLike),
                        criteriaBuilder.like(membersLastname, nameAsSqlLike)
                );
            }
        };
    }
}
