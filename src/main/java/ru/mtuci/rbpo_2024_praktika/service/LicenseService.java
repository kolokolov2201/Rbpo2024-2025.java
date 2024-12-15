package ru.mtuci.rbpo_2024_praktika.service;

import ru.mtuci.rbpo_2024_praktika.model.License;
import ru.mtuci.rbpo_2024_praktika.request.LicenseRequest;

public interface LicenseService {
    License createLicense(LicenseRequest licenseRequest);
}