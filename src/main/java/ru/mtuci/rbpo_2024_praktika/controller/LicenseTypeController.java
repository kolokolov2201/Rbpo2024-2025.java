package ru.mtuci.rbpo_2024_praktika.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.mtuci.rbpo_2024_praktika.model.LicenseType;
import ru.mtuci.rbpo_2024_praktika.request.LicenseTypeRequest;
import ru.mtuci.rbpo_2024_praktika.service.LicenseTypeService;

import java.util.List;
//TOD: 1. Дать пользователю возможность получить список типов лицензий !!!!!Сделано

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
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteLicenseType(@PathVariable Long id) {
        try {
            licenseTypeService.deleteLicenseType(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/view")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")//тут
    public ResponseEntity<List<LicenseType>> getAllLicenseTypes() {
        try {
            List<LicenseType> licenseTypes = licenseTypeService.getAllLicenseTypes();
            return new ResponseEntity<>(licenseTypes, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}