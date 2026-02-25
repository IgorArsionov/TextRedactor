package com.example.textredactor.pages;

import com.example.textredactor.CreateFile;
import com.example.textredactor.HelloApplication;
import com.example.textredactor.engine.model.Letter;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;

public class Text extends VBox {

    private final TextArea textArea = new TextArea();
    private final Label title = new Label("Letter name");
    private final Label unsavedLabel = new Label("● Unsaved changes");
    private final CreateFile createFile = CreateFile.init();

    private Letter currentLetter;
    private boolean loadingLetter = false;

    public Text() {
        setSpacing(15);
        setStyle("-fx-padding: 15px;");
        VBox.setVgrow(this, Priority.ALWAYS);
        HBox.setHgrow(this, Priority.ALWAYS);

        unsavedLabel.getStyleClass().add("unsaved-label");
        unsavedLabel.setVisible(false);

        HBox topBar = createTopBar();
        VBox editor = createEditor();

        VBox.setVgrow(editor, Priority.ALWAYS);

        textArea.textProperty().addListener((obs, oldText, newText) -> {
            if (!loadingLetter) {
                unsavedLabel.setVisible(true);
            }
        });

        getChildren().addAll(topBar, editor);
    }

    public void setLetter(Letter letter) {
        this.currentLetter = letter;

        loadingLetter = true;

        title.setText(letter.getName());
        textArea.setText(letter.getLetters());

        unsavedLabel.setVisible(false);

        loadingLetter = false;
    }

    private HBox createTopBar() {
        Button back = new Button("← Back");

        back.setOnAction(e -> {
            HelloApplication.showCard("Letters");
        });

        Button save = new Button("Save");

        Region spacer1 = new Region();
        Region spacer2 = new Region();

        HBox.setHgrow(spacer1, Priority.ALWAYS);
        HBox.setHgrow(spacer2, Priority.ALWAYS);

        VBox titleBox = new VBox(title, unsavedLabel);
        titleBox.setAlignment(Pos.CENTER);

        HBox topBar = new HBox(10, back, spacer1, titleBox, spacer2, save);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.getStyleClass().add("top-bar");

        save.setOnAction(e -> {
            if (currentLetter == null) return;
            currentLetter.setLetters(textArea.getText());
            unsavedLabel.setVisible(false);

            String currentText = textArea.getText();
            currentLetter.setLetters(currentText);
            createFile.writeFileByFile(currentText, currentLetter.getFile());
        });

        return topBar;
    }

    private VBox createEditor() {
        textArea.getStyleClass().add("editor-text");
        textArea.setWrapText(true);

        VBox container = new VBox(textArea);
        container.getStyleClass().add("editor-container");

        VBox.setVgrow(container, Priority.ALWAYS);
        VBox.setVgrow(textArea, Priority.ALWAYS);
        textArea.setMaxHeight(Double.MAX_VALUE);

        return container;
    }
}