package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;

public interface GiftCertificateDao extends Dao<GiftCertificate> {

    long addTagToGiftCertificate(long giftCertificateId, long tagId);

    long removeTagToGiftCertificate(long giftCertificateId);

    List<GiftCertificate> findByCriteriaAndSort(String searchCriteria, String searchName, String sortCriteria, String sortDirection);
}
