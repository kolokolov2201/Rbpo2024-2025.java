package ru.mtuci.rbpo_2024_praktika.controller;

import lombok.RequiredArgsConstructor;
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

    @GetMapping("/license")
    public ResponseEntity<?> getLicenseInfo(@RequestBody DeviceInfoRequest deviceInfoRequest) {
        if (deviceInfoRequest == null || deviceInfoRequest.getMacAddress() == null || deviceInfoRequest.getMacAddress().isEmpty()) {
            return ResponseEntity.badRequest().body("MAC-адрес не может быть пустым");
        }
        String macAddress = deviceInfoRequest.getMacAddress();

        try {
            List<Ticket> tickets = licenseService.getLicenseInfo(macAddress);
            if(tickets == null || tickets.isEmpty()){
                return ResponseEntity.status(404).body("Лицензия не найдена для указанного MAC-адреса: " + macAddress);
            }
            return ResponseEntity.ok(tickets);
        } catch (LicenseNotFoundException e) {
            return ResponseEntity.status(404).body("Лицензия не найдена для указанного MAC-адреса: " + macAddress);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Ошибка: " + e.getMessage());
        }
    }
}
