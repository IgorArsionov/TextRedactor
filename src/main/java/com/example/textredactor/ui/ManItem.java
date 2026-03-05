package com.example.textredactor.ui;

import com.example.textredactor.engine.model.Man;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class ManItem extends HBox {

    private final Button openBtn = new Button("Open");
    private Man man;

    public ManItem(Man man) {
        this.man = man;

        setSpacing(10);
        setPadding(new Insets(8));
        getStyleClass().add("man-item");

        VBox info = new VBox(2);

        Label nameLabel = new Label(man.getName());
        nameLabel.getStyleClass().add("man-name");

        Label metaLabel = new Label(man.getCountry() + " • " + man.getCity());
        metaLabel.getStyleClass().add("man-meta");

        info.getChildren().addAll(nameLabel, metaLabel);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        openBtn.getStyleClass().add("small-btn");

        getChildren().addAll(info, spacer, openBtn);
    }

    public Button getOpenBtn() {
        return openBtn;
    }

    public Man getMan() {
        return man;
    }
}