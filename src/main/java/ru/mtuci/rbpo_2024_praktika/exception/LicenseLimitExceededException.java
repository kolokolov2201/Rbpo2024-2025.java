package ru.mtuci.rbpo_2024_praktika.exception;
public class LicenseLimitExceededException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public LicenseLimitExceededException(String message) {
        super(message);
    }

    public LicenseLimitExceededException(String message, Throwable cause) {
        super(message, cause);
    }
}
