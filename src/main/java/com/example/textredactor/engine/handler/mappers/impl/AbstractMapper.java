package com.example.textredactor.engine.handler.mappers.impl;

import com.example.textredactor.engine.data.Data;
import com.example.textredactor.engine.exception.MappingProcessException;
import com.example.textredactor.engine.handler.mappers.Mapper;
import com.example.textredactor.engine.model.Text;

public abstract class AbstractMapper<T> implements Mapper {
    protected T currentClass;

    public void map(String value) {
        if (value == null) {
            throw new MappingProcessException("Value cannot be null");
        }
        String parameter = value.trim();
        if (parameter.equals("{")) {
            checkNotExistsObject(currentClass);
            currentClass = createObject();
            return;
        }

        if (parameter.equals("}")) {
            checkExistsObject(currentClass);
            saveObject(currentClass);
            currentClass = null;
            return;
        }
        String[] values = parameter.split(":", 2);

        if (values.length != 2) {
            throw new MappingProcessException("Invalid parameter format");
        }

        String key = values[0];
        String paramValue = values[1];

        apply(key, paramValue);

    }

    protected abstract void apply(String key, String value);
    protected abstract void saveObject(T object);
    protected abstract T createObject();

    protected void checkExists(String value) {
        if (value == null) {
            throw new MappingProcessException("Value cannot be null");
        }
    }

    protected void checkExistsObject(T object) {
        if (object == null) {
            throw new MappingProcessException("Object cannot be null");
        }
    }

    protected void checkNotExistsObject(T object) {
        if (object != null) {
            throw new MappingProcessException("Object already exists");
        }
    }

    @Override
    public String toString() {
        return "AbstractMapper{" +
                "currentClass=" + currentClass +
                '}';
    }
}
