package ru.mtuci.rbpo_2024_praktika.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.mtuci.rbpo_2024_praktika.request.ChangeLicenseStatusRequest;
import ru.mtuci.rbpo_2024_praktika.service.LicenseService;

@RequiredArgsConstructor
@RequestMapping("/block")
@RestController
@PreAuthorize("hasAnyRole('ADMIN')")
public class BlockController {

}
