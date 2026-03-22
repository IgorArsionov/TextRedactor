package com.example.textredactor.engine.repository.impl;

import com.example.textredactor.engine.data.Data;
import com.example.textredactor.engine.exception.FileException;
import com.example.textredactor.engine.handler.FileHandler;
import com.example.textredactor.engine.model.Man;
import com.example.textredactor.engine.repository.ManRepository;

import java.io.*;
import java.nio.file.Files;
import java.util.List;

public class ManRepositoryImpl implements ManRepository {
    private static final String NEW_LINE_TOKEN = "[[NL]]";

    private final File file = new File(Data.MODELS_FOLDER, Data.MAN_FILE_NAME);
    private final FileHandler fileHandler = FileHandler.getInstance();

    @Override
    public void readMan() {
        Data.createIfMissing(Data.MODELS_FOLDER);

        try {
            if (Files.notExists(file.toPath())) {
                Files.createFile(file.toPath());
            }
        } catch (IOException e) {
            throw new FileException("Failed to create man file", e);
        }

        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                if (line.startsWith("description:")) {
                    String raw = line.substring("description:".length());
                    String decoded = decode(raw);
                    line = "description:" + decoded;
                }
                fileHandler.getMapper(Man.class).map(line);
            }
        } catch (IOException e) {
            throw new FileException("Failed to read man file", e);
        }
    }

    @Override
    public void saveMan(Man man) {
        Data.createIfMissing(Data.MODELS_FOLDER);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writeToFile(writer, man);
        } catch (IOException e) {
            throw new FileException("Error writing file: " + e.getMessage());
        }
    }

    @Override
    public void updateMan(List<Man> mans) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            for (Man man : mans) {
                writeToFile(writer, man);
            }
        } catch (IOException e) {
            throw new FileException("Error writing file: ", e);
        }
    }

    private String encode(String text) {
        if (text == null) {
            return "";
        }

        return text
                .replace("\r\n", NEW_LINE_TOKEN)
                .replace("\n", NEW_LINE_TOKEN);
    }

    private String decode(String text) {
        if (text == null) {
            return "";
        }

        return text.replace(NEW_LINE_TOKEN, "\n");
    }

    private void writeToFile(BufferedWriter writer, Man man) throws IOException {
        writer.write("{\n");
        writer.write("id:" + man.getId() + "\n");
        writer.write("name:" + man.getName() + "\n");
        writer.write("country:" + man.getCountry() + "\n");
        writer.write("city:" + man.getCity() + "\n");
        writer.write("description:" + encode(man.getDescription()) + "\n");
        writer.write("tags:" + String.join(",", man.getTags()) + "\n");
        writer.write("}\n");
    }
}
