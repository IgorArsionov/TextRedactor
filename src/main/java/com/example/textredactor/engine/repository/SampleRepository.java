package com.example.textredactor.engine.repository;

public interface SampleRepository {

    void readSample();

    void saveSample(String key, String value);

    void updateSample();
}
