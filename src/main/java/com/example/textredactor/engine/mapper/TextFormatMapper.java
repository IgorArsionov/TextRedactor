package com.example.textredactor.engine.mapper;

import com.example.textredactor.engine.data.Data;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextFormatMapper {

    private static final Pattern WORD_PATTERN = Pattern.compile("\\p{L}+|\\p{N}+");

    public TextFormatResult map(String text) {
        if (text == null || text.isBlank()) {
            return new TextFormatResult(text, 0);
        }

        Matcher matcher = WORD_PATTERN.matcher(text);
        StringBuilder result = new StringBuilder();

        int lastEnd = 0;
        int replacedCount = 0;

        while (matcher.find()) {
            result.append(text, lastEnd, matcher.start());

            String originalWord = matcher.group();
            String replacement = findReplacement(originalWord);

            if (replacement != null) {
                result.append(applyCaseStyle(originalWord, replacement));
                replacedCount++;
            } else {
                result.append(originalWord);
            }

            lastEnd = matcher.end();
        }

        result.append(text, lastEnd, text.length());

        return new TextFormatResult(result.toString(), replacedCount);
    }

    private String findReplacement(String word) {
        if (word == null) {
            return null;
        }

        for (Map.Entry<String, String> entry : Data.sample.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(word)) {
                return entry.getValue();
            }
        }

        return null;
    }

    private String applyCaseStyle(String original, String replacement) {
        if (original.equals(original.toUpperCase())) {
            return replacement.toUpperCase();
        }

        if (Character.isUpperCase(original.charAt(0))
                && original.substring(1).equals(original.substring(1).toLowerCase())) {
            return capitalize(replacement);
        }

        return replacement.toLowerCase();
    }

    private String capitalize(String value) {
        if (value == null || value.isEmpty()) {
            return value;
        }

        if (value.length() == 1) {
            return value.toUpperCase();
        }

        return Character.toUpperCase(value.charAt(0))
                + value.substring(1).toLowerCase();
    }
}