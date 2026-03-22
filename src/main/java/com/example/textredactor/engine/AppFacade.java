package com.example.textredactor.engine;

import com.example.textredactor.engine.mapper.TextFormatResult;
import com.example.textredactor.engine.model.Text;
import com.example.textredactor.engine.service.TextService;
import com.example.textredactor.engine.service.impl.TextServiceImpl;
import java.util.List;

public class AppFacade {
    private final TextService textService = new TextServiceImpl();

    public List<Text> getAllTexts() {
        return textService.getAllTexts();
    }

    public Text getTextById(int id) {
        return textService.getTextById(id);
    }

    public Text createText(String title, String text) {
        return textService.saveText(title, text);
    }

    public TextFormatResult processText(String text) {
        return textService.processText(text);
    }

    public Text updateText(int id, String title, String text) {
        //return textService.updateText(id, title, text);
        return null;
    }

    public void deleteText(int id) {
        //textService.deleteText(id);
    }
}
