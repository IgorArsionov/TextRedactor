package com.example.textredactor.ui;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class Block extends HBox {
    private TextField wordField;
    private TextField positionField;
    private TextField letterField;

    private Block(String[] data) {
        super(10);
        wordField = new TextField();
        wordField.setPromptText("Слово");
        wordField.setPrefColumnCount(15);
        wordField.setText(data[0]);
        wordField.setPrefWidth(100);

        positionField = new TextField();
        positionField.setPromptText("Позиция");
        positionField.setPrefColumnCount(3);
        positionField.setText(data[1].split(";")[0]);

        letterField = new TextField();
        letterField.setPromptText("Буква");
        letterField.setPrefColumnCount(15);
        letterField.setText(data[1].split(";")[1]);
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
        return wordField.getText() + "=" + positionField.getText() + ";" + letterField.getText();
    }
}
