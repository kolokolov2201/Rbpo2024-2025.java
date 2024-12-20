package ru.mtuci.rbpo_2024_praktika.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mtuci.rbpo_2024_praktika.exception.*;
import ru.mtuci.rbpo_2024_praktika.model.*;
import ru.mtuci.rbpo_2024_praktika.repository.DeviceLicenseRepository;
import ru.mtuci.rbpo_2024_praktika.repository.LicenseHistoryRepository;
import ru.mtuci.rbpo_2024_praktika.repository.LicenseRepository;
import ru.mtuci.rbpo_2024_praktika.request.LicenseRequest;
import ru.mtuci.rbpo_2024_praktika.service.LicenseHistoryService;
import ru.mtuci.rbpo_2024_praktika.service.LicenseService;
import ru.mtuci.rbpo_2024_praktika.service.LicenseTypeService;
import ru.mtuci.rbpo_2024_praktika.service.ProductService;
import ru.mtuci.rbpo_2024_praktika.service.UserService;
import ru.mtuci.rbpo_2024_praktika.repository.DeviceRepository;


import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class LicenseServiceImpl implements LicenseService {

    private final LicenseRepository licenseRepository;
    private final ProductService productService;
    private final UserService userService;
    private final LicenseTypeService licenseTypeService;
    private final LicenseHistoryService licenseHistoryService;
    private final TicketGenerator ticketGenerator;
    private final DeviceLicenseRepository deviceLicenseRepository;
    private final LicenseHistoryRepository licenseHistoryRepository;
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
    @Override
    public boolean existsByLicenseTypeId(Long licenseTypeId) {
        return  licenseRepository.existsByLicenseTypeId(licenseTypeId);
    }

    @Override
    public void deleteLicense(Long id) {
        if(!licenseRepository.existsById(id)){
            throw new IllegalArgumentException("License not found for id: " + id);
        }
        licenseRepository.deleteById(id);
    }

    @Override
    public List<License> getAllLicenses() {
        return licenseRepository.findAll();
    }

    @Override
    public Ticket activateLicense(String code, String deviceId, Device device, ApplicationUser applicationUser) {
        Optional<License> optionalLicense = licenseRepository.findByCode(code);

        if (optionalLicense.isPresent()) {
            License license = optionalLicense.get();

            if (!license.isActive()) {
                throw new ActivationNotPossibleException("Лицензия не активна");
            }

            DeviceLicense deviceLicense = new DeviceLicense();
            deviceLicense.setLicense(license);
            deviceLicense.setDevice(device);
            deviceLicense.setActivationDate(new Date());
            deviceLicenseRepository.save(deviceLicense);

            if (license.getFirstActivationDate() == null) {
                license.setFirstActivationDate(new Date());
            }
            license.setBlocked(false);
            licenseRepository.save(license);

            LicenseHistory licenseHistory = new LicenseHistory();
            licenseHistory.setLicense(license);
            licenseHistory.setApplicationUser(applicationUser);
            licenseHistory.setStatus("Activation");
            licenseHistory.setChangeDate(new Date());
            licenseHistory.setDescription("Лицензия активирована на устройстве с ID: " + deviceId);

            licenseHistoryRepository.save(licenseHistory);

            return ticketGenerator.generateTicket(license, device);
        } else {
            throw new LicenseNotFoundException("Лицензия не найдена");
        }
    }


}
