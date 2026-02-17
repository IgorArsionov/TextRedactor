package com.example.textredactor.pages;

import com.example.textredactor.engine.Engine;
import com.example.textredactor.ui.MainMenu;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.*;

public class General extends HBox {
    private Engine engine = new Engine();

    public General() {

        /* ===== ROOT ===== */
        setSpacing(0);
        setPrefSize(900, 600);

        /* ===== LEFT MENU ===== */
        MainMenu menu = new MainMenu(12);
        menu.getMain().getStyleClass().add("active");

        /* ===== CONTENT ===== */
        VBox content = new VBox(12);
        content.setPadding(new Insets(15));
        content.setFillWidth(true);

        // Большое поле ввода
        TextArea textArea = new TextArea();
        textArea.setPromptText("Paste or write your text here...");
        VBox.setVgrow(textArea, Priority.ALWAYS);

        // Нижняя панель
        TextField fileNameField = new TextField();
        fileNameField.setPromptText("Filename");

        Button submitBtn = new Button("Submit");
        submitBtn.getStyleClass().add("primary");
        submitBtn.setOnAction(e -> {
            textArea.setText(engine.Start(textArea.getText(), fileNameField.getText()));
        });

        Button saveBtn = new Button("Save");
        saveBtn.setOnAction(e -> {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent saveContent = new ClipboardContent();
            saveContent.putString(textArea.getText());
            clipboard.setContent(saveContent);
            textArea.setText("");
        });

        HBox bottomBar = new HBox(10, fileNameField, submitBtn, saveBtn);
        bottomBar.setPadding(new Insets(5, 0, 0, 0));

        HBox.setHgrow(fileNameField, Priority.ALWAYS);

        content.getChildren().addAll(textArea, bottomBar);

        /* ===== ADD TO ROOT ===== */
        getChildren().addAll(menu, content);
        HBox.setHgrow(content, Priority.ALWAYS);
    }
}
