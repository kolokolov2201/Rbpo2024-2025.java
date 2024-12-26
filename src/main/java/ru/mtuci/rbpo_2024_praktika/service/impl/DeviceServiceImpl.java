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
//TODO: 1. Нельзя самим генерировать mac-адрес. Получаем только от клиента и никак иначе !!!!!Сделано

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;
    private final UserService userService;

    @Override
    public Device createDevice(DeviceRequest deviceRequest) {
        Long userId = deviceRequest.getUserId();
        ApplicationUser user = userService.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"));

        String macAddress = deviceRequest.getMacAddress();//тут
        if (macAddress == null || macAddress.isEmpty()) {
            throw new InvalidInputException("MAC address must be provided");
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
            throw new IllegalArgumentException("Device not found for id: " + id);
        }
        deviceRepository.deleteById(id);
    }

    @Override
    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }
}