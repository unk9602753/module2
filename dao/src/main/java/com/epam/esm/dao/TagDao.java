package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

/**
 * interface for tagDao
 */
public interface TagDao extends Dao<Tag> {
    /**
     * @param id id of certificate
     * @return list of tags for certificate
     */
    List<Tag> findAllTagsByCertificateId(long id);

    /**
     * @param name name of tag
     * @return tag if exist or empty in other way
     */
    Optional<Tag> findByName(String name);
}
