package com.example.textredactor.engine.service.impl;

import com.example.textredactor.CreateFile;
import com.example.textredactor.engine.model.Letter;
import com.example.textredactor.engine.service.ReaderService;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReaderServiceImpl implements ReaderService {

    private final CreateFile createFile = CreateFile.init();

    @Override
    public List<Letter> getLetters() {
        List<Letter> letters = new ArrayList<>();
        File[] filesLetters = createFile.getFiles();
        for (File file : filesLetters) {
            letters.add(new Letter(file.getName(), createFile.getLetterFromFile(file), file));
        }
        return letters;
    }

    @Override
    public void deleteLetter(File file) {
        file.delete();
    }
}
