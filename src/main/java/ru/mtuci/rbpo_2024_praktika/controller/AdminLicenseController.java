package ru.mtuci.rbpo_2024_praktika.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.mtuci.rbpo_2024_praktika.exception.LicenseNotFoundException;
import ru.mtuci.rbpo_2024_praktika.exception.LicenseStateException;
import ru.mtuci.rbpo_2024_praktika.service.LicenseService;

@RestController
@RequestMapping("/admin/licenses")
public class AdminLicenseController {

    @Autowired
    private LicenseService licenseService;

    @PostMapping("/{licenseId}/block")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> blockLicense(@PathVariable Long licenseId) {
        try {
            licenseService.blockLicense(licenseId);
            return ResponseEntity.ok("Лицензия успешно заблокирована.");
        } catch (LicenseNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Лицензия не найдена.");
        } catch (LicenseStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Невозможно заблокировать лицензию в текущем состоянии: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Произошла ошибка при блокировке лицензии: " + e.getMessage());
        }
    }


    @PostMapping("/{licenseId}/unblock")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> unblockLicense(@PathVariable Long licenseId) {
        try {
            licenseService.unblockLicense(licenseId);
            return ResponseEntity.ok("Лицензия успешно разблокирована.");
        }  catch (LicenseNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Лицензия не найдена.");
        } catch (LicenseStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Невозможно разблокировать лицензию в текущем состоянии: " + e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Произошла ошибка при разблокировке лицензии: " + e.getMessage());
        }
    }
}