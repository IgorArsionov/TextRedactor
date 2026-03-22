package com.example.textredactor.ui.pages;

import com.example.textredactor.engine.AppFacade;
import com.example.textredactor.engine.model.Man;
import com.example.textredactor.engine.model.Text;
import com.example.textredactor.ui.i18n.I18n;
import com.example.textredactor.ui.layout.MainLayout;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.util.List;
import java.util.Map;

public class DashboardPage extends VBox {

    private final AppFacade appFacade = new AppFacade();

    public DashboardPage() {
        getStyleClass().add("page");
        setSpacing(18);
        setPadding(new Insets(24));
        setFillWidth(true);

        HBox header = createHeader();
        HBox content = createContent();

        VBox.setVgrow(content, Priority.ALWAYS);

        getChildren().addAll(header, content);
    }

    private HBox createHeader() {
        Label title = new Label(I18n.get("dashboard.title"));
        title.getStyleClass().add("page-title");

        Label subtitle = new Label(I18n.get("dashboard.subtitle"));
        subtitle.getStyleClass().add("page-subtitle");

        VBox titleBox = new VBox(4, title, subtitle);

        Button textsButton = new Button(I18n.get("dashboard.quick.texts"));
        textsButton.getStyleClass().add("ghost-button");
        textsButton.setOnAction(e -> MainLayout.showPage("texts"));

        Button mansButton = new Button(I18n.get("dashboard.quick.mans"));
        mansButton.getStyleClass().add("ghost-button");
        mansButton.setOnAction(e -> MainLayout.showPage("mans"));

        Button replacementsButton = new Button(I18n.get("dashboard.quick.replacements"));
        replacementsButton.getStyleClass().add("primary-button");
        replacementsButton.setOnAction(e -> MainLayout.showPage("settings"));

        HBox actions = new HBox(8, textsButton, mansButton, replacementsButton);
        actions.setAlignment(Pos.CENTER_RIGHT);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox header = new HBox(16, titleBox, spacer, actions);
        header.setAlignment(Pos.CENTER_LEFT);

        return header;
    }

    private HBox createContent() {
        VBox leftColumn = new VBox(16);
        VBox rightColumn = new VBox(16);

        HBox.setHgrow(leftColumn, Priority.ALWAYS);
        HBox.setHgrow(rightColumn, Priority.ALWAYS);

        VBox overviewCard = createOverviewCard();
        VBox recentTextsCard = createRecentTextsCard();
        VBox recentMansCard = createRecentMansCard();
        VBox quickHelpCard = createQuickHelpCard();

        VBox.setVgrow(recentTextsCard, Priority.ALWAYS);
        VBox.setVgrow(recentMansCard, Priority.ALWAYS);

        leftColumn.getChildren().addAll(overviewCard, recentTextsCard);
        rightColumn.getChildren().addAll(quickHelpCard, recentMansCard);

        HBox content = new HBox(16, leftColumn, rightColumn);
        HBox.setHgrow(leftColumn, Priority.ALWAYS);
        HBox.setHgrow(rightColumn, Priority.ALWAYS);

        return content;
    }

    private VBox createOverviewCard() {
        VBox card = createCard(I18n.get("dashboard.overview"));

        List<Text> texts = appFacade.getAllTexts();
        List<Man> mans = appFacade.getAllMans();
        Map<String, String> replacements = appFacade.getAllReplacements();

        card.getChildren().addAll(
                createStatRow(I18n.get("dashboard.stats.texts"), String.valueOf(texts.size())),
                createStatRow(I18n.get("dashboard.stats.mans"), String.valueOf(mans.size())),
                createStatRow(I18n.get("dashboard.stats.replacements"), String.valueOf(replacements.size()))
        );

        return card;
    }

    private VBox createRecentTextsCard() {
        VBox card = createCard(I18n.get("dashboard.recent.texts"));

        List<Text> texts = appFacade.getAllTexts();

        if (texts.isEmpty()) {
            card.getChildren().add(createEmptyRow(I18n.get("dashboard.empty.texts")));
            return card;
        }

        int start = Math.max(0, texts.size() - 5);
        List<Text> latest = texts.subList(start, texts.size());

        for (int i = latest.size() - 1; i >= 0; i--) {
            Text text = latest.get(i);
            card.getChildren().add(createSimpleRow(
                    safeOrDefault(text.getTitle(), I18n.get("dashboard.untitled")),
                    buildTextPreview(text.getText())
            ));
        }

        return card;
    }

