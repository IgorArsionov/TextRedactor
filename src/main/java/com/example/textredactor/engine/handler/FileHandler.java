package com.example.textredactor.engine.handler;

import com.example.textredactor.engine.handler.mappers.Mapper;
import com.example.textredactor.engine.handler.mappers.impl.TextMapper;
import com.example.textredactor.engine.model.Text;

import java.util.HashMap;
import java.util.Map;

public class FileHandler {
    private static FileHandler instance;
    private Map<Class<?>, Mapper> mapperMap;

    private FileHandler() {
        mapperMap = new HashMap<>();
        mapperMap.put(Text.class, new TextMapper());
    }

    public static FileHandler getInstance() {
        if (instance == null) {
            instance = new FileHandler();
        }
        return instance;
    }

    public Mapper getMapper(Class<?> clazz) {
        return mapperMap.get(clazz);
    }

}
