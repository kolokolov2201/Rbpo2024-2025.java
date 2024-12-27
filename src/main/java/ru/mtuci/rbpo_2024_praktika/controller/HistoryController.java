package ru.mtuci.rbpo_2024_praktika.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mtuci.rbpo_2024_praktika.model.LicenseHistory;
import ru.mtuci.rbpo_2024_praktika.service.LicenseHistoryService;

import java.util.List;

@RestController
@RequestMapping("/license")
@PreAuthorize("hasRole('ADMIN')")
public class HistoryController {

    @Autowired
    private LicenseHistoryService licenseHistoryService;

    @GetMapping("/history")
    public ResponseEntity<List<LicenseHistory>> getAllLicenseHistory() {
        try {
            List<LicenseHistory> historyEntries = licenseHistoryService.getAllHistory();
            return new ResponseEntity<>(historyEntries, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
