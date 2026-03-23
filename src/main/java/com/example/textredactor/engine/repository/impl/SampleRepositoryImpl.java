package com.example.textredactor.engine.repository.impl;

import com.example.textredactor.engine.data.Data;
import com.example.textredactor.engine.exception.FileException;
import com.example.textredactor.engine.repository.SampleRepository;

import java.io.*;
import java.nio.file.Files;
import java.util.Map;

public class SampleRepositoryImpl implements SampleRepository {
    private final File file = new File(Data.APP_FOLDER, Data.SAMPLE_FILE_NAME);

    @Override
    public void readSample() {

        try {
            if (Files.notExists(file.toPath())) {
                Files.createFile(file.toPath());
            }
        } catch (IOException e) {
            throw new FileException("Failed to create man file", e);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }
                String[] parts = line.split("=", 2);
                if (parts.length != 2) {
                    continue;
                }
                Data.sample.put(parts[0], parts[1]);
            }
        } catch (Exception e) {
            throw new FileException("Error reading file - "
                    + e.getMessage() + ", file: ");
        }

    }

    @Override
    public void saveSample(String key, String value) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(String.format("%s=%s%n", key, value));
        } catch (Exception e) {
            throw new FileException("Error writing file - "
                    + e.getMessage() + ", file: ");
        }
    }

    @Override
    public void updateSample() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            for (Map.Entry<String, String> entry : Data.sample.entrySet()) {
                writer.write(String.format("%s=%s%n", entry.getKey(), entry.getValue()));
            }
        } catch (Exception e) {
            throw new FileException("Error writing file - "
                    + e.getMessage() + ", file: ");
        }
    }
}
