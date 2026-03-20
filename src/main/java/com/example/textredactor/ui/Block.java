package com.example.textredactor.ui;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class Block extends HBox {
    private TextField wordField;
    private TextField positionField;
    private TextField letterField;

    public Block(String[] data) {
        super(10);
        init(data[0].trim(), data[1].split(";")[0].trim(), data[1].split(";")[1].trim());
    }

    public Block() {
        super(10);
        init("", "", "");
    }

    public String getWordField() {
        return wordField.getText();
    }

    public String getPositionField() {
        return positionField.getText();
    }

    public String getLetterField() {
        return letterField.getText();
    }

    public String toString() {
        return wordField.getText().trim() + "=" + positionField.getText().trim() + ";" + letterField.getText().trim();
    }

    private void init(String v1, String v2, String v3) {
        wordField = new TextField();
        wordField.setPromptText("Слово");
        wordField.setPrefColumnCount(15);
        wordField.setText(v1);
        wordField.setPrefWidth(100);

        positionField = new TextField();
        positionField.setPromptText("Позиция");
        positionField.setPrefColumnCount(3);
        positionField.setText(v2);

        letterField = new TextField();
        letterField.setPromptText("Буква");
        letterField.setPrefColumnCount(15);
        letterField.setText(v3);
        letterField.setPrefWidth(100);

        CheckBox autoPositionCheckBox = new CheckBox("All");

        autoPositionCheckBox
                .selectedProperty()
                .addListener((obs, oldVal, newVal) -> {
                    if (newVal) {
                        positionField.setText("0");
                        positionField.setDisable(true);
                    } else {
                        positionField.setDisable(false);
                    }
                });

        getChildren().addAll(wordField, positionField, letterField, autoPositionCheckBox);
    }
}
