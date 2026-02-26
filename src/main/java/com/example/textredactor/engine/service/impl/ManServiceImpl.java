package com.example.textredactor.engine.service.impl;

import com.example.textredactor.engine.data.Data;
import com.example.textredactor.engine.exception.ModelNotFoundException;
import com.example.textredactor.engine.model.Man;
import com.example.textredactor.engine.service.ManService;

import java.util.List;

public class ManServiceImpl implements ManService {

    private final Data data = Data.getInstance();

    @Override
    public void addMan(String name, String county, String city, String description) {
        data.addMan(name, county, city, description);
    }

    @Override
    public Man getManByName(String name) {
        return data.manList.stream()
                .filter(v -> v.getName().equals(name))
                .findFirst().orElseThrow(() -> new ModelNotFoundException("Man with name " + name + " not found"));
    }

    @Override
    public List<Man> getManList() {
        return data.manList;
    }


}
