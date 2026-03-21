package com.example.textredactor.engine.service.impl;

import com.example.textredactor.engine.model.Text;
import com.example.textredactor.engine.service.TextService;

import java.util.List;

public class TextServiceImpl implements TextService {
    @Override
    public List<Text> getAllTexts() {
        return List.of();
    }

    @Override
    public Text getTextById(int id) {
        return null;
    }

    @Override
    public void saveText(Text text) {

    }

    @Override
    public String processText(String text) {
        return "";
    }
}
