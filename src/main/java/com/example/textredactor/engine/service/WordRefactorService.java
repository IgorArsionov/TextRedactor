package com.example.textredactor.engine.service;

import com.example.textredactor.engine.model.Word;

public interface WordRefactorService {
    Word Process(String text);
}