    private VBox createRecentMansCard() {
        VBox card = createCard(I18n.get("dashboard.recent.mans"));

        List<Man> mans = appFacade.getAllMans();

        if (mans.isEmpty()) {
            card.getChildren().add(createEmptyRow(I18n.get("dashboard.empty.mans")));
            return card;
        }

        int start = Math.max(0, mans.size() - 5);
        List<Man> latest = mans.subList(start, mans.size());

        for (int i = latest.size() - 1; i >= 0; i--) {
            Man man = latest.get(i);
            card.getChildren().add(createSimpleRow(
                    safeOrDefault(man.getName(), I18n.get("dashboard.unknown")),
                    buildManMeta(man)
            ));
        }

        return card;
    }

    private VBox createQuickHelpCard() {
        VBox card = createCard(I18n.get("dashboard.quick.title"));

        Button openTexts = new Button(I18n.get("dashboard.quick.openTexts"));
        openTexts.getStyleClass().add("ghost-button");
        openTexts.setMaxWidth(Double.MAX_VALUE);
        openTexts.setOnAction(e -> MainLayout.showPage("texts"));

        Button openMans = new Button(I18n.get("dashboard.quick.openMans"));
        openMans.getStyleClass().add("ghost-button");
        openMans.setMaxWidth(Double.MAX_VALUE);
        openMans.setOnAction(e -> MainLayout.showPage("mans"));

        Button openReplacements = new Button(I18n.get("dashboard.quick.openReplacements"));
        openReplacements.getStyleClass().add("ghost-button");
        openReplacements.setMaxWidth(Double.MAX_VALUE);
        openReplacements.setOnAction(e -> MainLayout.showPage("settings"));

        VBox buttons = new VBox(8, openTexts, openMans, openReplacements);
        buttons.setFillWidth(true);

        card.getChildren().addAll(
                createHintLabel(I18n.get("dashboard.quick.hint1")),
                createHintLabel(I18n.get("dashboard.quick.hint2")),
                buttons
        );

        return card;
    }

    private VBox createCard(String titleText) {
        Label title = new Label(titleText);
        title.getStyleClass().add("card-title");

        VBox card = new VBox(12, title);
        card.getStyleClass().add("card");
        card.setPadding(new Insets(16));

        return card;
    }

    private HBox createStatRow(String labelText, String valueText) {
        Label label = new Label(labelText);
        label.getStyleClass().add("stat-title");

        Label value = new Label(valueText);
        value.getStyleClass().add("stat-value");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox row = new HBox(label, spacer, value);
        row.getStyleClass().add("simple-row");
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(10, 12, 10, 12));

        return row;
    }

    private VBox createSimpleRow(String title, String meta) {
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("text-item-title");

        Label metaLabel = new Label(meta);
        metaLabel.getStyleClass().add("text-item-meta");
        metaLabel.setWrapText(true);

        VBox row = new VBox(3, titleLabel, metaLabel);
        row.getStyleClass().add("simple-row");
        row.setPadding(new Insets(10, 12, 10, 12));

        return row;
    }

    private HBox createEmptyRow(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("empty-state-label");

        HBox row = new HBox(label);
        row.getStyleClass().add("simple-row");
        row.setPadding(new Insets(10, 12, 10, 12));

        return row;
    }

    private Label createHintLabel(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("row-text");
        label.setWrapText(true);
        return label;
    }

    private String buildTextPreview(String text) {
        if (text == null || text.isBlank()) {
            return I18n.get("dashboard.empty.preview");
        }

        String normalized = text.replace("\n", " ").replace("\r", " ").trim();
        return normalized.length() > 60 ? normalized.substring(0, 60) + "..." : normalized;
    }

    private String buildManMeta(Man man) {
        String country = safe(man.getCountry());
        String city = safe(man.getCity());

        if (!country.equals("--") && !city.equals("--") && !country.isBlank() && !city.isBlank()) {
            return country + " • " + city;
        }

        if (!country.equals("--") && !country.isBlank()) {
            return country;
        }

        if (!city.equals("--") && !city.isBlank()) {
            return city;
        }

        return I18n.get("dashboard.empty.meta");
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }

    private String safeOrDefault(String value, String fallback) {
        return value == null || value.isBlank() ? fallback : value;
    }
}