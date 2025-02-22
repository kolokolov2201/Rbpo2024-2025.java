package ru.mtuci.rbpo_2024_praktika.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mtuci.rbpo_2024_praktika.exception.InvalidInputException;
import ru.mtuci.rbpo_2024_praktika.exception.UserNotFoundException;
import ru.mtuci.rbpo_2024_praktika.model.ApplicationUser;
import ru.mtuci.rbpo_2024_praktika.model.Device;
import ru.mtuci.rbpo_2024_praktika.repository.DeviceRepository;
import ru.mtuci.rbpo_2024_praktika.request.DeviceRequest;
import ru.mtuci.rbpo_2024_praktika.service.DeviceService;
import ru.mtuci.rbpo_2024_praktika.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;
    private final UserService userService;

    @Override
    public Device createDevice(DeviceRequest deviceRequest) {
        Long userId = deviceRequest.getUserId();
        ApplicationUser user = userService.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с id " + userId + " не найден"));

        String macAddress = deviceRequest.getMacAddress();
        if (macAddress == null || macAddress.isEmpty()) {
            throw new InvalidInputException("MAC-адрес должен быть указан");
        }

        Device device = new Device();
        device.setName(deviceRequest.getName());
        device.setMacAddress(macAddress);
        device.setApplicationUser(user);

        return deviceRepository.save(device);
    }

    @Override
    public void deleteDevice(Long id) {
        if(!deviceRepository.existsById(id)){
            throw new IllegalArgumentException("Устройство не найдено по id: " + id);
        }
        deviceRepository.deleteById(id);
    }


    @Override
    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }
}