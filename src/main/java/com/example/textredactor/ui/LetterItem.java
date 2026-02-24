package com.example.textredactor.ui;

import com.example.textredactor.engine.Engine;
import com.example.textredactor.engine.model.Letter;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class LetterItem extends HBox {
    private Button delete;

    public LetterItem(Letter letter) {
        super(10);

        getStyleClass().add("letter-item");
        setAlignment(Pos.CENTER_LEFT);

        Label title = new Label(letter.getName());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button open = new Button("Open");
        open.getStyleClass().add("primary");

        delete = new Button("Delete");
        delete.getStyleClass().add("danger");

        getChildren().addAll(title, spacer, open, delete);
    }

    public Button getDelete() {
        return delete;
    }
}
