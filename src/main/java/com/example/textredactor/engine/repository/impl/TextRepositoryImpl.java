package com.example.textredactor.engine.repository.impl;

import com.example.textredactor.engine.data.Data;
import com.example.textredactor.engine.exception.FileException;
import com.example.textredactor.engine.handler.FileHandler;
import com.example.textredactor.engine.model.Text;
import com.example.textredactor.engine.repository.TextRepository;
import java.io.*;
import java.nio.file.Files;
import java.util.List;

public class TextRepositoryImpl implements TextRepository {

    private static final String NEW_LINE_TOKEN = "[[NL]]";

    private final FileHandler fileHandler = FileHandler.getInstance();
    private final File textFile = new File(Data.MODELS_FOLDER, Data.TEXT_FILE_NAME);

    @Override
    public void readText() {
        Data.createIfMissing(Data.MODELS_FOLDER);

        try {
            if (Files.notExists(textFile.toPath())) {
                Files.createFile(textFile.toPath());
            }
        } catch (IOException e) {
            throw new FileException("Failed to create man file", e);
        }

        String line;

        try (BufferedReader reader = new BufferedReader(new FileReader(textFile))) {
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                if (line.startsWith("text:")) {
                    String raw = line.substring(5);
                    String decoded = decode(raw);
                    line = "text:" + decoded;
                }

                fileHandler.getMapper(Text.class).map(line);
            }
        } catch (Exception e) {
            throw new FileException("Error reading file: " + e.getMessage());
        }
    }

    @Override
    public void saveText(Text text) {
        Data.createIfMissing(Data.MODELS_FOLDER);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(textFile, true))) {
            writeToFile(writer, text);
        } catch (IOException e) {
            throw new FileException("Error writing file: " + e.getMessage());
        }
    }

    @Override
    public void updateText(List<Text> texts) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(textFile, false))) {
            for (Text text : texts) {
                writeToFile(writer, text);
            }
        } catch (IOException e) {
            throw new FileException("Error writing file: " + e.getMessage());
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

    private void writeToFile(BufferedWriter writer, Text text) throws IOException {
        writer.write("{\n");
        writer.write("id:" + text.getId() + "\n");
        writer.write("title:" + text.getTitle() + "\n");

        String encodedText = encode(text.getText());
        writer.write("text:" + encodedText + "\n");

        writer.write("}\n");
    }
}