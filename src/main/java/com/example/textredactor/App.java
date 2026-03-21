package com.example.textredactor;

import com.example.textredactor.ui.layout.MainLayout;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        rebuildUi();
        stage.show();
    }

    public static void rebuildUi() {
        MainLayout root = new MainLayout();

        Scene scene = new Scene(root, 1200, 760);
        scene.getStylesheets().add(
                App.class.getResource("/styles/app.css").toExternalForm()
        );

        primaryStage.setTitle("Text Redactor");
        primaryStage.getIcons().add(
                new Image(App.class.getResourceAsStream("/icons/app.png"))
        );
        primaryStage.setMinWidth(960);
        primaryStage.setMinHeight(620);
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch();
    }
}