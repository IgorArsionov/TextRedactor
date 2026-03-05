package com.example.textredactor.engine.exception;

public class BufferedWriterException extends RuntimeException {
    public BufferedWriterException(String message) {
        super(message);
    }
    public BufferedWriterException(String message, Throwable cause) {
        super(message, cause);
    }
}
