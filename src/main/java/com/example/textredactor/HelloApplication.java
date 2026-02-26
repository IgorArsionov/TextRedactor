package com.example.textredactor;

import com.example.textredactor.engine.Engine;
import com.example.textredactor.engine.data.Data;
import com.example.textredactor.pages.*;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
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
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icons/icon.png")));
        pages.put(Data.pageGeneral, new General());
        pages.put(Data.pageSettings, new Settings());
        pages.put(Data.pageLetters, new Letters());
        pages.put(Data.pageText, new Text());
        pages.put(Data.pageManPage, new ManPage());
        pages.put(Data.pageAddManPage, new AddManPage());

        CreateFile createFile = CreateFile.init();

        Engine.setShablon(createFile.getText());

        vBox = new VBox();
        VBox.setVgrow(vBox, Priority.ALWAYS);
        HBox.setHgrow(vBox, Priority.ALWAYS);

        showCard(Data.pageGeneral);

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

    public static Node getCard(String name) {
        return pages.get(name);
    }
}
