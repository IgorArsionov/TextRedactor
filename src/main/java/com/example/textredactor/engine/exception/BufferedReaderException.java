package com.example.textredactor.engine.exception;

public class BufferedReaderException extends RuntimeException {
    public BufferedReaderException(String message) {
        super(message);
    }
    public BufferedReaderException(String message, Throwable cause) {
        super(message, cause);
    }
}
