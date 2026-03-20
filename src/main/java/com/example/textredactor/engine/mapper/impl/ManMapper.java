package com.example.textredactor.engine.mapper.impl;

import com.example.textredactor.engine.data.Data;
import com.example.textredactor.engine.exception.InvalidFileFormatException;
import com.example.textredactor.engine.model.Man;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ManMapper extends AbstractMapper<Man> {
    private final Map<String, Consumer<String>> setters;
    private final Data data = Data.getInstance();

    public ManMapper() {
        setters = new HashMap<>();
        setters.put("name", v -> {
            checkObjectExists("name");
            currentObject.setName(v);
        });
        setters.put("country", v -> {
            checkObjectExists("country");
            currentObject.setCountry(v);
        });
        setters.put("city", v -> {
            checkObjectExists("city");
            currentObject.setCity(v);
        });
        setters.put("description", v -> {
            checkObjectExists("description");
            currentObject.setDescription(v);
        });
    }

    @Override
    protected Man createObject() {
        return new Man();
    }

    @Override
    protected void saveObject(Man object) {
        data.addMan(object);
    }

    @Override
    protected void applyField(String key, String value) {

        Consumer<String> setter = setters.get(key);
        if (setter == null) {
            throw new InvalidFileFormatException("Unknown field: " + key);
        }
        checkValueExists(key, value);
        setters.get(key).accept(value);
    }
}
