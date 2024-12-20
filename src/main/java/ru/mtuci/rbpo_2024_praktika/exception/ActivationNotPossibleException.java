package ru.mtuci.rbpo_2024_praktika.exception;

public class ActivationNotPossibleException extends RuntimeException {

    public ActivationNotPossibleException(String message) {
        super(message);
    }

    public ActivationNotPossibleException(String message, Throwable cause) {
        super(message, cause);
    }
}
