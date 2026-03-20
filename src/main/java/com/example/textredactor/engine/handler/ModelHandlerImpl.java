package com.example.textredactor.engine.handler;

import com.example.textredactor.engine.mapper.Mapper;
import com.example.textredactor.engine.mapper.impl.AbstractMapper;
import com.example.textredactor.engine.mapper.impl.ManMapper;
import com.example.textredactor.engine.model.Man;

import java.util.HashMap;
import java.util.Map;

public class ModelHandlerImpl implements ModelHandler {
    Map<Class<?>, Mapper> mappers = new HashMap<>();

    public ModelHandlerImpl() {
        mappers.put(Man.class, new ManMapper());
    }

    @Override
    public Mapper getMapper(Class<?> clazz) {
        return mappers.get(clazz);
    }
}
