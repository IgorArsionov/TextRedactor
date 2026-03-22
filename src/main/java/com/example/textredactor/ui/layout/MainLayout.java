package com.example.textredactor.ui.layout;

import com.example.textredactor.ui.i18n.I18n;
import com.example.textredactor.ui.navigation.PageManager;
import com.example.textredactor.ui.navigation.Sidebar;
import com.example.textredactor.ui.pages.*;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class MainLayout extends HBox {

    private static PageManager pageManager;
    private static ManPage manPage;
    private static ManEditorPage manEditorPage;
    private static ManDetailsPage manDetailsPage;

    public MainLayout() {
        getStyleClass().add("app-root");

        pageManager = new PageManager();

        Sidebar sidebar = new Sidebar(pageManager);

        StackPane contentWrapper = new StackPane(pageManager);
        contentWrapper.getStyleClass().add("content-wrapper");
        HBox.setHgrow(contentWrapper, Priority.ALWAYS);

        registerPages();

        getChildren().addAll(sidebar, contentWrapper);
    }

    private void registerPages() {
        manPage = new ManPage();
        manEditorPage = new ManEditorPage();
        manDetailsPage = new ManDetailsPage();

        pageManager.registerPage("dashboard", new DashboardPage());
        pageManager.registerPage("texts", new TextsPage());
        pageManager.registerPage("mans", manPage);
        pageManager.registerPage("manEditor", manEditorPage);
        pageManager.registerPage("manDetails", manDetailsPage);
        pageManager.registerPage("settings", new ReplacementsPage());

        pageManager.showPage("dashboard");
    }

    public static void showPage(String key) {
        pageManager.showPage(key);
    }

    public static ManPage getManPage() {
        return manPage;
    }

    public static ManEditorPage getManEditorPage() {
        return manEditorPage;
    }

    public static ManDetailsPage getManDetailsPage() {
        return manDetailsPage;
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