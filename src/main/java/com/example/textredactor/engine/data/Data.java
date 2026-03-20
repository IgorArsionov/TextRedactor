package com.example.textredactor.engine.data;

import com.example.textredactor.engine.model.Man;

import java.util.ArrayList;
import java.util.List;

public class Data {
    public List<Man> manList;

    public static String folderModel = "Models";

    public static String pageGeneral = "General";
    public static String pageSettings = "Settings";
    public static String pageLetters = "Letters";
    public static String pageText = "Text";
    public static String pageManPage = "ManPage";
    public static String pageAddManPage = "AddManPage";

    private static Data data;

    private Data() {
        manList = new ArrayList<>();
    }

    public static Data getInstance() {
        if (data == null) {
            data = new Data();
        }
        return data;
    }

    public Man addMan(String name, String county, String city, String description) {
        Man man = new Man(name, county, city, description);
        manList.add(man);
        return man;
    }
    public void addMan(Man man) {
        manList.add(man);
    }
}
