package com.example.textredactor.engine.service.impl;

import com.example.textredactor.engine.Engine;
import com.example.textredactor.engine.model.Word;
import com.example.textredactor.engine.service.WordRefactorService;

public class WordRefactorServiceImpl implements WordRefactorService {

    @Override
    public Word Process(String text) {
        String before = "";
        String after = "";
        String word = "";
        int start = 0;
        int end;
        int count = 0;
        end = text.length() - 1;
        while (start <= end && !Character.isLetter(text.charAt(start))) {
            start++;
        }
        while (end >= start && !Character.isLetter(text.charAt(end))) {
            end--;
        }

        before = text.substring(0, start);
        after = text.substring(end + 1);
        word = text.substring(start, end + 1);
        StringBuilder builder = new StringBuilder(text);
        if (Engine.shablon.containsKey(word.toLowerCase())) {
            int x = Integer.parseInt(Engine.shablon.get(word.toLowerCase()).split(";")[0]);
            if (x == 0) {
                word = Engine.shablon.get(word.toLowerCase()).split(";")[1];
            } else {
                builder.insert(x,Engine.shablon.get(word.toLowerCase()).split(";")[1]);
                word = builder.toString();
            }

            return new Word(before, word, after);
        }
        return new Word(before, word, after);
    }
}
