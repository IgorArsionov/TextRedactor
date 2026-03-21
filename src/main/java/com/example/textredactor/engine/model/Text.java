package com.example.textredactor.engine.model;

public class Text {
    private int id;
    private String text;

    public Text(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public Text() {
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
