package com.example.textredactor.ui;

import com.example.textredactor.HelloApplication;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

public class MainMenu extends VBox {
    private SideBarButton settingsBtn;
    private SideBarButton main;
    private SideBarButton libraryBtn;

    public MainMenu(int v) {
        super(v);
        setPadding(new Insets(15));
        setPrefWidth(160);
        getStyleClass().add("side-panel");

        settingsBtn =
                new SideBarButton("Settings", "/icons/settings.png");
        main =
                new SideBarButton("Main", "/icons/main.png");

        settingsBtn.setOnAction(e -> {
            HelloApplication.showCard("Settings");
        });

        libraryBtn =
                new SideBarButton("Library", "/icons/library.png");

        main.setOnAction(e -> {
            HelloApplication.showCard("General");
        });

        libraryBtn.setOnAction(e -> {
            HelloApplication.showCard("Letters");
        });

        getChildren().addAll(main, settingsBtn, libraryBtn);
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
