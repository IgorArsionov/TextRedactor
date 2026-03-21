package com.example.textredactor.engine.handler.mappers;

import com.example.textredactor.engine.exception.MappingProcessException;

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

        apply(parameter);

    }

    protected abstract void apply(String value);
    protected abstract T createObject();
}
