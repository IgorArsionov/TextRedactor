package com.example.textredactor.engine.service;

import com.example.textredactor.engine.model.ManRecord;

import java.util.List;

public interface ManRecordService {
    List<ManRecord> getRecordsByManId(int manId);
    ManRecord addRecord(int manId, String text);
    void deleteRecord(int recordId);
    void readRecords();
}