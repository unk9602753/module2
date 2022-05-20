package com.epam.esm.controller;

import com.epam.esm.entity.GiftCertificateDto;
import com.epam.esm.service.GiftCertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("certificates")
@RequiredArgsConstructor
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;

    @GetMapping("{id}")
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
    public List<GiftCertificateDto> getAllCertificatesByCriteria(@RequestParam(value = "sort_direction", required = false, defaultValue = "asc") String direction,
                                                                 @RequestParam(value = "sort_criteria", required = false) String criteria,
                                                                 @RequestParam(value = "search_criteria", required = false, defaultValue = "certificate") String searchCriteria,
                                                                 @RequestParam(value = "search_name", required = false, defaultValue = "") String name) {
        return giftCertificateService.findByCriteriaAndSort(searchCriteria,name,criteria,direction);
    }
}
