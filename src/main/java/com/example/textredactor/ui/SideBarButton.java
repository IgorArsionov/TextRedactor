package com.example.textredactor.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;

public class SideBarButton extends Button {

    private ImageView iconView;
    private final Label textLabel;

    public SideBarButton(String text, String iconPath) {
        // Текст
        textLabel = new Label(text);
        setIconView(iconPath);
        // Контейнер
        HBox content = new HBox(10, iconView, textLabel);
        content.setAlignment(Pos.CENTER_LEFT);
        content.setPadding(new Insets(6, 10, 6, 10));

        setMaxWidth(Double.MAX_VALUE);

        setGraphic(content);
        setText(null); // ❗ важно
        setAlignment(Pos.CENTER_LEFT);

        getStyleClass().add("side-button");
    }

    public SideBarButton(String text) {
        // Текст
        textLabel = new Label(text);
        // Контейнер
        HBox content = new HBox(10, textLabel);
        content.setAlignment(Pos.CENTER_LEFT);
        content.setPadding(new Insets(6, 10, 6, 10));

        setMaxWidth(Double.MAX_VALUE);

        setGraphic(content);
        setText(null); // ❗ важно
        setAlignment(Pos.CENTER_LEFT);

        getStyleClass().add("side-button");
    }

    private void setIconView(String iconPath) {
        // Иконка
        iconView = new ImageView(
                new Image(getClass().getResource(iconPath).toExternalForm())
        );
        iconView.setFitWidth(18);
        iconView.setFitHeight(18);
        iconView.setPreserveRatio(true);
    }

    public void setIcon(String iconPath) {
        iconView.setImage(
                new Image(getClass().getResourceAsStream(iconPath))
        );
    }

    public void setLabel(String text) {
        textLabel.setText(text);
    }
}

