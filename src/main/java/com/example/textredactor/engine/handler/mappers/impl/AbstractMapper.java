package com.example.textredactor.engine.handler.mappers.impl;

import com.example.textredactor.engine.exception.MappingProcessException;
import com.example.textredactor.engine.handler.mappers.Mapper;

public abstract class AbstractMapper<T> implements Mapper {
    protected T currentClass;

    public void map(String value) {
        if (value == null) {
            throw new MappingProcessException("Value cannot be null");
        }
        String parameter = value.trim();
        if (parameter.equals("{")) {
            //TODO: check if it exists
            currentClass = createObject();
        }

        if (parameter.equals("}")) {
            //TODO: check if it already not exists
            currentClass = null;
        }
        String key = parameter.split(":", 1)[0];
        String paramValue = parameter.split(":", 1)[1];
        apply(key, paramValue);

    }

    protected abstract void apply(String key, String value);
    protected abstract T createObject();
}
