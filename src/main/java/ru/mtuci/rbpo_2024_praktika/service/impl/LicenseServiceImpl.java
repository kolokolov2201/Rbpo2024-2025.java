package ru.mtuci.rbpo_2024_praktika.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mtuci.rbpo_2024_praktika.exception.*;
import ru.mtuci.rbpo_2024_praktika.model.*;
import ru.mtuci.rbpo_2024_praktika.repository.DeviceLicenseRepository;
import ru.mtuci.rbpo_2024_praktika.repository.LicenseHistoryRepository;
import ru.mtuci.rbpo_2024_praktika.repository.LicenseRepository;
import ru.mtuci.rbpo_2024_praktika.request.DeviceInfoRequest;
import ru.mtuci.rbpo_2024_praktika.request.LicenseRequest;
import ru.mtuci.rbpo_2024_praktika.request.UpdateLicenseRequest;
import ru.mtuci.rbpo_2024_praktika.service.LicenseHistoryService;
import ru.mtuci.rbpo_2024_praktika.service.LicenseService;
import ru.mtuci.rbpo_2024_praktika.service.LicenseTypeService;
import ru.mtuci.rbpo_2024_praktika.service.ProductService;
import ru.mtuci.rbpo_2024_praktika.service.UserService;
import ru.mtuci.rbpo_2024_praktika.repository.DeviceRepository;

//TOD: 1. createLicense - user должен устанавливаться только при первой активации Сделал
//TOD: 2. activateLicense - запись в deviceLicense должна происходить только если такой записи ещё нет !!!!!Сделано
//TOD: 3. activateLicense - дата окончания у вас пересчитывается с каждой повторной активацией? !!!!!Сделано
//TOD: 4. activateLicense - нарушена последовательность действий при активации. Вы сначала сохраняете информацию, а потом делаете проверки. !!!!!Сделано
//TOD: 5. activateLicense - нет проверки, что не превышено максимально доступное число устройств лицензии !!!!!Сделано
//TOD: 6. activateLicense - Пользователь должен иметь возможность активировать лицензию единожды на каждом своём устройстве в рамках лимита устройств !!!!!Сделано
//TOD: 7. getLicenseInfo - должен возвращаться один тикет, а не список !!!!!Сделано
//TOD: 8. renewLicense - все действия с лицензией должен проводить либо владелец, либо пользователь лицензии. И никто другой !!!!!Сделано

import java.util.*;


@Service
@RequiredArgsConstructor
public class LicenseServiceImpl implements LicenseService {

    private final LicenseRepository licenseRepository;
    private final ProductService productService;
    private final UserService userService;
    private final LicenseTypeService licenseTypeService;
    private final LicenseHistoryService licenseHistoryService;
    private final TicketGenerator ticketGenerator;
    private final DeviceLicenseRepository deviceLicenseRepository;
    private final LicenseHistoryRepository licenseHistoryRepository;
    private final DeviceRepository deviceRepository;
    @Override
    public License createLicense(LicenseRequest licenseRequest) {
        Long productId = licenseRequest.getProductId();
        Long ownerId = licenseRequest.getOwnerId();
        Long licenseTypeId = licenseRequest.getLicenseTypeId();
        Integer deviceCount = licenseRequest.getDeviceCount();


        Product product = productService.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + productId + " not found"));
        ApplicationUser user = userService.findById(ownerId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + ownerId + " not found"));
        LicenseType licenseType = licenseTypeService.findById(licenseTypeId)
                .orElseThrow(() -> new LicenseTypeNotFoundException("License type with ID " + licenseTypeId + " not found"));

        String activationCode;
        do {
            activationCode = UUID.randomUUID().toString();
        } while (licenseRepository.existsByCode(activationCode));

        License license = new License();
        license.setProduct(product);
        license.setOwner(user);
        license.setLicenseType(licenseType);
        license.setDeviceCount(deviceCount != null ? deviceCount : 0);
        license.setCode(activationCode);
        license.setBlocked(false);
        license.setFirstActivationDate(null);
        license.setEndingDate(null);
        license.setDuration(licenseType.getDefaultDuration());
        license.setDescription(licenseRequest.getDescription());

        License savedLicense = licenseRepository.save(license);
        licenseHistoryService.recordLicenseChange(savedLicense, user, "Creation", "License successfully created");
        return savedLicense;
    }

    @Override
    public void deleteLicense(Long id) {
        if(!licenseRepository.existsById(id)){
            throw new IllegalArgumentException("License not found for id: " + id);
        }
        licenseRepository.deleteById(id);
    }

    @Override
    public List<License> getAllLicenses() {
        return licenseRepository.findAll();
    }

    @Override
    public Ticket activateLicense(String code, String deviceId, Device device) {
        Optional<License> optionalLicense = licenseRepository.findByCode(code);
        ApplicationUser authenticatedUser = userService.getAuthenticatedUser();

        if (authenticatedUser == null) {
            throw new IllegalArgumentException("Пользователь не аутентифицирован");
        }

        if (optionalLicense.isPresent()) {
            License license = optionalLicense.get();
            if (license.getApplicationUser() == null) {
                license.setApplicationUser(authenticatedUser);
                licenseRepository.save(license);
            }
            if (!license.getOwner().equals(authenticatedUser) && !license.getApplicationUser().equals(authenticatedUser)) {
                throw new IllegalArgumentException("У вас нет прав на активацию этой лицензии");
            }
            if (!license.isActive()) {
                throw new ActivationNotPossibleException("Лицензия не активна");
            }

            Optional<Device> optionalDevice = deviceRepository.findById(device.getId());
            if (optionalDevice.isEmpty()) {
                throw new DeviceNotFoundException("Устройство с ID " + device.getId() + " не найдено");
            }
            Device actualDevice = optionalDevice.get();

            long activeDeviceCount = deviceLicenseRepository.countByLicense(license);
            if (activeDeviceCount >= license.getDeviceCount()) {
                throw new LicenseLimitExceededException("Достигнуто максимальное количество устройств для этой лицензии");
            }

            Optional<DeviceLicense> existingDeviceLicense = deviceLicenseRepository.findByLicenseAndDevice(license, actualDevice);
            if (existingDeviceLicense.isPresent()) {
                throw new LicenseAlreadyActivatedException("Лицензия уже активирована на этом устройстве");
            }

            Date firstActivationDate = license.getFirstActivationDate();
            if (firstActivationDate == null) {
                firstActivationDate = new Date();
                license.setFirstActivationDate(firstActivationDate);
                var endingDate = calculateEndingDate(firstActivationDate, license.getDuration());
                license.setEndingDate(endingDate);
            }

            license.setBlocked(false);

            DeviceLicense deviceLicense = new DeviceLicense();
            deviceLicense.setLicense(license);
            deviceLicense.setDevice(actualDevice);
            deviceLicense.setActivationDate(new Date());
            deviceLicenseRepository.save(deviceLicense);

            License savedLicense = licenseRepository.save(license);
            ApplicationUser applicationUser = actualDevice.getApplicationUser();
            licenseHistoryService.recordLicenseChange(savedLicense, applicationUser, "Активация", "Лицензия успешно активирована");
            return ticketGenerator.generateTicket(license, actualDevice);

        } else {
            throw new LicenseNotFoundException("Лицензия не найдена");
        }
    }

    private Date calculateEndingDate(Date startDate, int duration) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DAY_OF_YEAR, duration);
        return calendar.getTime();
    }
