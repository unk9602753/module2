package com.epam.esm.service;

import com.epam.esm.exception.ServiceException;

import java.util.List;
import java.util.Optional;

/**
 * @param <T> entity type
 */
public interface BaseService<T> {
    /**
     * @param id id of entity
     * @return entity if exist, empty in other way
     * @throws ServiceException
     */
    Optional<T> find(long id) throws ServiceException;

    /**
     * @return list of entities
     * @throws ServiceException
     */
    List<T> findAll()throws ServiceException;

    /**
     * @param entity entity
     * @throws ServiceException
     */
    void create(T entity)throws ServiceException;

    /**
     * @param id id of entity
     * @throws ServiceException
     */
    void delete(long id)throws ServiceException;
}
