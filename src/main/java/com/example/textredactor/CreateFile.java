package com.example.textredactor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CreateFile {
    private static CreateFile createFile;

    private final File file;
    private final File folder;
    private String folderName = "letters";
    private final List<String> words = new ArrayList<>();

    public static CreateFile init() {
        if (createFile == null) {
            createFile = new CreateFile();
        }
        return createFile;
    }

    private CreateFile() {
        String name = "words.txt";
        folder = new File(folderName);
        if (!folder.exists()) {
            boolean mkdir = folder.mkdir();
            System.out.println("Folder created: " + mkdir);
        }
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

    public void writeFile(String text) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
            bw.write(text);
            System.out.println("Successfully wrote to the file." + file.getPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File CreateFileByName(String nameFile) {
        String name = nameFile + ".txt";
        File file = new File(folderName + "/" + name);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Can't create file: " + name, e);
            }
        }

        return file;
    }

    public void writeFileByName(String text, String name) {
        File newFile = CreateFileByName(name);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(newFile, false))) {
            bw.write(text);
            System.out.println("Successfully wrote to the file." + file.getPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