//сделать проверку еще по чему то
    @Override
    public Ticket getLicenseInfo(DeviceInfoRequest deviceInfoRequest) {
        String macAddress = deviceInfoRequest.getMacAddress();
        Optional<Device> optionalDevice = deviceRepository.findByMacAddress(macAddress);

        if (optionalDevice.isPresent()) {
            Device device = optionalDevice.get();
            List<License> licenses = licenseRepository.findByApplicationUser(device.getApplicationUser());

            if (!licenses.isEmpty()) {
                License firstLicense = licenses.get(0);
                return ticketGenerator.generateTicket(firstLicense, device);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public void blockLicense(Long licenseId) {
        License license = licenseRepository.findById(licenseId)
                .orElseThrow(() -> new LicenseNotFoundException("Лицензия не найдена."));

        if (license.getBlocked()) {
            throw new LicenseStateException("Лицензия уже заблокирована.");
        }

        license.setBlocked(true);
        licenseRepository.save(license);
    }

    @Override
    public void unblockLicense(Long licenseId) {
        License license = licenseRepository.findById(licenseId)
                .orElseThrow(() -> new LicenseNotFoundException("Лицензия не найдена."));

        if (!license.getBlocked()) {
            throw new LicenseStateException("Лицензия уже разблокирована.");
        }


        license.setBlocked(false);
        licenseRepository.save(license);
    }
    @Override
    public Ticket renewLicense(UpdateLicenseRequest updateLicenseRequest) {
        ApplicationUser authenticatedUser = userService.getAuthenticatedUser();

        if (authenticatedUser == null) {
            throw new IllegalArgumentException("Пользователь не аутентифицирован");
        }

        License license = getByCode(updateLicenseRequest.getCode());
        if (license == null) {
            throw new IllegalArgumentException("Лицензия не найдена");
        }

        if (!license.getOwner().equals(authenticatedUser) || !license.getApplicationUser().equals(authenticatedUser)) {
            throw new IllegalArgumentException("У вас нет прав на продление этой лицензии");
        }

        Device device = deviceRepository.findByMacAddress(updateLicenseRequest.getMacAddress())
                .orElseThrow(() -> new IllegalArgumentException("Устройство не найдено."));

        boolean isLinked = deviceLicenseRepository.existsByLicenseIdAndDeviceId(license.getId(), device.getId());
        if (!isLinked) {
            throw new IllegalArgumentException("Устройство не связано с данной лицензией.");
        }

        Date newEndingDate = null;
        if (license.getDuration() != null && license.getDuration() > 0) {
            newEndingDate = calculateEndingDate(license.getEndingDate(), license.getDuration());
        }

        license.setEndingDate(newEndingDate);
        licenseRepository.save(license);
        recordLicenseChange(
                license,
                "продление",
                "Новая дата истечения: " + newEndingDate
        );
        return ticketGenerator.generateTicket(license, device);
    }

    private void recordLicenseChange(License license, String status, String description){
        LicenseHistory licenseHistory = new LicenseHistory(
                null,
                license,
                license.getApplicationUser(),
                status,
                new Date(),
                description
        );
        licenseHistoryRepository.save(licenseHistory);
    }
    private License getByCode(String key) {
        return licenseRepository.findByCode(key).orElse(null);
    }
}