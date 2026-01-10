package com.example.textredactor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Engine {

    public static Map<String, String> shablon = new HashMap<String, String>();

    public static String startEngine(String text) {
        String[] words = text.split(" ");
        StringBuilder sb = new StringBuilder();

        List<Word> list = Arrays.stream(words)
                .map(Word::new)
                .toList();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).toString()).append(" ");
        }
        return sb.toString();
    }

    public static void setShablon(List<String> words) {
        for (int i = 0; i < words.size(); i++) {
            String[] value = words.get(i).split(" = ");
            shablon.put(value[0], value[1]);
        }
    }


}
