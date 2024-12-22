package ru.mtuci.rbpo_2024_praktika.exception;

public class LicenseNotFoundException extends RuntimeException {

    public LicenseNotFoundException(String message) {
        super(message);
    }
}