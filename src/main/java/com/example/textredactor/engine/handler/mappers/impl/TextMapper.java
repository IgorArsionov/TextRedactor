package com.example.textredactor.engine.handler.mappers.impl;

import com.example.textredactor.engine.data.Data;
import com.example.textredactor.engine.model.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class TextMapper extends AbstractMapper<Text> {
    private Map<String, Consumer<String>> mappers = new HashMap<>();

    public TextMapper() {
        mappers.put("id", v -> {
            checkExists(v);
            currentClass.setId(Integer.parseInt(v));
        });
        mappers.put("title", v -> {
            checkExists(v);
            currentClass.setTitle(v);
        });
        mappers.put("text", v -> {
            checkExists(v);
            currentClass.setText(v);
        });
    }

    @Override
    protected void apply(String key, String value) {
        Consumer<String> mapper = mappers.get(key);
        if (mapper != null) {
            mapper.accept(value);
        }
    }

    @Override
    protected void saveObject(Text object) {
        checkExistsObject(object);
        Data.TEXTS.add(object);
    }

    @Override
    protected Text createObject() {
        return new Text();
    }
}
