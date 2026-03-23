package com.example.textredactor.engine.repository;

import com.example.textredactor.engine.model.Text;

import java.util.List;

public interface TextRepository {

    void readText();

    void saveText(Text text);

    void updateText(List<Text> texts);
}
