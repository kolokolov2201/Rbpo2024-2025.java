package ru.mtuci.rbpo_2024_praktika.controller;

import org.springframework.web.bind.annotation.*;
import ru.mtuci.rbpo_2024_praktika.request.DeviceAddRequest;
import ru.mtuci.rbpo_2024_praktika.model.ApplicationUser;
import ru.mtuci.rbpo_2024_praktika.model.Device;
import ru.mtuci.rbpo_2024_praktika.service.DeviceService;
import ru.mtuci.rbpo_2024_praktika.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import java.net.SocketException;

@RequiredArgsConstructor
@RequestMapping("/device")
@RestController
public class DeviceController {


}
