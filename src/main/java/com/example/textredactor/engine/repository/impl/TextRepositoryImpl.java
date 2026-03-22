package com.example.textredactor.engine.repository.impl;

import com.example.textredactor.engine.data.Data;
import com.example.textredactor.engine.exception.FileException;
import com.example.textredactor.engine.handler.FileHandler;
import com.example.textredactor.engine.model.Text;
import com.example.textredactor.engine.repository.TextRepository;
import java.io.*;

public class TextRepositoryImpl implements TextRepository {

    private static final String NEW_LINE_TOKEN = "[[NL]]";

    private final FileHandler fileHandler = FileHandler.getInstance();
    private final File textFile = new File(Data.MODELS_FOLDER, Data.TEXT_FILE_NAME);

    @Override
    public void readText() {
        Data.createIfMissing(Data.MODELS_FOLDER);

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
                System.out.println("=======");
                System.out.println(fileHandler.getMapper(Text.class));
                System.out.println("=======");

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
            writer.write("{\n");
            writer.write("id:" + text.getId() + "\n");
            writer.write("title:" + text.getTitle() + "\n");

            String encodedText = encode(text.getText());
            writer.write("text:" + encodedText + "\n");

            writer.write("}\n");
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
}