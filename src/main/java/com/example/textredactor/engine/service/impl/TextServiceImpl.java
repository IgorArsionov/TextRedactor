package com.example.textredactor.engine.service.impl;

import com.example.textredactor.engine.data.Data;
import com.example.textredactor.engine.mapper.TextFormatMapper;
import com.example.textredactor.engine.mapper.TextFormatResult;
import com.example.textredactor.engine.model.Text;
import com.example.textredactor.engine.repository.TextRepository;
import com.example.textredactor.engine.repository.impl.TextRepositoryImpl;
import com.example.textredactor.engine.service.TextService;

import java.util.List;

public class TextServiceImpl implements TextService {
    private final TextRepository textRepository = new TextRepositoryImpl();
    private final TextFormatMapper textFormatMapper = new TextFormatMapper();

    @Override
    public List<Text> getAllTexts() {
        return Data.TEXTS;
    }

    @Override
    public Text getTextById(int id) {
        return Data.TEXTS.stream()
                .filter(text -> text.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Text saveText(String title, String text) {
        Text model = new Text();
        model.setId(generateNextTextId());
        model.setTitle(title);
        model.setText(text);

        Data.TEXTS.add(model);
        textRepository.saveText(model);

        return model;
    }

    @Override
    public TextFormatResult processText(String text) {
        return textFormatMapper.map(text);
    }

    @Override
    public Text updateText(int id, String title, String text) {
        Text existing = getTextById(id);

        if (existing == null) {
            return null;
        }

        existing.setTitle(title);
        existing.setText(text);

        textRepository.updateText(Data.TEXTS);
        return existing;
    }

    @Override
    public void deleteText(int id) {
        Text existing = getTextById(id);

        if (existing == null) {
            return;
        }

        Data.TEXTS.remove(existing);
        textRepository.updateText(Data.TEXTS);
    }

    @Override
    public void readText() {
        textRepository.readText();
    }

    private int generateNextTextId() {
        return Data.TEXTS.stream()
                .mapToInt(Text::getId)
                .max()
                .orElse(-1) + 1;
    }
}