package com.example.textredactor.engine.repository.impl;

import com.example.textredactor.engine.exception.BufferedReaderException;
import com.example.textredactor.engine.exception.BufferedWriterException;
import com.example.textredactor.engine.exception.CreateFileException;
import com.example.textredactor.engine.model.Man;
import com.example.textredactor.engine.repository.AddManRepository;

import java.io.*;
import java.util.List;

public class AddManRepositoryImpl implements AddManRepository {

    @Override
    public void addMan(Man man) {
        createFolder();
        File file = new File(folder + "/" + man.getName() + ".txt");
        if (!file.exists()) { //исключает повторное созданеи (Наверное)
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new CreateFileException("Can't create file:" + file.getAbsolutePath(), e);
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            writer.write(":NAME\n");
            writer.write(man.getName() + "\n");
            writer.write(":COUNTRY\n");
            writer.write(man.getCountry() + "\n");
            writer.write(":CITY\n");
            writer.write(man.getCity() + "\n");
            writer.write(":DESCRIPTION\n");
            writer.write(man.getDescription() + "\n");
        } catch (IOException e) {
            throw new BufferedWriterException("Can't create file:" + file.getAbsolutePath(), e);
        }
    }

    @Override
    public List<Man> getMans() {
        File fileFolder = new File(folder);
        File[] files = fileFolder.listFiles();
        for (File file : files) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.contains(":NAME")) {
                        //TODO: логика записаи с файла
                    }
                }
            } catch (IOException e) {
                throw new BufferedReaderException("Can't read file:" + file.getAbsolutePath(), e);
            }
        }
        return List.of();
    }
}
