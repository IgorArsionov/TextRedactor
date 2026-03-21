package com.example.textredactor.engine.service;

import com.example.textredactor.engine.model.Text;

import java.util.List;

public interface TextService {
    List<Text> getAllTexts();

    Text getTextById(int id);

    void saveText(Text text);

    String processText(String text);
}
