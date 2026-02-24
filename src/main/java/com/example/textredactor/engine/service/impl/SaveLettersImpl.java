package com.example.textredactor.engine.service.impl;

import com.example.textredactor.engine.service.SaveLetters;
import com.example.textredactor.CreateFile;

public class SaveLettersImpl implements SaveLetters {
    private CreateFile createFile = CreateFile.init();
    @Override
    public void saveLetters(String nameFile, String letters) {
        createFile.writeFileByName(letters, nameFile);
    }

    @Override
    public void loadLetters() {

    }

    @Override
    public void updateLetters() {

    }
}
