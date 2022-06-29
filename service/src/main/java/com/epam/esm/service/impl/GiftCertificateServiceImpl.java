package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.GiftCertificateDto;
import com.epam.esm.exception.ErrorCode;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.GiftCertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateDao giftCertificateDao;
    private final TagDao tagDao;

    @Override
    public void delete(long id) throws ServiceException {
        int statement = giftCertificateDao.remove(id);
        if (statement == 0) {
            throw new ServiceException(ErrorCode.CODE_40020, id);
        }
    }

    @Override
    @Transactional
    public Optional<GiftCertificateDto> find(long id) throws ServiceException {
        Optional<GiftCertificate> giftCertificate = giftCertificateDao.find(id);
        if (giftCertificate.isEmpty()) {
            throw new ServiceException(ErrorCode.CODE_40003, id);
        }
        List<Tag> allTagsByCertificateId = tagDao.findAllTagsByCertificateId(id);
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto(giftCertificate.get());
        giftCertificateDto.setTags(allTagsByCertificateId);
        return Optional.of(giftCertificateDto);
    }

    @Override
    public List<GiftCertificateDto> findAll() {
        List<GiftCertificate> all = giftCertificateDao.findAll();
        return mapToListOfDtos(all);
    }

    @Override
    public List<GiftCertificateDto> findByCriteriaAndSort(String searchCriteria, String searchName, String sortCriteria) throws ServiceException {
        if (!(searchCriteria.equals("tag") || searchCriteria.equals("certificate"))) {
            throw new ServiceException(ErrorCode.CODE_40021, searchCriteria);
        }
        String sortCriteriaAndSortDirection = sortCriteria.replace("-", " ");
        if (!isValidSortParams(sortCriteriaAndSortDirection)) {
            throw new ServiceException(ErrorCode.CODE_40039, sortCriteriaAndSortDirection);
        }
        List<GiftCertificate> giftCertificates = giftCertificateDao.findByCriteriaAndSort(searchCriteria, searchName, sortCriteriaAndSortDirection);
        if (giftCertificates.isEmpty()) {
            throw new ServiceException(ErrorCode.CODE_40030, searchCriteria + ", " + searchName);
        }
        return mapToListOfDtos(giftCertificates);
    }

    @Override
    @Transactional
    public void create(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = new GiftCertificate(giftCertificateDto);
        List<Tag> tags = giftCertificateDto.getTags();
        setCreatedAndUpdatedDate(giftCertificate, LocalDateTime.now(), LocalDateTime.now());
        long id = giftCertificateDao.insert(giftCertificate).longValue();
        linkCertificateWithTags(tags, id);
    }

    @Override
    @Transactional
    public void update(GiftCertificateDto patch, long id) throws ServiceException {
        Optional<GiftCertificate> optGift = giftCertificateDao.find(id);
        GiftCertificate certificate = new GiftCertificate(patch);
        if (optGift.isEmpty()) {
            throw new ServiceException(ErrorCode.CODE_40001, id);
        }
        GiftCertificate updatedCertificate = updateObject(optGift.get(), certificate, id);
        setCreatedAndUpdatedDate(updatedCertificate, optGift.get().getCreateDate(), LocalDateTime.now());
        List<Tag> tags = patch.getTags();
        giftCertificateDao.update(updatedCertificate);
        giftCertificateDao.removeTagToGiftCertificate(id);
        linkCertificateWithTags(tags, id);
    }

    private void linkCertificateWithTags(List<Tag> tags, long certificateId) {
        if (tags == null) return;
        tags.forEach(t -> {
            if (tagDao.findByName(t.getName()).isEmpty()) {
                tagDao.insert(t);
            }
            giftCertificateDao.addTagToGiftCertificate(certificateId, tagDao.findByName(t.getName()).get().getId());
        });
    }

    private List<GiftCertificateDto> mapToListOfDtos(List<GiftCertificate> giftCertificates) {
        List<GiftCertificateDto> dtoList = new ArrayList<>();
        if (giftCertificates != null) {
            giftCertificates.forEach(g -> {
                GiftCertificateDto giftCertificateDto = new GiftCertificateDto(g);
                List<Tag> allTagsByCertificateId = tagDao.findAllTagsByCertificateId(g.getId());
                giftCertificateDto.setTags(allTagsByCertificateId);
                dtoList.add(giftCertificateDto);
            });
        }
        return dtoList;
    }

    private GiftCertificate updateObject(GiftCertificate certificate, GiftCertificate patch, long id) throws ServiceException {
        if ((patch.getName() != null) && (patch.getName().length() < 40)) {
            certificate.setName(patch.getName());
        } else if ((patch.getName() != null) && (patch.getName().length() > 40)) {
            throw new ServiceException(ErrorCode.CODE_40042, id);
        }
        if ((patch.getDescription() != null) && (patch.getDescription().length() < 40)) {
            certificate.setDescription(patch.getDescription());
        } else if ((patch.getDescription() != null) && (patch.getDescription().length() > 40)) {
            throw new ServiceException(ErrorCode.CODE_40043, id);
        }
        if ((patch.getPrice() != 0) && (patch.getPrice() >= 1 && patch.getPrice() <= 10000)) {
            certificate.setPrice(patch.getPrice());
        } else if ((patch.getPrice() != 0) && (patch.getPrice() < 1 || patch.getPrice() > 10000)) {
            throw new ServiceException(ErrorCode.CODE_40044, id);
        }
        if ((patch.getDuration() != 0) && (patch.getDuration() >= 5 && patch.getDuration() <= 365)) {
            certificate.setDuration(patch.getDuration());
        } else if ((patch.getDuration() != 0) && (patch.getDuration() < 5 || patch.getDuration() > 365)) {
            throw new ServiceException(ErrorCode.CODE_40045, id);
        }
        return certificate;
    }

    private void setCreatedAndUpdatedDate(GiftCertificate giftCertificate, LocalDateTime created, LocalDateTime updated) {
        giftCertificate.setCreateDate(created);
        giftCertificate.setLastUpdateDate(updated);
    }

    private boolean isValidSortParams(String param) {
        if(param.substring(param.length()-1).equals(",")){
            return false;
        }
        List<String> list = Arrays.asList("asc", "desc", "id", "name", "description", "price", "duration", "create_date", "last_update_date");
        String replace = param.replace(",", " ");
        String[] s = replace.split(" ");
        Optional<String> first = Arrays.stream(s).filter(str -> !list.contains(str)).findFirst();
        return first.isEmpty();
    }
}
