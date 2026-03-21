package com.example.textredactor.engine.mapper;

import com.example.textredactor.engine.data.Data;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextFormatMapper {

    private static final Pattern WORD_PATTERN = Pattern.compile("\\p{L}+|\\p{N}+");

    public TextFormatResult map(String text) {
        return mapWithCount(text);
    }

    public TextFormatResult mapWithCount(String text) {
        if (text == null || text.isBlank()) {
            return new TextFormatResult(text, 0);
        }

        Map<String, String> sample = Data.sample;
        Matcher matcher = WORD_PATTERN.matcher(text);
        StringBuilder result = new StringBuilder();

        int lastEnd = 0;
        int replacedCount = 0;

        while (matcher.find()) {
            result.append(text, lastEnd, matcher.start());

            String word = matcher.group();
            String replacedWord = sample.get(word);

            if (replacedWord != null) {
                result.append(replacedWord);
                replacedCount++;
            } else {
                result.append(word);
            }

            lastEnd = matcher.end();
        }

        result.append(text, lastEnd, text.length());

        return new TextFormatResult(result.toString(), replacedCount);
    }
}