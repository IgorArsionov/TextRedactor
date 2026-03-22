package com.example.textredactor.engine.service;

import java.util.Map;

public interface SampleService {

    void createSample(String key, String value);

    Map<String, String> getSample();

    void deleteSample(String key);

    void updateSample(String key, String value);
}
