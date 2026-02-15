package com.example.textredactor.pages;

import com.example.textredactor.HelloApplication;
import com.example.textredactor.engine.Engine;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class General extends VBox {
    private Engine engine = new Engine();

    public General() {
        super(10);
        setStyle("-fx-padding: 10;");
        VBox.setVgrow(this, Priority.ALWAYS);
        HBox.setHgrow(this, Priority.ALWAYS);

        VBox top = new VBox(10);

        TextArea textArea = new TextArea();

        HBox lettersSaver = new HBox(10);
        lettersSaver.setPrefWidth(200);

        Button settings = new Button("Settings");
        settings.setOnAction(e -> {
            HelloApplication.showCard("Settings");
        });

        top.getChildren().addAll(textArea, lettersSaver);

        TextField buckUp = new TextField();
        buckUp.setPromptText("SaveFile Name");

        Button button = new Button("Submit");
        button.setOnAction(e -> {
            textArea.setText(engine.Start(textArea.getText(), buckUp.getText()));
        });



        Button save = new Button("Save");
        save.setOnAction(e -> {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(textArea.getText());
            clipboard.setContent(content);
            textArea.setText("");
        });

        Button library = new Button("Library");

        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(button, buckUp, save, settings, library);

        getChildren().addAll(top,hBox);
    }
}
