package ru.mtuci.rbpo_2024_praktika.service;

import ru.mtuci.rbpo_2024_praktika.model.LicenseType;
import java.util.Optional;

public interface LicenseTypeService {
    Optional<LicenseType> findById(Long id);
    LicenseType createLicenseType(ru.mtuci.rbpo_2024_praktika.request.LicenseTypeRequest licenseTypeRequest);

}