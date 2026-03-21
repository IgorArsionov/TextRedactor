package com.example.textredactor.engine.service;

import com.example.textredactor.engine.model.Man;
import com.example.textredactor.engine.model.ManRecord;

import java.util.List;

public interface ManRecordService {

    void addRecord(String text, int manId, Man Man);

    List<ManRecord> getRecordList(Man man);
}
