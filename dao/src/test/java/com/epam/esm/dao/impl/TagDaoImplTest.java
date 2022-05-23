package com.epam.esm.dao.impl;

import com.epam.esm.config.DaoConfig;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoConfig.class)
@ActiveProfiles("dev")
class TagDaoImplTest {

    @Autowired
    TagDao tagDao;

    @Autowired
    GiftCertificateDao giftCertificateDao;

    List<Tag> tags;

    @BeforeEach
    void setUp() {
        tags = tagDao.findAll();
    }

    @Test
    void insert() {
        Tag tag = new Tag(1L,"tag");
        Number id = tagDao.insert(tag);
        assertTrue(id.intValue() > 0);
    }

    @Test
    void wrongInsert() {
        Tag tag = new Tag(1L,null);
        assertThrows(DataIntegrityViolationException.class,()-> tagDao.insert(tag));
    }

    @Test
    void find() {
        Optional<Tag> tag = tagDao.find(tags.get(0).getId());
        assertTrue(tag.isPresent());
    }

    @Test
    void findAll() {
        assertTrue(!tags.isEmpty());
    }

    @Test
    void remove() {
        int countOfColumns = tagDao.remove(tags.get(0).getId());
        assertTrue(countOfColumns>0);
    }

    @Test
    void removeWrongId(){
        int countOfColumns = tagDao.remove(-1);
        assertTrue(countOfColumns == 0);
    }

    @Test
    void findAllTagsByCertificateId() {
        long certificateId = giftCertificateDao.findAll().get(0).getId();
        List<Tag> allTagsByCertificateId = tagDao.findAllTagsByCertificateId(certificateId);
        assertTrue(allTagsByCertificateId != null);
    }

    @Test
    void findByName() {
        Optional<Tag> tag = tagDao.findByName(tags.get(0).getName());
        assertTrue(tag.isPresent());
    }

    @Test
    void findByWrongName(){
        Optional<Tag> tag = tagDao.findByName("-------------");
        assertTrue(tag.isEmpty());
    }
}