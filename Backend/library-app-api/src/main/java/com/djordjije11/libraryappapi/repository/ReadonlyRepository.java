package com.djordjije11.libraryappapi.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface ReadonlyRepository<T, ID extends Serializable> extends Repository<T, ID> {
    Optional<T> findById(ID id);
    List<T> findAll();
    List<T> findAllById(Iterable<ID> ids);
    boolean existsById(ID id);
    long count();
}
