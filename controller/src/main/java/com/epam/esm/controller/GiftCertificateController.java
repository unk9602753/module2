package com.epam.esm.controller;

import com.epam.esm.entity.GiftCertificateDto;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.GiftCertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("certificates")
@RequiredArgsConstructor
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto getCertificateById(@PathVariable long id) throws ServiceException {
        Optional<GiftCertificateDto> optDto = giftCertificateService.find(id);
        return optDto.get();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postCertificate(@Validated @RequestBody GiftCertificateDto giftCertificateDto) throws ServiceException {
        giftCertificateService.create(giftCertificateDto);
    }

    @DeleteMapping(value = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCertificate(@PathVariable long id) throws ServiceException {
        giftCertificateService.delete(id);
    }

    @PatchMapping(path = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void patchCertificate(@PathVariable long id, @RequestBody GiftCertificateDto patch) throws ServiceException {
        giftCertificateService.update(patch, id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificateDto> getAllCertificatesByCriteria(@RequestParam(value = "search_name", required = false, defaultValue = "") String name,
                                                                 @RequestParam(value = "search_criteria", required = false, defaultValue = "certificate") String searchCriteria,
                                                                 @RequestParam(value = "sort", required = false, defaultValue = "id") String sortCriteria) throws ServiceException {
        return giftCertificateService.findByCriteriaAndSort(searchCriteria, name, sortCriteria);
    }
}
