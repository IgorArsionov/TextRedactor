package com.example.textredactor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        CreateFile createFile = new CreateFile();

        Engine.setShablon(createFile.getText());

        VBox gBox = new VBox(10);
        gBox.setStyle("-fx-padding: 10;");
        VBox.setVgrow(gBox, Priority.ALWAYS);
        HBox.setHgrow(gBox, Priority.ALWAYS);

        TextArea textArea = new TextArea();
        Button button = new Button("Submit");
        button.setOnAction(e -> {
            textArea.setText(Engine.startEngine(textArea.getText()));
        });
        Button save = new Button("Save");
        save.setOnAction(e -> {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(textArea.getText());
            clipboard.setContent(content);
            textArea.setText("");
        });

        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(button, save);

        gBox.getChildren().addAll(textArea,hBox);

        Scene scene = new Scene(gBox);
        scene.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm()
        );
        stage.setTitle("Text Redactor");

        stage.setScene(scene);
        stage.show();
    }
}
