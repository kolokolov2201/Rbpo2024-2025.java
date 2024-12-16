package ru.mtuci.rbpo_2024_praktika.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mtuci.rbpo_2024_praktika.model.LicenseType;
import ru.mtuci.rbpo_2024_praktika.repository.LicenseTypeRepository;
import ru.mtuci.rbpo_2024_praktika.request.LicenseTypeRequest;
import ru.mtuci.rbpo_2024_praktika.service.LicenseTypeService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LicenseTypeServiceImpl implements LicenseTypeService {

    private final LicenseTypeRepository licenseTypeRepository;
    @Override
    public Optional<LicenseType> findById(Long id) {
        return licenseTypeRepository.findById(id);
    }

    @Override
    public LicenseType createLicenseType(LicenseTypeRequest licenseTypeRequest) {
        LicenseType licenseType = new LicenseType();
        licenseType.setName(licenseTypeRequest.getName());
        licenseType.setDefaultDuration(licenseTypeRequest.getDefaultDuration());
        licenseType.setDescription(licenseTypeRequest.getDescription());
        return licenseTypeRepository.save(licenseType);
    }
    @Override
    public void deleteLicenseType(Long id) {
        if (!licenseTypeRepository.existsById(id)) {
            throw new IllegalArgumentException("License type not found for id: " + id);
        }
        licenseTypeRepository.deleteById(id);
    }
    @Override
    public List<LicenseType> getAllLicenseTypes() {
        return licenseTypeRepository.findAll();
    }
}