package com.epam.esm.dao.impl;

import com.epam.esm.config.DaoConfig;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.GiftCertificate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoConfig.class)
@ActiveProfiles("dev")
class GiftCertificateDaoImplTest {
    @Autowired
    GiftCertificateDao giftCertificateDao;

    @Autowired
    TagDao tagDao;

    List<GiftCertificate> giftCertificates;

    @BeforeEach
    void setUp() {
        giftCertificates = giftCertificateDao.findAll();
    }

    @Test
    void insert() {
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .name("certificate")
                .description("description")
                .price(1.1)
                .duration(10)
                .lastUpdateDate(LocalDateTime.now())
                .createDate(LocalDateTime.now())
                .build();
        Number id = giftCertificateDao.insert(giftCertificate);
        assertTrue(id.intValue()>0);
    }

    @Test
    void wrongInsert() {
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .name(null)
                .description("description")
                .price(1.1)
                .duration(10)
                .lastUpdateDate(LocalDateTime.now())
                .createDate(LocalDateTime.now())
                .build();
        assertThrows(DataIntegrityViolationException.class,()->giftCertificateDao.insert(giftCertificate));
    }

    @Test
    void update() {
        GiftCertificate certificate = giftCertificates.get(0);
        certificate.setName("update");
        giftCertificateDao.update(certificate);
        GiftCertificate certificateUpdated = giftCertificateDao.find(certificate.getId()).get();
        assertEquals(certificate,certificateUpdated);
    }

    @Test
    void find() {
        Optional<GiftCertificate> optCertificate = giftCertificateDao.find(giftCertificates.get(0).getId());
        assertTrue(optCertificate.isPresent());
    }

    @Test
    void wrongFind() {
        Optional<GiftCertificate> optCertificate = giftCertificateDao.find(-1);
        assertTrue(optCertificate.isEmpty());
    }

    @Test
    void findAll() {
        assertTrue(!giftCertificates.isEmpty());
    }

    @Test
    void remove() {
        int countOfColumns = giftCertificateDao.remove(giftCertificates.get(0).getId());
        assertTrue(countOfColumns>0);
    }

    @Test
    void findByCriteriaAndSort() {
        String name = giftCertificates.get(0).getName();
        List<GiftCertificate> certificates = giftCertificateDao.findByCriteriaAndSort("certificate", name,"id");
        assertTrue(!certificates.isEmpty());
    }


    @Test
    void addTagToGiftCertificate() {
        long id = giftCertificates.get(0).getId();
        long tagId = tagDao.findAll().get(0).getId();
        long count = giftCertificateDao.addTagToGiftCertificate(id, tagId);
        assertTrue(count>0);
    }

    @Test
    void removeTagToGiftCertificate() {
        long id = giftCertificates.get(0).getId();
        long count = giftCertificateDao.removeTagToGiftCertificate(id);
        assertTrue(count>0);

    }
}