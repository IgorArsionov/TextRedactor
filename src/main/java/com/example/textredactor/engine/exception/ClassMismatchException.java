package com.example.textredactor.engine.exception;

public class ClassMismatchException extends RuntimeException {
    public ClassMismatchException(String message) {
        super(message);
    }
    public ClassMismatchException(String message, Throwable cause) {
        super(message, cause);
    }
}
