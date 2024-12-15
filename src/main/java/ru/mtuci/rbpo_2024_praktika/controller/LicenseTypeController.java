package ru.mtuci.rbpo_2024_praktika.controller;

import ru.mtuci.rbpo_2024_praktika.request.LicenseTypeAddRequest;
import ru.mtuci.rbpo_2024_praktika.model.LicenseType;
import ru.mtuci.rbpo_2024_praktika.service.LicenseService;
import ru.mtuci.rbpo_2024_praktika.service.LicenseTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@RestController
@RequestMapping("/license-type")
public class LicenseTypeController {
}
