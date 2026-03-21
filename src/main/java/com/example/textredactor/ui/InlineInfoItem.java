package com.example.textredactor.ui;

import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;

public class InlineInfoItem extends HBox {

    private final Label titleLabel = new Label();
    private final Label valueLabel = new Label();

    public InlineInfoItem(String title, String value) {
        titleLabel.setText(title + ":");
        valueLabel.setText(value);

        getStyleClass().add("inline-info-item");
        titleLabel.getStyleClass().add("inline-info-title");
        valueLabel.getStyleClass().add("inline-info-value");

        setSpacing(4);
        getChildren().addAll(titleLabel, valueLabel);

        setOnMouseClicked(e -> copyValue());
    }

    public void setValue(String value) {
        valueLabel.setText(value);
    }

    private void copyValue() {
        ClipboardContent content = new ClipboardContent();
        content.putString(valueLabel.getText());
        Clipboard.getSystemClipboard().setContent(content);
    }
}