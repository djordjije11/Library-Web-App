package com.djordjije11.libraryappapi.repository;

import com.djordjije11.libraryappapi.helper.string.util.StringExt;
import com.djordjije11.libraryappapi.model.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Page<Author> findAllByFirstnameContainsIgnoreCaseOrLastnameContainsIgnoreCase(Pageable pageable, String firstname, String lastname);
    Page<Author> findAllByFirstnameContainsIgnoreCaseAndLastnameContainsIgnoreCaseOrFirstnameContainsIgnoreCaseAndLastnameContainsIgnoreCase(Pageable pageable, String firstname1, String lastname1, String firstname2, String lastname2);


    default Page<Author> findAllAuthorsByOneName(Pageable pageable, String name){
        return findAllByFirstnameContainsIgnoreCaseOrLastnameContainsIgnoreCase(pageable, name, name);
    }
    default Page<Author> findAllAuthorsByFullName(Pageable pageable, String name1, String name2){
        return findAllByFirstnameContainsIgnoreCaseAndLastnameContainsIgnoreCaseOrFirstnameContainsIgnoreCaseAndLastnameContainsIgnoreCase(pageable, name1, name2, name2, name1);
    }
    default Page<Author> findAllAuthors(Pageable pageable, String filter){
        if (StringExt.isNullOrBlank(filter)) {
            return findAll(pageable);
        }
        String[] names = filter.split(StringExt.SPACE, 2);
        if (names.length > 1){
            return findAllAuthorsByFullName(pageable, names[0], names[1]);
        }
        return findAllAuthorsByOneName(pageable, filter);
    }
}
