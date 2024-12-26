package ru.mtuci.rbpo_2024_praktika.exception;

public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String message) {
        super(message);
    }
}