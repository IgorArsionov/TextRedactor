package com.example.textredactor.ui.components;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class SidebarButton extends Button {

    private final Label titleLabel = new Label();

    public SidebarButton(String text) {
        titleLabel.setText(text);

        Label dot = new Label("•");
        dot.getStyleClass().add("sidebar-dot");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox content = new HBox(10, dot, titleLabel, spacer);
        content.setAlignment(Pos.CENTER_LEFT);

        setGraphic(content);
        setText(null);

        getStyleClass().add("sidebar-button");
        titleLabel.getStyleClass().add("sidebar-button-text");
        setMaxWidth(Double.MAX_VALUE);
    }

    public void setActive(boolean active) {
        if (active) {
            if (!getStyleClass().contains("active")) {
                getStyleClass().add("active");
            }
        } else {
            getStyleClass().remove("active");
        }
    }
}