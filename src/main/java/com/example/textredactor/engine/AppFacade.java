package com.example.textredactor.engine;

import com.example.textredactor.engine.mapper.TextFormatResult;
import com.example.textredactor.engine.model.Man;
import com.example.textredactor.engine.model.Text;
import com.example.textredactor.engine.service.ManService;
import com.example.textredactor.engine.service.SampleService;
import com.example.textredactor.engine.service.TextService;
import com.example.textredactor.engine.service.impl.ManServiceImpl;
import com.example.textredactor.engine.service.impl.SampleServiceImpl;
import com.example.textredactor.engine.service.impl.TextServiceImpl;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AppFacade {
    private final TextService textService = new TextServiceImpl();
    private final SampleService sampleService = new SampleServiceImpl();
    private final ManService manService = new ManServiceImpl();
    private static boolean initialized = false;

    public AppFacade() {
        if (!initialized) {
            textService.readText();
            initialized = true;
        }
    }

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
        return textService.updateText(id, title, text);
    }

    public void deleteText(int id) {
        textService.deleteText(id);
    }

    public void updateReplacement(String key, String value) {
        sampleService.updateSample(key, value);
    }

    public Map<String, String> getAllReplacements() {
        return sampleService.getSample();
    }

    public void deleteReplacement(String key) {
        sampleService.deleteSample(key);
    }

    public void addReplacement(String key, String value) {
        sampleService.createSample(key, value);
    }

    public List<Man> getAllMans() {
        return manService.getAllMans();
    }

    public Man getManById(int id) {
        return manService.getManById(id);
    }

    public void deleteMan(int id) {
        manService.deleteMan(id);
    }

    public Man saveMan(String name, String country, String city, String description, String timeZone, Set<String> tags) {
        return manService.saveMan(name, country, city, description, timeZone, tags);
    }

    public Man updateMan(int id, String name, String country, String city, String description, String timeZone, Set<String> tags) {
        return manService.updateMan(id, name, country, city, description, timeZone, tags);
    }

}