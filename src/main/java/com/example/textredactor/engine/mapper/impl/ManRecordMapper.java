package com.example.textredactor.engine.mapper.impl;

import com.example.textredactor.engine.data.Data;
import com.example.textredactor.engine.exception.InvalidFileFormatException;
import com.example.textredactor.engine.model.Man;
import com.example.textredactor.engine.model.ManRecord;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ManRecordMapper extends AbstractMapper<ManRecord> {
    private final Map<String, Consumer<String>> setters;

    public ManRecordMapper() {
        setters = new HashMap<>();
        setters.put("manId", v -> {
            checkObjectExists("manId");
            checkValueExists("manId", v);
            currentObject.setManId(Integer.parseInt(v));
        });
        setters.put("text", v -> {
            checkObjectExists("text");
            currentObject.setText(v);
        });
    }

    @Override
    protected ManRecord createObject() {
        return new ManRecord();
    }

    @Override
    protected void saveObject(ManRecord object) {
        Man man = Data.getInstance().getMan(object.getManId() - 1);
        man.getManRecordList().add(object);
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
