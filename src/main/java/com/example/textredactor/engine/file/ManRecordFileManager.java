package com.example.textredactor.engine.file;

import com.example.textredactor.engine.exception.BufferedWriterException;
import com.example.textredactor.engine.exception.ClassMismatchException;
import com.example.textredactor.engine.model.ManRecord;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class ManRecordFileManager extends AbstractFileManager {

    private static ManRecordFileManager manRecordFileManager;
    private String fileName;

    private ManRecordFileManager() {
    }

    public static ManRecordFileManager getInstance() {
        if (manRecordFileManager == null) {
            manRecordFileManager = new ManRecordFileManager();
        }
        return manRecordFileManager;
    }

    @Override
    public <T> void writeFileContent(T value) {
        if (!(value instanceof ManRecord record)) {
            throw new ClassMismatchException("Value is not instance of ManRecord");
        }

        createFile(record.getManId() + ".txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentFile, true))) {
            writer.write(String.format("{%n"));
            writer.write(String.format("manId:%d%n", record.getManId()));
            writer.write(String.format("text:%s%n", encodeText(record.getText())));
            writer.write(String.format("}%n"));
        } catch (Exception e) {
            throw new BufferedWriterException("Error writing record to file", e);
        }
    }

    @Override
    public void readFileContent(Class<?> clazz) {
        super.readFileContent(clazz);
    }
}