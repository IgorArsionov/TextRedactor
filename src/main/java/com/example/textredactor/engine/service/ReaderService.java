package com.example.textredactor.engine.service;

import com.example.textredactor.engine.model.Letter;
import java.io.File;
import java.util.List;

public interface ReaderService {
    List<Letter> getLetters();

    void deleteLetter(File file);
}
