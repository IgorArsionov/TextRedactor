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
        return Data.TEXTS.get(id);
    }

    @Override
    public Text saveText(String title, String text) {
        Text model = new Text();
        model.setId(Data.TEXTS.size());
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
        Data.TEXTS.get(id).setTitle(title);
        Data.TEXTS.get(id).setText(text);
        textRepository.updateText(Data.TEXTS);
        return Data.TEXTS.get(id);
    }

    @Override
    public void deleteText(int id) {
        Data.TEXTS.remove(id);
        textRepository.updateText(Data.TEXTS);
    }

    @Override
    public void readText() {
        textRepository.readText();
    }
}
