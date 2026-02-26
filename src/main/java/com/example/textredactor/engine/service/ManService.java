package com.example.textredactor.engine.service;

import com.example.textredactor.engine.model.Man;

import java.util.List;

public interface ManService {
    void addMan(String name, String county, String city, String description);

    Man getManByName(String name);

    List<Man> getManList();
}
