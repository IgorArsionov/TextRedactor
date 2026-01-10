package com.example.textredactor;

public class Word {
    private int start = 0;
    private int end;
    private String before;
    private String text;
    private String after;

    public Word(String word) {
        end = word.length() - 1;
        while (start <= end && !Character.isLetter(word.charAt(start))) {
            start++;
        }
        while (end >= start && !Character.isLetter(word.charAt(end))) {
            end--;
        }

        before = word.substring(0, start);
        after = word.substring(end + 1);
        text = word.substring(start, end + 1);
        if (Engine.shablon.containsKey(text)) {
            text = Engine.shablon.get(text);
            System.out.println(text + " :Переделано");
        }
    }

    public String getText() {
        return text;
    }

    public String toString() {
        return before + text + after;
    }
}
