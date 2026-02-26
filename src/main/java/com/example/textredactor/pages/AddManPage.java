package com.example.textredactor.pages;

import com.example.textredactor.HelloApplication;
import com.example.textredactor.engine.data.Data;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class AddManPage extends VBox {

    private TextField nameField = new TextField();
    private TextField countryField = new TextField();
    private TextField cityField = new TextField();
    private TextArea descriptionArea = new TextArea();

    private Button createBtn = new Button("Create");
    private Button backBtn = new Button("Back");

    public AddManPage() {
        setAlignment(Pos.TOP_CENTER);
        setPadding(new Insets(40));
        setSpacing(20);

        Label title = new Label("Add new man");
        title.getStyleClass().add("page-title");

        VBox card = createCard();

        getChildren().addAll(title, card);
    }

    private VBox createCard() {
        VBox card = new VBox(20);
        card.setMaxWidth(600);
        card.setPadding(new Insets(25));
        card.getStyleClass().add("editor-container");

        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);

        nameField.setPromptText("Name");
        countryField.setPromptText("Country");
        cityField.setPromptText("City");

        grid.add(createField("Name", nameField), 0, 0);
        grid.add(createField("Country", countryField), 1, 0);
        grid.add(createField("City", cityField), 0, 1);

        descriptionArea.setPromptText("Description...");
        descriptionArea.setWrapText(true);
        descriptionArea.setPrefHeight(150);

        VBox descriptionBox = new VBox(6,
                new Label("Description"),
                descriptionArea
        );

        createBtn.setPrefHeight(40);
        createBtn.getStyleClass().add("accent-button");

        backBtn.setOnAction(e -> {
            HelloApplication.showCard(Data.pageManPage);
        });

        HBox buttonBox = new HBox(10, backBtn, createBtn);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        card.getChildren().addAll(grid, descriptionBox, buttonBox);

        return card;
    }

    private VBox createField(String labelText, TextField field) {
        Label label = new Label(labelText);
        VBox box = new VBox(5, label, field);
        VBox.setVgrow(field, Priority.NEVER);
        return box;
    }
}