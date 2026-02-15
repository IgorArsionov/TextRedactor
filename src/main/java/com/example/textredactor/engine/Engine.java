package com.example.textredactor.engine;

import com.example.textredactor.engine.model.Word;
import com.example.textredactor.engine.service.SaveLetters;
import com.example.textredactor.engine.service.WordRefactorService;
import com.example.textredactor.engine.service.impl.SaveLettersImpl;
import com.example.textredactor.engine.service.impl.WordRefactorServiceImpl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Engine {
    public static Map<String, String> shablon = new HashMap<String, String>();
    private WordRefactorService wordRefactorService = new WordRefactorServiceImpl();
    private SaveLetters saveLetters = new SaveLettersImpl();

    public String Start(String text, String nameFile) {
        String[] words = text.split(" ");
        StringBuilder sb = new StringBuilder();

        List<Word> list = Arrays.stream(words)
                .map(wordRefactorService::Process)
                .toList();

        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).toString()).append(" ");
        }
        //TODO: Сохранение текста
        if (!nameFile.isEmpty()) {
            saveLetters.saveLetters(nameFile, sb.toString());
        }
        return sb.toString();
    }

    public static void setShablon(List<String> words) {
        for (int i = 0; i < words.size(); i++) {
            String[] value = words.get(i).split("=");
            shablon.put(value[0], value[1]);
        }
    }
}
