package com.example.textredactor.engine.repository;

import com.example.textredactor.engine.model.Man;

import java.util.List;

public interface ManRepository {

    void readMan();

    void saveMan(Man man);

    void updateMan(List<Man> mans);
}
