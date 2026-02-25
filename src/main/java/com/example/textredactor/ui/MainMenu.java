package com.example.textredactor.ui;

import com.example.textredactor.HelloApplication;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class MainMenu extends VBox {

    private SideBarButton settingsBtn;
    private SideBarButton main;
    private SideBarButton libraryBtn;

    private final VBox notificationBox = new VBox();
    private final Label notificationText = new Label();
    private final PauseTransition hideTimer = new PauseTransition(Duration.seconds(3));

    public MainMenu(int v) {
        super(v);
        setPadding(new Insets(15));
        setPrefWidth(160);
        getStyleClass().add("side-panel");

        settingsBtn = new SideBarButton("Settings", "/icons/settings.png");
        main = new SideBarButton("Main", "/icons/main.png");
        libraryBtn = new SideBarButton("Library", "/icons/library.png");

        main.setOnAction(e -> HelloApplication.showCard("General"));
        settingsBtn.setOnAction(e -> HelloApplication.showCard("Settings"));
        libraryBtn.setOnAction(e -> HelloApplication.showCard("Letters"));

        initNotification();

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        getChildren().addAll(
                main,
                settingsBtn,
                libraryBtn,
                spacer,
                notificationBox
        );
    }

    private void initNotification() {
        notificationBox.getStyleClass().add("menu-notification");
        notificationText.getStyleClass().add("menu-notification-text");

        notificationBox.getChildren().add(notificationText);
        notificationBox.setVisible(false);

        hideTimer.setOnFinished(e -> notificationBox.setVisible(false));
    }

    public void showSuccess(String text) {
        notificationText.setText(text);
        notificationBox.getStyleClass().removeAll("warn");
        notificationBox.getStyleClass().add("success");

        notificationBox.setVisible(true);
        notificationText.setWrapText(true);

        hideTimer.stop();
        hideTimer.playFromStart();
    }

    public void showWarning(String text) {
        notificationText.setText(text);
        notificationBox.getStyleClass().removeAll("success");
        notificationBox.getStyleClass().add("warn");

        notificationBox.setVisible(true);

        hideTimer.stop();
        hideTimer.playFromStart();
    }

    public SideBarButton getSettingsBtn() {
        return settingsBtn;
    }

    public SideBarButton getMain() {
        return main;
    }

    public SideBarButton getLibraryBtn() {
        return libraryBtn;
    }
}