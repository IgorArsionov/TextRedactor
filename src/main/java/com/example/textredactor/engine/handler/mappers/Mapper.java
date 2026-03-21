package com.example.textredactor.engine.handler.mappers;

public interface Mapper {
    <T> T map(Object source);
}
