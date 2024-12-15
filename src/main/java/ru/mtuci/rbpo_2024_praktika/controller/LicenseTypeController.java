package ru.mtuci.rbpo_2024_praktika.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mtuci.rbpo_2024_praktika.model.LicenseType;
import ru.mtuci.rbpo_2024_praktika.request.LicenseTypeRequest;
import ru.mtuci.rbpo_2024_praktika.service.LicenseTypeService;

@RestController
@RequestMapping("/licensetypes")
@RequiredArgsConstructor
public class LicenseTypeController {

    private final LicenseTypeService licenseTypeService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LicenseType> addLicenseType(@Valid @RequestBody LicenseTypeRequest licenseTypeRequest) {
        try {
            LicenseType createdLicenseType = licenseTypeService.createLicenseType(licenseTypeRequest);
            return new ResponseEntity<>(createdLicenseType, HttpStatus.CREATED);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}