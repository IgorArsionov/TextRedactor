package com.example.textredactor.engine.handler.mappers.impl;

import com.example.textredactor.engine.model.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class TextMapper extends AbstractMapper<Text> {
    private Map<String, Consumer<Text>> mappers = new HashMap<>();

    public TextMapper() {
    }

    @Override
    protected void apply(String key, String value) {
        mappers.put("id", text -> text.setText(value));
    }

    @Override
    protected Text createObject() {
        return new Text();
    }
}
