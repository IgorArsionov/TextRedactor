package com.example.textredactor.pages;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class Letters extends VBox {
    public Letters() {
        super(10);
        setStyle("-fx-padding: 10;");
        VBox.setVgrow(this, Priority.ALWAYS);
        HBox.setHgrow(this, Priority.ALWAYS);

        VBox content = new VBox(10);
        content.setStyle("-fx-padding: 10;");


    }
}
