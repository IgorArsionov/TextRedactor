package com.example.textredactor;

import com.example.textredactor.engine.Engine;
import com.example.textredactor.pages.General;
import com.example.textredactor.pages.Settings;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HelloApplication extends Application {
    private static Map<String, Node> pages = new HashMap<>();
    private static VBox vBox;

    @Override
    public void start(Stage stage) throws IOException {
        stage.setWidth(800);
        stage.setHeight(600);
        pages.put("General", new General());
        pages.put("Settings", new Settings());

        CreateFile createFile = CreateFile.init();

        Engine.setShablon(createFile.getText());

        vBox = new VBox();

        showCard("General");

        Scene scene = new Scene(vBox);
        scene.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm()
        );
        stage.setTitle("Text Redactor");

        stage.setScene(scene);
        stage.show();
    }

    public static void showCard(String name) {
        Node node = pages.get(name);
        vBox.getChildren().setAll(node);
    }
}
