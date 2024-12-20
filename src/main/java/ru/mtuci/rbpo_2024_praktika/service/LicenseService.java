package ru.mtuci.rbpo_2024_praktika.service;

import ru.mtuci.rbpo_2024_praktika.model.ApplicationUser;
import ru.mtuci.rbpo_2024_praktika.model.Device;
import ru.mtuci.rbpo_2024_praktika.model.License;
import ru.mtuci.rbpo_2024_praktika.model.Ticket;
import ru.mtuci.rbpo_2024_praktika.request.LicenseRequest;

import java.util.List;

public interface LicenseService {
    License createLicense(LicenseRequest licenseRequest);
    void deleteLicense(Long id);
    List<License> getAllLicenses();
    boolean existsByLicenseTypeId(Long licenseTypeId);
    Ticket activateLicense(String code, String deviceId, Device device);
}