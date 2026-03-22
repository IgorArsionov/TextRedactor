package com.example.textredactor.ui.pages;

import com.example.textredactor.engine.AppFacade;
import com.example.textredactor.ui.i18n.I18n;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import java.util.LinkedHashMap;
import java.util.Map;

public class ReplacementsPage extends VBox {

    private final AppFacade appFacade = new AppFacade();

    private final VBox rulesBox = new VBox(6);

    private final TextField searchField = new TextField();
    private final TextField fromField = new TextField();
    private final TextField toField = new TextField();
    private final Label statusLabel = new Label();

    public ReplacementsPage() {
        getStyleClass().add("page");
        setSpacing(18);
        setPadding(new Insets(24));
        setFillWidth(true);

        HBox header = createHeader();
        VBox content = createContent();

        VBox.setVgrow(content, Priority.ALWAYS);

        getChildren().addAll(header, content);

        loadRulesFromFacade();
    }

    private HBox createHeader() {
        Label title = new Label(I18n.get("replacements.title"));
        title.getStyleClass().add("page-title");

        Label subtitle = new Label(I18n.get("replacements.subtitle"));
        subtitle.getStyleClass().add("page-subtitle");

        VBox titleBox = new VBox(4, title, subtitle);

        searchField.setPromptText(I18n.get("replacements.search"));
        searchField.getStyleClass().add("search-field");
        searchField.setMaxWidth(260);

        searchField.textProperty().addListener((obs, oldValue, newValue) -> {
            filterReplacements(newValue);
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox header = new HBox(16, titleBox, spacer, searchField);
        header.setAlignment(Pos.CENTER_LEFT);

        return header;
    }

    private VBox createContent() {
        VBox wrapper = new VBox(16);

        VBox addCard = createAddCard();
        VBox listCard = createListCard();

        VBox.setVgrow(listCard, Priority.ALWAYS);

        wrapper.getChildren().addAll(addCard, listCard);
        return wrapper;
    }

    private VBox createAddCard() {
        VBox card = new VBox(12);
        card.getStyleClass().add("card");
        card.setPadding(new Insets(16));

        Label title = new Label(I18n.get("replacements.addRule"));
        title.getStyleClass().add("card-title");

        fromField.setPromptText(I18n.get("replacements.from"));
        fromField.getStyleClass().add("search-field");

        toField.setPromptText(I18n.get("replacements.to"));
        toField.getStyleClass().add("search-field");

        Button addButton = new Button(I18n.get("replacements.add"));
        addButton.getStyleClass().add("primary-button");

        addButton.setOnAction(e -> {
            String from = fromField.getText() == null ? "" : fromField.getText().trim();
            String to = toField.getText() == null ? "" : toField.getText().trim();

            if (from.isEmpty() || to.isEmpty()) {
                setStatus(I18n.get("replacements.fillBoth"), false);
                return;
            }

            Map<String, String> currentRules = appFacade.getAllReplacements();

            if (currentRules.containsKey(from)) {
                appFacade.updateReplacement(from, to);
                setStatus(I18n.get("replacements.updated"), true);
            } else {
                appFacade.addReplacement(from, to);
                setStatus(I18n.get("replacements.added"), true);
            }

            fromField.clear();
            toField.clear();
            loadRulesFromFacade();
        });

        statusLabel.getStyleClass().add("editor-status");
        statusLabel.setVisible(false);
        statusLabel.setManaged(false);

        HBox inputs = new HBox(10, fromField, toField, addButton);
        HBox.setHgrow(fromField, Priority.ALWAYS);
        HBox.setHgrow(toField, Priority.ALWAYS);

        card.getChildren().addAll(title, inputs, statusLabel);
        return card;
    }

    private VBox createListCard() {
        VBox card = new VBox(12);
        card.getStyleClass().add("card");
        card.setPadding(new Insets(16));

        Label title = new Label(I18n.get("replacements.list"));
        title.getStyleClass().add("card-title");

        ScrollPaneWrapper wrapper = new ScrollPaneWrapper(rulesBox);
        VBox.setVgrow(wrapper, Priority.ALWAYS);

        card.getChildren().addAll(title, wrapper);
        return card;
    }

    private void loadRulesFromFacade() {
        filterReplacements(searchField.getText());
    }

    private void filterReplacements(String query) {
        Map<String, String> all = appFacade.getAllReplacements();

        if (query == null || query.isBlank()) {
            renderReplacements(all);
            return;
        }

        String normalized = query.trim().toLowerCase();
        Map<String, String> filtered = new LinkedHashMap<>();

        for (Map.Entry<String, String> entry : all.entrySet()) {
            String key = safe(entry.getKey()).toLowerCase();
            String value = safe(entry.getValue()).toLowerCase();

            if (key.contains(normalized) || value.contains(normalized)) {
                filtered.put(entry.getKey(), entry.getValue());
            }
        }

        renderReplacements(filtered);
    }

    private void renderReplacements(Map<String, String> rules) {
        rulesBox.getChildren().clear();

        if (rules.isEmpty()) {
            Label emptyLabel = new Label(I18n.get("replacements.notFound"));
            emptyLabel.getStyleClass().add("empty-state-label");
            rulesBox.getChildren().add(emptyLabel);
            return;
        }

        for (Map.Entry<String, String> entry : rules.entrySet()) {
            rulesBox.getChildren().add(createRuleItem(entry.getKey(), entry.getValue()));
        }
    }

    private HBox createRuleItem(String from, String to) {
        Label fromLabel = new Label(from);
        fromLabel.getStyleClass().add("rule-from");

        Label arrow = new Label("→");
        arrow.getStyleClass().add("rule-arrow");

        Label toLabel = new Label(to);
        toLabel.getStyleClass().add("rule-to");

        HBox left = new HBox(8, fromLabel, arrow, toLabel);
        left.setAlignment(Pos.CENTER_LEFT);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button deleteButton = new Button("✕");
        deleteButton.getStyleClass().add("rule-delete-button");

        HBox item = new HBox(10, left, spacer, deleteButton);
        item.getStyleClass().add("rule-item");
        item.setAlignment(Pos.CENTER_LEFT);
        item.setPadding(new Insets(10, 12, 10, 12));

        deleteButton.setOnAction(e -> {
            appFacade.deleteReplacement(from);
            loadRulesFromFacade();
            setStatus(I18n.get("replacements.deleted"), true);
        });

        item.setOnMouseClicked(e -> {
            fromField.setText(from);
            toField.setText(to);
        });

        return item;
    }

    private void setStatus(String text, boolean success) {
        statusLabel.setText(text);

        if (success) {
            if (!statusLabel.getStyleClass().contains("saved")) {
                statusLabel.getStyleClass().add("saved");
            }
        } else {
            statusLabel.getStyleClass().remove("saved");
        }

        statusLabel.setVisible(true);
        statusLabel.setManaged(true);
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }
}