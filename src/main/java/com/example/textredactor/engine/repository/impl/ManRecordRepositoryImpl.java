package com.example.textredactor.engine.repository.impl;

import com.example.textredactor.engine.data.Data;
import com.example.textredactor.engine.exception.FileException;
import com.example.textredactor.engine.model.ManRecord;
import com.example.textredactor.engine.repository.ManRecordRepository;

import java.io.*;
import java.nio.file.Files;
import java.util.List;

public class ManRecordRepositoryImpl implements ManRecordRepository {
    private static final String NEW_LINE_TOKEN = "[[NL]]";

    private final File file = new File(Data.MODELS_FOLDER, Data.MAN_RECORD_FILE_NAME);

    @Override
    public void readRecords() {
        Data.createIfMissing(Data.MODELS_FOLDER);

        try {
            if (Files.notExists(file.toPath())) {
                Files.createFile(file.toPath());
            }
        } catch (IOException e) {
            throw new FileException("Failed to create man records file", e);
        }

        Data.MAN_RECORDS.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            ManRecord current = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                if (line.equals("{")) {
                    current = new ManRecord();
                    continue;
                }

                if (line.equals("}")) {
                    if (current != null) {
                        Data.MAN_RECORDS.add(current);
                    }
                    current = null;
                    continue;
                }

                if (current == null) {
                    continue;
                }

                String[] parts = line.split(":", 2);
                if (parts.length != 2) {
                    continue;
                }

                String key = parts[0];
                String value = parts[1];

                switch (key) {
                    case "id" -> current.setId(Integer.parseInt(value));
                    case "manId" -> current.setManId(Integer.parseInt(value));
                    case "text" -> current.setText(decode(value));
                }
            }

        } catch (IOException e) {
            throw new FileException("Failed to read man records file", e);
        }
    }

    @Override
    public void saveRecord(ManRecord record) {
        Data.createIfMissing(Data.MODELS_FOLDER);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writeRecord(writer, record);
        } catch (IOException e) {
            throw new FileException("Failed to save man record", e);
        }
    }

    @Override
    public void updateRecords(List<ManRecord> records) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            for (ManRecord record : records) {
                writeRecord(writer, record);
            }
        } catch (IOException e) {
            throw new FileException("Failed to update man records", e);
        }
    }

    private void writeRecord(BufferedWriter writer, ManRecord record) throws IOException {
        writer.write("{\n");
        writer.write("id:" + record.getId() + "\n");
        writer.write("manId:" + record.getManId() + "\n");
        writer.write("text:" + encode(record.getText()) + "\n");
        writer.write("}\n");
    }

    private String encode(String text) {
        if (text == null) {
            return "";
        }

        return text.replace("\r\n", NEW_LINE_TOKEN)
                .replace("\n", NEW_LINE_TOKEN);
    }

    private String decode(String text) {
        if (text == null) {
            return "";
        }

        return text.replace(NEW_LINE_TOKEN, "\n");
    }
}