package ru.mtuci.rbpo_2024_praktika.exception;
public class LicenseAlreadyActivatedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public LicenseAlreadyActivatedException(String message) {
        super(message);
    }

    public LicenseAlreadyActivatedException(String message, Throwable cause) {
        super(message, cause);
    }
}
