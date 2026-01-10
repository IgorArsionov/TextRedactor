package com.example.textredactor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CreateFile {
    private final File file;
    private final List<String> words = new ArrayList<>();

    public CreateFile() {
        String name = "words.txt";
        file = new File(name);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Can't create file: " + name, e);
            }
        }
    }

    public List<String> getText() {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                words.add(line);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return words;
    }
}
