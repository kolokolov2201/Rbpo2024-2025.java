package ru.mtuci.rbpo_2024_praktika.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.mtuci.rbpo_2024_praktika.exception.ActivationNotPossibleException;
import ru.mtuci.rbpo_2024_praktika.exception.LicenseNotFoundException;
import ru.mtuci.rbpo_2024_praktika.model.ApplicationUser;
import ru.mtuci.rbpo_2024_praktika.model.Device;
import ru.mtuci.rbpo_2024_praktika.model.License;
import ru.mtuci.rbpo_2024_praktika.model.Ticket;
import ru.mtuci.rbpo_2024_praktika.repository.DeviceRepository;
import ru.mtuci.rbpo_2024_praktika.request.ActivationRequest;
import ru.mtuci.rbpo_2024_praktika.request.LicenseRequest;
import ru.mtuci.rbpo_2024_praktika.service.LicenseService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/licenses")
@RequiredArgsConstructor
public class LicenseController {

    private final LicenseService licenseService;
    private final DeviceRepository deviceRepository;

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
    @PostMapping("/activate")
    public ResponseEntity<?> activateLicense(@RequestBody ActivationRequest request) {
        try {
            Long deviceId = Long.parseLong(request.getDeviceId());
            Optional<Device> optionalDevice = deviceRepository.findById(deviceId);
            if (optionalDevice.isEmpty()) {
                return ResponseEntity.badRequest().body("Устройство не найдено");
            }
            Ticket ticket = licenseService.activateLicense(request.getCode(), String.valueOf(deviceId), optionalDevice.get());

            return ResponseEntity.ok(ticket);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Некорректный идентификатор устройства");
        } catch (ActivationNotPossibleException e) {
            return ResponseEntity.badRequest().body("Активация невозможна: " + e.getMessage());
        } catch (LicenseNotFoundException e) {
            return ResponseEntity.badRequest().body("Лицензия не найдена.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
