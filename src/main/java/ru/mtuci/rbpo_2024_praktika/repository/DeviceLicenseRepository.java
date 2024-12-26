package ru.mtuci.rbpo_2024_praktika.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mtuci.rbpo_2024_praktika.model.Device;
import ru.mtuci.rbpo_2024_praktika.model.DeviceLicense;
import ru.mtuci.rbpo_2024_praktika.model.License;

import java.util.Optional;

@Repository
public interface DeviceLicenseRepository extends JpaRepository<DeviceLicense, Long> {
    boolean existsByLicenseIdAndDeviceId(Long licenseId, Long deviceId);
    long countByLicense(License license);
    Optional<DeviceLicense> findByLicenseAndDevice(License license, Device device);
}