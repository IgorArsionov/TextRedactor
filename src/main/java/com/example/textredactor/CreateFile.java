package com.example.textredactor;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CreateFile {
    private static CreateFile createFile;

    private final File file;
    private final File folder;
    private final List<String> words = new ArrayList<>();

    public static CreateFile init() {
        if (createFile == null) {
            createFile = new CreateFile();
        }
        return createFile;
    }

    private CreateFile() {
        try {
            Path appDir = Path.of(System.getProperty("user.home"), "TextRedactor");
            Path lettersDir = appDir.resolve("letters");
            Path wordsFile = appDir.resolve("words.txt");

            Files.createDirectories(appDir);
            Files.createDirectories(lettersDir);

            if (!Files.exists(wordsFile)) {
                Files.createFile(wordsFile);
            }

            folder = lettersDir.toFile();
            file = wordsFile.toFile();

            System.out.println("App dir: " + appDir);
            System.out.println("Words file: " + file.getAbsolutePath());
            System.out.println("Letters dir: " + folder.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("Can't initialize app files", e);
        }
    }

    public List<String> getText() {
        words.clear();
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
            System.out.println("Successfully wrote to file: " + file.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File createFileByName(String nameFile) {
        String name = nameFile + ".txt";
        File newFile = new File(folder, name);

        if (!newFile.exists()) {
            try {
                boolean created = newFile.createNewFile();
                System.out.println("Created letter file: " + created + " -> " + newFile.getAbsolutePath());
            } catch (IOException e) {
                throw new RuntimeException("Can't create file: " + name, e);
            }
        }

        return newFile;
    }

    public void writeFileByName(String text, String name) {
        File newFile = createFileByName(name);
        writeToFile(newFile, text);
    }

    public void writeFileByFile(String text, File file) {
        writeToFile(file, text);
    }

    private void writeToFile(File file, String text) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
            bw.write(text);
            System.out.println("Successfully wrote to file: " + file.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public File[] getFiles() {
        File[] files = folder.listFiles();
        return files != null ? files : new File[0];
    }

    public String getLetterFromFile(File file) {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return builder.toString();
    }
}