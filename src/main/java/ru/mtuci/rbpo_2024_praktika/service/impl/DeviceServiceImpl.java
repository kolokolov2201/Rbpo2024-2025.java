package ru.mtuci.rbpo_2024_praktika.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mtuci.rbpo_2024_praktika.exception.UserNotFoundException;
import ru.mtuci.rbpo_2024_praktika.model.ApplicationUser;
import ru.mtuci.rbpo_2024_praktika.model.Device;
import ru.mtuci.rbpo_2024_praktika.repository.DeviceRepository;
import ru.mtuci.rbpo_2024_praktika.request.DeviceRequest;
import ru.mtuci.rbpo_2024_praktika.service.DeviceService;
import ru.mtuci.rbpo_2024_praktika.service.UserService;

import java.util.List;
import java.util.Random;


@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;
    private final UserService userService;
    private static final Random random = new Random();

    @Override
    public Device createDevice(DeviceRequest deviceRequest) {
        Long userId = deviceRequest.getUserId();
        ApplicationUser user = userService.findById(userId)
                .orElseThrow(()-> new UserNotFoundException("User with id" + userId + " not found"));

        Device device = new Device();
        device.setName(deviceRequest.getName());
        String macAddress = deviceRequest.getMacAddress();
        if (macAddress == null || macAddress.isEmpty()) {
            macAddress = generateRandomMacAddress();
        }
        device.setMacAddress(macAddress);
        device.setApplicationUser(user);
        return deviceRepository.save(device);
    }
    private String generateRandomMacAddress() {
        byte[] macAddressBytes = new byte[6];
        random.nextBytes(macAddressBytes);
        macAddressBytes[0] = (byte) (macAddressBytes[0] | 0x02); // Set locally administered bit
        StringBuilder macAddress = new StringBuilder();
        for (byte b : macAddressBytes) {
            macAddress.append(String.format("%02X-", b));
        }
        macAddress.deleteCharAt(macAddress.length() - 1);
        return macAddress.toString();
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