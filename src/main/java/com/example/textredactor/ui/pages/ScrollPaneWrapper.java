package com.example.textredactor.ui.pages;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;

public class ScrollPaneWrapper extends ScrollPane {

    public ScrollPaneWrapper(Node content) {
        super(content);
        getStyleClass().add("app-scroll-pane");
        setFitToWidth(true);
        setHbarPolicy(ScrollBarPolicy.NEVER);
    }
}