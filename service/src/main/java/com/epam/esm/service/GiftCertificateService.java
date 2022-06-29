package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificateDto;
import com.epam.esm.exception.ServiceException;

import java.util.List;

/**
 * interface extended base interface for entity and add new functions
 */
public interface GiftCertificateService extends BaseService<GiftCertificateDto> {

    /**
     * @param giftCertificateDto Dto
     * @param id id of dto
     * @throws ServiceException
     */
    void update(GiftCertificateDto giftCertificateDto, long id) throws ServiceException;

    /**
     * @param searchCriteria criteria for search
     * @param searchName name for search
     * @param sortCriteria sort criteria
     * @return list of certificate satisfying params
     * @throws ServiceException
     */
    List<GiftCertificateDto> findByCriteriaAndSort(String searchCriteria, String searchName, String sortCriteria) throws ServiceException;
}
