package ru.mtuci.rbpo_2024_praktika.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mtuci.rbpo_2024_praktika.exception.LicenseNotFoundException;
import ru.mtuci.rbpo_2024_praktika.model.Ticket;
import ru.mtuci.rbpo_2024_praktika.request.DeviceInfoRequest;
import ru.mtuci.rbpo_2024_praktika.service.LicenseService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/info")
@RestController
public class LicenseInfoController {
    private final LicenseService licenseService;

    @PostMapping("/license")
    public ResponseEntity<Ticket> getLicenseInfo(@RequestBody DeviceInfoRequest deviceInfoRequest) {
        Ticket ticket = licenseService.getLicenseInfo(deviceInfoRequest);

        if (ticket != null) {
            return ResponseEntity.ok(ticket);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
