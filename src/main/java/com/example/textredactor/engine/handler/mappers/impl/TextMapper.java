package com.example.textredactor.engine.handler.mappers.impl;

import com.example.textredactor.engine.model.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class TextMapper extends AbstractMapper<Text> {
    private Map<String, Consumer<String>> mappers = new HashMap<>();

    public TextMapper() {
        mappers.put("id", v -> {
            //TODO: checkout
            currentClass.setId(Integer.parseInt(v));
        });
        mappers.put("text", v -> {
            //TODO: checkout
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
    protected Text createObject() {
        return new Text();
    }
}
