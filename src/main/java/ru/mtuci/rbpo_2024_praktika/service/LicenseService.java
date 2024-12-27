package ru.mtuci.rbpo_2024_praktika.service;

import ru.mtuci.rbpo_2024_praktika.model.Device;
import ru.mtuci.rbpo_2024_praktika.model.License;
import ru.mtuci.rbpo_2024_praktika.model.Ticket;
import ru.mtuci.rbpo_2024_praktika.request.DeviceInfoRequest;
import ru.mtuci.rbpo_2024_praktika.request.LicenseRequest;
import ru.mtuci.rbpo_2024_praktika.request.UpdateLicenseRequest;

import java.util.List;

public interface LicenseService {
    License createLicense(LicenseRequest licenseRequest);
    void deleteLicense(Long id);
    List<License> getAllLicenses();
    Ticket activateLicense(String code, String deviceId, Device device);
    Ticket getLicenseInfo(DeviceInfoRequest deviceInfoRequest);
    void blockLicense(Long licenseId);
    void unblockLicense(Long licenseId);
    Ticket renewLicense(UpdateLicenseRequest updateLicenseRequest);
}