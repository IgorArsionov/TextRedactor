package com.example.textredactor.engine.model;

import java.io.File;

public class Letter {
    private String name;
    private String letters;
    private File file;

    public Letter(String name, String letters, File file) {
        this.name = name;
        this.letters = letters;
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLetters() {
        return letters;
    }

    public void setLetters(String letters) {
        this.letters = letters;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
