package com.example.textredactor.ui.layout;

import com.example.textredactor.ui.i18n.I18n;
import com.example.textredactor.ui.navigation.PageManager;
import com.example.textredactor.ui.navigation.Sidebar;
import com.example.textredactor.ui.pages.DashboardPage;
import com.example.textredactor.ui.pages.TextsPage;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class MainLayout extends HBox {

    private final PageManager pageManager = new PageManager();

    public MainLayout() {
        getStyleClass().add("app-root");

        Sidebar sidebar = new Sidebar(pageManager);

        StackPane contentWrapper = new StackPane(pageManager);
        contentWrapper.getStyleClass().add("content-wrapper");
        HBox.setHgrow(contentWrapper, Priority.ALWAYS);

        registerPages();

        getChildren().addAll(sidebar, contentWrapper);
    }

    private void registerPages() {
        pageManager.registerPage("dashboard", new DashboardPage());
        pageManager.registerPage("texts", new TextsPage());
        pageManager.registerPage("mans", createPlaceholder(I18n.get("sidebar.mans")));
        pageManager.registerPage("settings", createPlaceholder(I18n.get("sidebar.settings")));

        pageManager.showPage("dashboard");
    }

    private VBox createPlaceholder(String titleText) {
        Label title = new Label(titleText);
        title.getStyleClass().add("page-title");

        Label subtitle = new Label(I18n.get("placeholder.notBuilt"));
        subtitle.getStyleClass().add("page-subtitle");

        VBox box = new VBox(8, title, subtitle);
        box.getStyleClass().add("page");
        return box;
    }
}