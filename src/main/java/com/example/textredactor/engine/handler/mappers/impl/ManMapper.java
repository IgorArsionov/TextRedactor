package com.example.textredactor.engine.handler.mappers.impl;

import com.example.textredactor.engine.data.Data;
import com.example.textredactor.engine.model.Man;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ManMapper extends AbstractMapper<Man> {
    private Map<String, Consumer<String>> mappers = new HashMap<>();

    public ManMapper() {
        mappers.put("id", v -> {
            checkExists(v);
            currentClass.setId(Integer.parseInt(v));
        });
        mappers.put("name", v -> {
            checkExists(v);
            currentClass.setName(v);
        });
        mappers.put("country", v -> {
            checkExists(v);
            currentClass.setCountry(v);
        });
        mappers.put("city", v -> {
            checkExists(v);
            currentClass.setCity(v);
        });
        mappers.put("description", v -> {
            checkExists(v);
            currentClass.setDescription(v);
        });
        mappers.put("timeZone", v -> {
            checkExists(v);
            currentClass.setTimeZone(v);
        });
        mappers.put("tags", v -> {
            checkExists(v);

            Set<String> tags = Arrays.stream(v.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isBlank())
                    .collect(Collectors.toSet());

            currentClass.setTags(tags);
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
    protected void saveObject(Man object) {
        checkExistsObject(object);
        Data.MEN.add(object);
    }

    @Override
    protected Man createObject() {
        return new Man();
    }
}
