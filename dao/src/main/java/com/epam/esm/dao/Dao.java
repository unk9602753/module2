package com.epam.esm.dao;

import java.util.List;
import java.util.Optional;

/**
 * @param <T>
 */
public interface Dao<T> {
    /**
     * @param entity entity
     * @return id of entity
     */
    Number insert(T entity);

    /**
     * @param entity entity
     */
    void update(T entity);

    /**
     * @param id id of entity
     * @return
     */
    Optional<T> find(long id);

    /**
     * @return list of entities
     */
    List<T> findAll();

    /**
     * @param id id of entity
     * @return statement
     */
    int remove(long id);
}
