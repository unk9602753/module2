package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagDao tagDao;

    @Override
    public Optional<Tag> find(long id) {
        Optional<Tag> optTag = tagDao.find(id);
        if (optTag.isPresent()) {
            return optTag;
        }
        throw new ServiceException("exception.find.tag", id);
    }

    @Override
    public List<Tag> findAll() {
        return tagDao.findAll();
    }

    @Override
    public void delete(long id) {
        int statement = tagDao.remove(id);
        if (statement == 0) {
            throw new ServiceException("exception.delete.tag", id);
        }
    }

    @Override
    @Transactional
    public void create(Tag tag) {
        boolean isTagExist = tagDao.findByName(tag.getName()).isPresent();
        if (!isTagExist) tagDao.insert(tag);
        throw new ServiceException("exception.create.tag");
    }
}

