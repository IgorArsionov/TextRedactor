package com.example.textredactor.ui.navigation;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;

import java.util.HashMap;
import java.util.Map;

public class PageManager extends StackPane {

    private final Map<String, Node> pages = new HashMap<>();

    public void registerPage(String key, Node page) {
        pages.put(key, page);
        getChildren().add(page);
        page.setVisible(false);
        page.managedProperty().bind(page.visibleProperty());
    }

    public void showPage(String key) {
        for (Map.Entry<String, Node> entry : pages.entrySet()) {
            boolean active = entry.getKey().equals(key);
            entry.getValue().setVisible(active);
        }
    }

    public Node getPage(String key) {
        return pages.get(key);
    }
}