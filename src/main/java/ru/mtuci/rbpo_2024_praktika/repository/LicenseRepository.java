package ru.mtuci.rbpo_2024_praktika.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mtuci.rbpo_2024_praktika.model.License;

public interface LicenseRepository extends JpaRepository<License, Long> {
    boolean existsByCode(String code);
}