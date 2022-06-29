package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;

/**
 * Representing interface extends parametrized Dao
 */
public interface GiftCertificateDao extends Dao<GiftCertificate> {

    /**
     * @param giftCertificateId id of certificate
     * @param tagId id of tag
     * @return 1 for success and 0 for failure
     */
    long addTagToGiftCertificate(long giftCertificateId, long tagId);

    /**
     * @param giftCertificateId id of certificate
     * @return 1 for success and 0 for failure
     */
    long removeTagToGiftCertificate(long giftCertificateId);

    /**
     * @param searchCriteria criteria of search: tag or certificate
     * @param searchName name for searching
     * @param sortCriteriaAndSortDirection sort criteria
     * @return list of certificate satisfying parameters
     */
    List<GiftCertificate> findByCriteriaAndSort(String searchCriteria, String searchName, String sortCriteriaAndSortDirection);
}
