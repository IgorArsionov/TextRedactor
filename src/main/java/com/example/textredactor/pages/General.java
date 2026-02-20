package com.example.textredactor.pages;

import com.example.textredactor.engine.Engine;
import com.example.textredactor.ui.MainMenu;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.*;

public class General extends HBox {
    private final Engine engine = new Engine();
    private final TextArea textArea = new TextArea();
    private final TextField fileNameField = new TextField();

    public General() {
        setSpacing(0);
        setPrefSize(900, 600);

        MainMenu menu = new MainMenu(12);
        menu.getMain().getStyleClass().add("active");

        VBox content = initContent();

        getChildren().addAll(menu, content);
        HBox.setHgrow(content, Priority.ALWAYS);
    }

    private Button initSubmitBtn() {
        Button submitBtn = new Button("Submit");
        submitBtn.getStyleClass().add("primary");
        submitBtn.setOnAction(e -> {
            textArea.setText(engine.Start(textArea.getText(), fileNameField.getText()));
            if(!fileNameField.getText().isEmpty()) {
                fileNameField.clear();
            }
        });
        return submitBtn;
    }

    private Button initSaveBtn() {
        Button saveBtn = new Button("Save");
        saveBtn.setOnAction(e -> {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent saveContent = new ClipboardContent();
            saveContent.putString(textArea.getText());
            clipboard.setContent(saveContent);
        });

        return saveBtn;
    }

    private Button initClearBtn() {
        Button clearBtn = new Button("X");
        clearBtn.getStyleClass().add("clear");
        clearBtn.setOnAction(e -> {
            textArea.setText("");
        });
        return clearBtn;
    }

    private VBox initContent() {
        VBox content = new VBox(12);
        content.setPadding(new Insets(15));
        content.setFillWidth(true);

        textArea.setPromptText("Paste or write your text here...");
        VBox.setVgrow(textArea, Priority.ALWAYS);

        fileNameField.setPromptText("Filename");

        Button submitBtn = initSubmitBtn();
        Button saveBtn = initSaveBtn();
        Button clearBtn = initClearBtn();

        HBox bottomBar = new HBox(10, fileNameField, submitBtn, saveBtn, clearBtn);
        bottomBar.setPadding(new Insets(5, 0, 0, 0));

        HBox.setHgrow(fileNameField, Priority.ALWAYS);

        content.getChildren().addAll(textArea, bottomBar);

        return content;
    }
}
