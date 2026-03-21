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
    public Text saveText(String text) {
        Text model = new Text();
        model.setText(text);
        model.setId(Data.TEXTS.size());
        Data.TEXTS.add(model);
        textRepository.saveText(model);
        return model;
    }

    @Override
    public TextFormatResult processText(String text) {
        return textFormatMapper.map(text);
    }
}
