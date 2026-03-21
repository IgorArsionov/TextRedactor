package com.example.textredactor.ui;

import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.VBox;

public class InfoTile extends VBox {

    private final Label titleLabel = new Label();
    private final Label valueLabel = new Label();

    public InfoTile(String title, String value) {
        titleLabel.setText(title);
        valueLabel.setText(value);

        getStyleClass().add("info-tile");
        titleLabel.getStyleClass().add("info-tile-title");
        valueLabel.getStyleClass().add("info-tile-value");

        setSpacing(2);
        getChildren().addAll(titleLabel, valueLabel);

        setOnMouseClicked(e -> copyValue());
    }

    public void setValue(String value) {
        valueLabel.setText(value);
    }

    public String getValue() {
        return valueLabel.getText();
    }

    private void copyValue() {
        ClipboardContent content = new ClipboardContent();
        content.putString(valueLabel.getText());
        Clipboard.getSystemClipboard().setContent(content);
    }
}