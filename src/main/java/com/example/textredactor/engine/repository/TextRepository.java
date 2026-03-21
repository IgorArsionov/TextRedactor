package com.example.textredactor.engine.repository;

import com.example.textredactor.engine.model.Text;

public interface TextRepository {

    void readText();

    void saveText(Text text);
}
