package com.example.textredactor.engine.service.impl;

import com.example.textredactor.engine.data.Data;
import com.example.textredactor.engine.repository.SampleRepository;
import com.example.textredactor.engine.repository.impl.SampleRepositoryImpl;
import com.example.textredactor.engine.service.SampleService;

import java.util.Map;

public class SampleServiceImpl implements SampleService {
    private final SampleRepository sampleRepository = new SampleRepositoryImpl();

    public SampleServiceImpl() {
        sampleRepository.readSample();
    }

    @Override
    public void createSample(String key, String value) {
        Data.sample.put(key, value);
        sampleRepository.saveSample(key, value);
    }

    @Override
    public Map<String, String> getSample() {
        return Data.sample;
    }

    @Override
    public void deleteSample(String key) {
        Data.sample.remove(key);
        sampleRepository.updateSample();
    }

    @Override
    public void updateSample(String key, String value) {
        Data.sample.put(key, value);
        sampleRepository.updateSample();
    }
}
