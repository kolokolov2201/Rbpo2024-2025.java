package ru.mtuci.rbpo_2024_praktika.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mtuci.rbpo_2024_praktika.model.ApplicationUser;
import ru.mtuci.rbpo_2024_praktika.model.License;

import java.util.Optional;
import java.util.List;

public interface LicenseRepository extends JpaRepository<License, Long> {
    boolean existsByCode(String code);
    boolean existsByLicenseTypeId(Long licenseTypeId);
    List<License> findByApplicationUser(ApplicationUser applicationUser);
    Optional<License> findByCode(String code);
}