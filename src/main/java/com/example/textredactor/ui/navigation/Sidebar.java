package com.example.textredactor.ui.navigation;

import com.example.textredactor.App;
import com.example.textredactor.ui.components.SidebarButton;
import com.example.textredactor.ui.i18n.I18n;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class Sidebar extends VBox {

    private final Map<String, SidebarButton> buttons = new LinkedHashMap<>();

    public Sidebar(PageManager pageManager) {
        getStyleClass().add("sidebar");
        setPadding(new Insets(18));
        setSpacing(8);
        setPrefWidth(190);

        SidebarButton dashboardBtn = createNavButton(
                "dashboard",
                I18n.get("sidebar.dashboard"),
                pageManager
        );

        SidebarButton textsBtn = createNavButton(
                "texts",
                I18n.get("sidebar.texts"),
                pageManager
        );

        SidebarButton mansBtn = createNavButton(
                "mans",
                I18n.get("sidebar.mans"),
                pageManager
        );

        SidebarButton settingsBtn = createNavButton(
                "settings",
                I18n.get("sidebar.settings"),
                pageManager
        );

        Label langTitle = new Label(I18n.get("sidebar.language"));
        langTitle.getStyleClass().add("sidebar-section-title");

        Button ukBtn = new Button(I18n.get("sidebar.lang.uk"));
        ukBtn.getStyleClass().add("lang-button");
        ukBtn.setOnAction(e -> {
            I18n.setLocale(new Locale("uk"));
            App.rebuildUi();
        });

        Button enBtn = new Button(I18n.get("sidebar.lang.en"));
        enBtn.getStyleClass().add("lang-button");
        enBtn.setOnAction(e -> {
            I18n.setLocale(new Locale("en"));
            App.rebuildUi();
        });

        HBox langBox = new HBox(8, ukBtn, enBtn);

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        getChildren().addAll(
                dashboardBtn,
                textsBtn,
                mansBtn,
                settingsBtn,
                spacer,
                langTitle,
                langBox
        );

        setActive("dashboard");
    }

    private SidebarButton createNavButton(String key, String title, PageManager pageManager) {
        SidebarButton button = new SidebarButton(title);
        button.setOnAction(e -> {
            pageManager.showPage(key);
            setActive(key);
        });
        buttons.put(key, button);
        return button;
    }

    private void setActive(String activeKey) {
        for (Map.Entry<String, SidebarButton> entry : buttons.entrySet()) {
            entry.getValue().setActive(entry.getKey().equals(activeKey));
        }
    }
}