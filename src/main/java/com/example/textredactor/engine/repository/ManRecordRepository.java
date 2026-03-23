package com.example.textredactor.engine.repository;

import com.example.textredactor.engine.model.ManRecord;

import java.util.List;

public interface ManRecordRepository {
    void readRecords();
    void saveRecord(ManRecord record);
    void updateRecords(List<ManRecord> records);
}