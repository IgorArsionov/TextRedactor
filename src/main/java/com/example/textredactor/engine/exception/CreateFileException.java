package com.example.textredactor.engine.exception;

public class CreateFileException extends RuntimeException {
    public CreateFileException(String message) {
        super(message);
    }
    public CreateFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
