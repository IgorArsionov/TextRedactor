package com.example.textredactor.engine.service.impl;

import com.example.textredactor.engine.file.ManRecordFileManager;
import com.example.textredactor.engine.model.Man;
import com.example.textredactor.engine.model.ManRecord;
import com.example.textredactor.engine.service.ManRecordService;
import java.util.List;

public class ManRecordServiceImpl implements ManRecordService {

    private final ManRecordFileManager manRecordFileManager = ManRecordFileManager.getInstance();

    @Override
    public void addRecord(String text, int manId, Man man) {
        manRecordFileManager.writeFileContent(new ManRecord(text, manId));
        man.getManRecordList().add(new ManRecord(text, manId));
    }

    @Override
    public List<ManRecord> getRecordList(Man man) {
        manRecordFileManager.setFile(man.getId() + ".txt");
        manRecordFileManager.readFileContent(ManRecord.class);
        return null;
    }
}
