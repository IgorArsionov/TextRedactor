package com.example.textredactor.engine.file;

import com.example.textredactor.engine.handler.ModelHandler;
import com.example.textredactor.engine.handler.ModelHandlerImpl;
import com.example.textredactor.engine.mapper.Mapper;
import com.example.textredactor.engine.model.Man;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class ManFileManager extends AbstractFileManager {
    private static ManFileManager manFileManager;
    private final String fileName = "Man.txt";

    private ManFileManager() {
        createFile(fileName);

    }

    public static ManFileManager init() {
        if (manFileManager == null) {
            manFileManager = new ManFileManager();
        }
        return manFileManager;
    }

    @Override
    public void writeFileContent(Man man) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentFile, true))) {
            writer.write(String.format("{%n"));
            writer.write(String.format("name:%s%n", man.getName()));
            writer.write(String.format("country:%s%n", man.getCountry()));
            writer.write(String.format("city:%s%n", man.getCity()));
            writer.write(String.format("description:%s%n", man.getDescription()));
            writer.write(String.format("}%n"));

        } catch (Exception e) {
            throw new RuntimeException("Error while writing file " + fileName, e);
        }
    }
}
