package com.example.textredactor.engine.service.impl;

import com.example.textredactor.engine.data.Data;
import com.example.textredactor.engine.model.Man;
import com.example.textredactor.engine.repository.ManRepository;
import com.example.textredactor.engine.repository.impl.ManRepositoryImpl;
import com.example.textredactor.engine.service.ManService;

import java.util.List;
import java.util.Set;

public class ManServiceImpl implements ManService {
    private final ManRepository manRepository = new ManRepositoryImpl();
    private static boolean initialized = false;

    public ManServiceImpl() {
        if (!initialized) {
            manRepository.readMan();
            initialized = true;
        }
    }

    @Override
    public List<Man> getAllMans() {
        return Data.MEN;
    }

    @Override
    public Man getManById(int id) {
        return Data.MEN.get(id);
    }

    @Override
    public Man saveMan(
            String name,
            String country,
            String city,
            String description,
            String timeZone,
            Set<String> tags
    ) {
        Man man = new Man();
        man.setId(Data.MEN.size());
        man.setName(name);
        man.setCountry(country);
        man.setCity(city);
        man.setDescription(description);
        man.setTimeZone(timeZone);
        man.setTags(tags);
        Data.MEN.add(man);
        manRepository.saveMan(man);
        return man;
    }

    @Override
    public Man updateMan(int id, String name, String country, String city, String description, String timeZone, Set<String> tags) {
        Man man = Data.MEN.get(id);
        man.setName(name);
        man.setCountry(country);
        man.setCity(city);
        man.setDescription(description);
        man.setTimeZone(timeZone);
        man.setTags(tags);
        manRepository.updateMan(Data.MEN);
        return man;
    }

    @Override
    public void deleteMan(int id) {
        Data.MEN.remove(id);
        manRepository.updateMan(Data.MEN);
    }

    @Override
    public void readMan() {
        manRepository.readMan();
    }
}
