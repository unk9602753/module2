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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoConfig.class)
class TagDaoImplTest {

    @Autowired
    private TagDao tagDao;

    @Autowired
    private GiftCertificateDao giftCertificateDao;

    private List<Tag> tags;

    @BeforeEach
    public void setUp() {
        tags = tagDao.findAll();
    }

    @Test
    public void insert() {
        Tag tag = new Tag(2,"tag31");
        Number id = tagDao.insert(tag);
        assertTrue(id.intValue() > 0);
    }

    @Test
    public void wrongInsert() {
        Tag tag = new Tag(1L,null);
        assertThrows(DataIntegrityViolationException.class,()-> tagDao.insert(tag));
    }

    @Test
    public void find() {
        Optional<Tag> tag = tagDao.find(tags.get(0).getId());
        assertTrue(tag.isPresent());
    }

    @Test
    public void findAll() {
        assertTrue(!tags.isEmpty());
    }

    @Test
    public void remove() {
        int countOfColumns = tagDao.remove(tags.get(0).getId());
        assertTrue(countOfColumns>0);
    }

    @Test
    public void removeWrongId(){
        int countOfColumns = tagDao.remove(-1);
        assertTrue(countOfColumns == 0);
    }

    @Test
    public void findAllTagsByCertificateId() {
        long certificateId = giftCertificateDao.findAll().get(0).getId();
        List<Tag> allTagsByCertificateId = tagDao.findAllTagsByCertificateId(certificateId);
        assertTrue(allTagsByCertificateId != null);
    }

    @Test
    public void findByName() {
        Optional<Tag> tag = tagDao.findByName(tags.get(0).getName());
        assertTrue(tag.isPresent());
    }

    @Test
    public void findByWrongName(){
        Optional<Tag> tag = tagDao.findByName("-------------");
        assertTrue(tag.isEmpty());
    }
}