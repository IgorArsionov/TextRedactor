package com.example.textredactor.engine.repository.impl;

import com.example.textredactor.engine.handler.FileHandler;
import com.example.textredactor.engine.model.Text;
import com.example.textredactor.engine.repository.TextRepository;

public class TextRepositoryImpl implements TextRepository {
    private FileHandler fileHandler = FileHandler.getInstance();

    @Override
    public void readText() {
        //while
        fileHandler.getMapper(Text.class).map("");
    }
}
