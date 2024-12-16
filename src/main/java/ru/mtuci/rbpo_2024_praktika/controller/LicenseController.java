package ru.mtuci.rbpo_2024_praktika.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.mtuci.rbpo_2024_praktika.model.ApplicationUser;
import ru.mtuci.rbpo_2024_praktika.model.License;
import ru.mtuci.rbpo_2024_praktika.request.LicenseRequest;
import ru.mtuci.rbpo_2024_praktika.service.LicenseService;

import java.util.List;

@RestController
@RequestMapping("/licenses")
@RequiredArgsConstructor
public class LicenseController {

    private final LicenseService licenseService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<License> addLicense(@Valid @RequestBody LicenseRequest licenseRequest) {
        try {
            License createdLicense = licenseService.createLicense(licenseRequest);
            return new ResponseEntity<>(createdLicense, HttpStatus.CREATED);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteLicense(@PathVariable Long id) {
        try {
            licenseService.deleteLicense(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/view")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<License>> getAllLicenses() {
        try {
            List<License> licenses = licenseService.getAllLicenses();
            return new ResponseEntity<>(licenses, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
