package com.example.textredactor.engine.data;

import com.example.textredactor.engine.model.Man;
import com.example.textredactor.engine.model.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Data {
    public static final List<Text> TEXTS = new ArrayList<>();
    public static final Map<String, String> sample = new HashMap<>();
    public static final List<Man> MEN = new ArrayList<>();

    public static final String APP_FOLDER_NAME = "TextRedactor";
    public static final String MODELS_FOLDER_NAME = "Models";
    public static final String TEXT_FILE_NAME = "text.txt";
    public static final String SAMPLE_FILE_NAME = "sample.txt";
    public static final String MAN_FILE_NAME = "man.txt";

    public static final File USER_HOME = new File(System.getProperty("user.home"));
    public static final File APP_FOLDER = new File(USER_HOME, APP_FOLDER_NAME);
    public static final File MODELS_FOLDER = new File(APP_FOLDER, MODELS_FOLDER_NAME);

    static {
        createIfMissing(APP_FOLDER);
        createIfMissing(MODELS_FOLDER);
    }

    public static void createIfMissing(File folder) {
        if (!folder.exists() && !folder.mkdirs()) {
            throw new RuntimeException("Can't create folder: " + folder.getAbsolutePath());
        }
    }
}
