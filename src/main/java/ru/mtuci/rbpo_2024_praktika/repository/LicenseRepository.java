package ru.mtuci.rbpo_2024_praktika.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mtuci.rbpo_2024_praktika.model.License;

import java.util.Optional;

public interface LicenseRepository extends JpaRepository<License, Long> {
    boolean existsByCode(String code);
    boolean existsByLicenseTypeId(Long licenseTypeId);
    Optional<License> findByCode(String code);
}