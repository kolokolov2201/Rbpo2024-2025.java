package ru.mtuci.rbpo_2024_praktika.service;

import ru.mtuci.rbpo_2024_praktika.model.Device;
import ru.mtuci.rbpo_2024_praktika.request.DeviceRequest;

import java.util.List;

public interface DeviceService {
    Device createDevice(DeviceRequest deviceRequest);
    void deleteDevice(Long id);
    List<Device> getAllDevices();
}