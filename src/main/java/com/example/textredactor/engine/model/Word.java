package com.example.textredactor.engine.model;

public class Word {
    private String before;
    private String text;
    private String after;

    public Word(String before, String word, String after) {
        this.before = before;
        this.text = word;
        this.after = after;
    }

    public String getText() {
        return text;
    }

    public String toString() {
        return before + text + after;
    }
}
