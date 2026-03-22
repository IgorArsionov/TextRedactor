package com.example.textredactor.engine.service;

import com.example.textredactor.engine.mapper.TextFormatResult;
import com.example.textredactor.engine.model.Text;

import java.util.List;

public interface TextService {
    List<Text> getAllTexts();

    Text getTextById(int id);

    Text saveText(String title, String text);

    TextFormatResult processText(String text);

    Text updateText(int id, String title, String text);

    void deleteText(int id);

    void readText();
}
