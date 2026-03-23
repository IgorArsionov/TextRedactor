package com.example.textredactor.engine.service.impl;

import com.example.textredactor.engine.data.Data;
import com.example.textredactor.engine.model.ManRecord;
import com.example.textredactor.engine.repository.ManRecordRepository;
import com.example.textredactor.engine.repository.impl.ManRecordRepositoryImpl;
import com.example.textredactor.engine.service.ManRecordService;

import java.util.List;

public class ManRecordServiceImpl implements ManRecordService {
    private final ManRecordRepository repository = new ManRecordRepositoryImpl();
    private static boolean initialized = false;

    public ManRecordServiceImpl() {
        if (!initialized) {
            repository.readRecords();
            initialized = true;
        }
    }

    @Override
    public List<ManRecord> getRecordsByManId(int manId) {
        return Data.MAN_RECORDS.stream()
                .filter(record -> record.getManId() == manId)
                .toList();
    }

    @Override
    public ManRecord addRecord(int manId, String text) {
        ManRecord record = new ManRecord();
        record.setId(generateNextId());
        record.setManId(manId);
        record.setText(text);

        Data.MAN_RECORDS.add(record);
        repository.saveRecord(record);

        return record;
    }

    @Override
    public void deleteRecord(int recordId) {
        ManRecord existing = Data.MAN_RECORDS.stream()
                .filter(record -> record.getId() == recordId)
                .findFirst()
                .orElse(null);

        if (existing == null) {
            return;
        }

        Data.MAN_RECORDS.remove(existing);
        repository.updateRecords(Data.MAN_RECORDS);
    }

    @Override
    public void readRecords() {
        repository.readRecords();
    }

    private int generateNextId() {
        return Data.MAN_RECORDS.stream()
                .mapToInt(ManRecord::getId)
                .max()
                .orElse(-1) + 1;
    }
}