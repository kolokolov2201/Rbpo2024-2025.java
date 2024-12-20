package ru.mtuci.rbpo_2024_praktika.exception;

public class DeviceNotFoundException extends RuntimeException {

    public DeviceNotFoundException(String message) {
        super(message);
    }
}