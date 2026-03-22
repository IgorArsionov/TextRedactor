package com.example.textredactor.engine.service;

import com.example.textredactor.engine.model.Man;

import java.util.List;
import java.util.Set;

public interface ManService {

    List<Man> getAllMans();

    Man getManById(int id);

    Man saveMan(
            String name,
            String country,
            String city,
            String description,
            String timeZone,
            Set<String> tags
    );

    Man updateMan(
            int id,
            String name,
            String country,
            String city,
            String description,
            String timeZone,
            Set<String> tags
    );

    void deleteMan(int id);

    void readMan();
}
