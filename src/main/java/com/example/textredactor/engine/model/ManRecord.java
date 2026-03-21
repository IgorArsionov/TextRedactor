package com.example.textredactor.engine.model;

public class ManRecord {
    private int manId;
    private String text;

    public ManRecord(String text, int manId) {
        this.text = text;
        this.manId = manId;
    }

    public ManRecord() {

    }

    public String getText() {
        return text;
    }
    public int getManId() {
        return manId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setManId(int manId) {
        this.manId = manId;
    }
}
