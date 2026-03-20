package com.example.textredactor.engine.file;

import com.example.textredactor.engine.data.Data;
import com.example.textredactor.engine.exception.CreateFileException;
import com.example.textredactor.engine.handler.ModelHandler;
import com.example.textredactor.engine.handler.ModelHandlerImpl;
import com.example.textredactor.engine.mapper.Mapper;
import com.example.textredactor.engine.model.Man;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public abstract class AbstractFileManager {
    protected final ModelHandler modelHandler = new ModelHandlerImpl();
    protected Mapper mapper;
    protected File currentFile;

    protected void setFile(File file) {
        currentFile = file;
    }

    protected void createFile(String fileName) {
        File generalFolder = new File(Data.folderModel);
        if (!generalFolder.exists()) {
            generalFolder.mkdir();
        }
        currentFile = new File(generalFolder, fileName);

        try {
            currentFile.createNewFile();
        } catch (IOException e) {
            throw new CreateFileException("Can't create file " + currentFile.getAbsolutePath(), e);
        }

    }

    public void readFileContent(Class<?> clazz) {
        mapper = modelHandler.getMapper(clazz);
        try (BufferedReader reader = new BufferedReader(new FileReader(currentFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.length() != 0) {
                    mapper.process(line);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error reading file " + currentFile.getAbsolutePath(), e);
        }
    }

    public abstract void writeFileContent(Man man);

}
