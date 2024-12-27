package ru.mtuci.rbpo_2024_praktika.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mtuci.rbpo_2024_praktika.model.License;
import ru.mtuci.rbpo_2024_praktika.model.ApplicationUser;
import ru.mtuci.rbpo_2024_praktika.model.LicenseHistory;
import ru.mtuci.rbpo_2024_praktika.repository.LicenseHistoryRepository;
import ru.mtuci.rbpo_2024_praktika.service.LicenseHistoryService;
import java.util.Date;
import java.util.List;

//TOD: 1. А читать историю нельзя? !!!!Сделано
@Service
@RequiredArgsConstructor
public class LicenseHistoryServiceImpl implements LicenseHistoryService {
    private final LicenseHistoryRepository licenseHistoryRepository;

    @Override
    public List<LicenseHistory> getAllHistory() {
        return licenseHistoryRepository.findAll();
    }

    @Override
    public void recordLicenseChange(License license, ApplicationUser user, String action, String description) {
        LicenseHistory licenseHistory = new LicenseHistory();
        licenseHistory.setLicense(license);
        licenseHistory.setApplicationUser(user);
        licenseHistory.setStatus(action);
        licenseHistory.setChangeDate(new Date());
        licenseHistory.setDescription(description);
        licenseHistoryRepository.save(licenseHistory);
    }
}