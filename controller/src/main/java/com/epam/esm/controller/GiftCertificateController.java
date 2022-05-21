package com.epam.esm.controller;

import com.epam.esm.entity.GiftCertificateDto;
import com.epam.esm.service.GiftCertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<GiftCertificateDto> getCertificateById(@PathVariable long id) {
        Optional<GiftCertificateDto> optDto = giftCertificateService.find(id);
        return new ResponseEntity<>(optDto.get(), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postCertificate(@RequestBody GiftCertificateDto giftCertificateDto) {
        giftCertificateService.create(giftCertificateDto);
    }

    @DeleteMapping(value = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCertificate(@PathVariable long id) {
        giftCertificateService.delete(id);
    }

    @PatchMapping(path = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void patchCertificate(@PathVariable long id, @RequestBody GiftCertificateDto patch) {
        giftCertificateService.update(patch, id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificateDto> getAllCertificatesByCriteria(@RequestParam(value = "search_name", required = false, defaultValue = "") String name,
                                                                 @RequestParam(value = "search_criteria", required = false, defaultValue = "certificate") String searchCriteria,
                                                                 @RequestParam(value = "sort_criteria", required = false) String sortCriteria) {
        return giftCertificateService.findByCriteriaAndSort(searchCriteria, name, sortCriteria);
    }
}
