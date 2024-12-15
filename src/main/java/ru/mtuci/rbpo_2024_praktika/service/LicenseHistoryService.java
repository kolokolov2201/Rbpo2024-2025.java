package ru.mtuci.rbpo_2024_praktika.service;

import ru.mtuci.rbpo_2024_praktika.model.License;
import ru.mtuci.rbpo_2024_praktika.model.ApplicationUser;
import ru.mtuci.rbpo_2024_praktika.model.LicenseHistory;

public interface LicenseHistoryService {
    void recordLicenseChange(License license, ApplicationUser user, String action, String description);

}