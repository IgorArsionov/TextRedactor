package com.example.textredactor.engine.handler;

import com.example.textredactor.engine.mapper.Mapper;

public interface ModelHandler {
    Mapper getMapper(Class<?> clazz);
}
