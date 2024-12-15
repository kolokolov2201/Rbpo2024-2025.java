package ru.mtuci.rbpo_2024_praktika.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mtuci.rbpo_2024_praktika.exception.LicenseTypeNotFoundException;
import ru.mtuci.rbpo_2024_praktika.exception.ProductNotFoundException;
import ru.mtuci.rbpo_2024_praktika.exception.UserNotFoundException;
import ru.mtuci.rbpo_2024_praktika.model.ApplicationUser;
import ru.mtuci.rbpo_2024_praktika.model.License;
import ru.mtuci.rbpo_2024_praktika.model.LicenseType;
import ru.mtuci.rbpo_2024_praktika.model.Product;
import ru.mtuci.rbpo_2024_praktika.repository.LicenseRepository;
import ru.mtuci.rbpo_2024_praktika.request.LicenseRequest;
import ru.mtuci.rbpo_2024_praktika.service.LicenseHistoryService;
import ru.mtuci.rbpo_2024_praktika.service.LicenseService;
import ru.mtuci.rbpo_2024_praktika.service.LicenseTypeService;
import ru.mtuci.rbpo_2024_praktika.service.ProductService;
import ru.mtuci.rbpo_2024_praktika.service.UserService;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class LicenseServiceImpl implements LicenseService {

    private final LicenseRepository licenseRepository;
    private final ProductService productService;
    private final UserService userService;
    private final LicenseTypeService licenseTypeService;
    private final LicenseHistoryService licenseHistoryService;


    @Override
    public License createLicense(LicenseRequest licenseRequest) {
        Long productId = licenseRequest.getProductId();
        Long ownerId = licenseRequest.getOwnerId();
        Long licenseTypeId = licenseRequest.getLicenseTypeId();
        Integer deviceCount = licenseRequest.getDeviceCount();

        Product product = productService.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + productId + " not found"));
        ApplicationUser user = userService.findById(ownerId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + ownerId + " not found"));
        LicenseType licenseType = licenseTypeService.findById(licenseTypeId)
                .orElseThrow(() -> new LicenseTypeNotFoundException("License type with ID " + licenseTypeId + " not found"));

        String activationCode;
        do {
            activationCode = UUID.randomUUID().toString();
        } while (licenseRepository.existsByCode(activationCode));

        License license = new License();
        license.setProduct(product);
        license.setApplicationUser(user);
        license.setOwner(user);
        license.setLicenseType(licenseType);
        license.setDeviceCount(deviceCount != null ? deviceCount : 0);
        license.setCode(activationCode);
        license.setBlocked(false);
        license.setFirstActivationDate(null);
        license.setEndingDate(null);

        License savedLicense = licenseRepository.save(license);
        licenseHistoryService.recordLicenseChange(savedLicense, user, "Creation", "License successfully created");
        return savedLicense;
    }
}