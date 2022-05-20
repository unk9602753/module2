package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateDto;

import java.util.List;

public interface GiftCertificateService extends BaseService<GiftCertificateDto> {

    void update(GiftCertificateDto giftCertificateDto, long id);

    List<GiftCertificateDto> findByCriteriaAndSort(String searchCriteria, String searchName, String sortCriteria, String sortDirection);
}
