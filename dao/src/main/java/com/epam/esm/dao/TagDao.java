package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDao extends Dao<Tag> {
    List<Tag> findAllTagsByCertificateId(long id);

    Optional<Tag> findByName(String name);
}
