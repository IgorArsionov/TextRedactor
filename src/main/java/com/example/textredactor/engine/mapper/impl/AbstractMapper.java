package com.example.textredactor.engine.mapper.impl;

import com.example.textredactor.engine.exception.InvalidFileFormatException;
import com.example.textredactor.engine.file.AbstractFileManager;
import com.example.textredactor.engine.mapper.Mapper;

public abstract class AbstractMapper<T> implements Mapper {
    protected T currentObject;
    @Override
    public void process(String value) {
        if (value == null) {
            throw new InvalidFileFormatException("Key is null");
        }

        value = value.trim();

        if (value.equals("{")) {
            checkObjectNotExists();
            currentObject = createObject();
            return;
        }

        if (value.equals("}")) {
            checkObjectExists();
            saveObject(currentObject);
            currentObject = null;
            return;
        }

        String[] parts = value.split(":", 2);
        if (parts.length != 2) {
            throw new InvalidFileFormatException("Invalid key: " + value);
        }
        String key = parts[0].trim();
        String field = AbstractFileManager.decodeText(parts[1].trim());

        applyField(key, field);
    }

    protected abstract T createObject();
    protected abstract void saveObject(T object);
    protected abstract void applyField(String key, String value);

    protected void checkObjectExists(String key) {
        if (currentObject == null) {
            throw new InvalidFileFormatException("Field '" + key + "' is outside object");
        }
    }

    protected void checkObjectExists() {
        if (currentObject == null) {
            throw new InvalidFileFormatException("Cannot close object before opening it");
        }
    }

    protected void checkObjectNotExists() {
        if (currentObject != null) {
            throw new InvalidFileFormatException("Cannot open new object before closing previous one");
        }
    }

    protected void checkValueExists(String key, String value) {
        if (value.trim().isEmpty()) {
            throw new InvalidFileFormatException("Field '" + key + "' has empty value");
        }
    }
}
