package com.happyfxmas.ordermicroservice.store.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<E, ID> {
    Optional<E> findById(ID id);

    List<E> findAll();

    void save(E entity);

    void update(E newEntity);

    void delete(E entity);
}
