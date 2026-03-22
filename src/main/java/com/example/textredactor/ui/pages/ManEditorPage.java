package com.example.textredactor.ui.pages;

import com.example.textredactor.engine.AppFacade;
import com.example.textredactor.engine.model.Man;
import com.example.textredactor.ui.i18n.I18n;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ManEditorPage extends VBox {

    private final AppFacade appFacade = new AppFacade();

    private Integer currentManId = null;

    private final TextField nameField = new TextField();
    private final TextField countryField = new TextField();
    private final TextField cityField = new TextField();
    private final TextField timeZoneField = new TextField();
    private final TextField tagsField = new TextField();
    private final TextArea descriptionArea = new TextArea();

    private final Label statusLabel = new Label();

    public ManEditorPage() {
        getStyleClass().add("page");
        setSpacing(18);
        setPadding(new Insets(24));
        setFillWidth(true);

        HBox header = createHeader();
        VBox content = createContent();

        VBox.setVgrow(content, Priority.ALWAYS);

        getChildren().addAll(header, content);

        resetForNewMan();
    }

    private HBox createHeader() {
        Label title = new Label(I18n.get("man.editor.title"));
        title.getStyleClass().add("page-title");

        Label subtitle = new Label(I18n.get("man.editor.subtitle"));
        subtitle.getStyleClass().add("page-subtitle");

        VBox titleBox = new VBox(4, title, subtitle);

        Button newButton = new Button(I18n.get("man.editor.new"));
        newButton.getStyleClass().add("ghost-button");
        newButton.setOnAction(e -> resetForNewMan());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox header = new HBox(16, titleBox, spacer, newButton);
        header.setAlignment(Pos.CENTER_LEFT);

        return header;
    }

    private VBox createContent() {
        VBox wrapper = new VBox(16);

        VBox formCard = createFormCard();
        VBox actionsCard = createActionsCard();

        wrapper.getChildren().addAll(formCard, actionsCard);
        return wrapper;
    }

    private VBox createFormCard() {
        VBox card = new VBox(14);
        card.getStyleClass().add("card");
        card.setPadding(new Insets(16));

        Label title = new Label(I18n.get("man.editor.form"));
        title.getStyleClass().add("card-title");

        nameField.setPromptText(I18n.get("man.editor.name"));
        nameField.getStyleClass().add("search-field");

        countryField.setPromptText(I18n.get("man.editor.country"));
        countryField.getStyleClass().add("search-field");

        cityField.setPromptText(I18n.get("man.editor.city"));
        cityField.getStyleClass().add("search-field");

        timeZoneField.setPromptText(I18n.get("man.editor.timezone"));
        timeZoneField.getStyleClass().add("search-field");

        tagsField.setPromptText(I18n.get("man.editor.tags"));
        tagsField.getStyleClass().add("search-field");

        descriptionArea.setPromptText(I18n.get("man.editor.description"));
        descriptionArea.getStyleClass().add("editor-area");
        descriptionArea.setWrapText(true);
        descriptionArea.setPrefRowCount(6);

        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(12);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.ALWAYS);
        col1.setFillWidth(true);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        col2.setFillWidth(true);

        grid.getColumnConstraints().addAll(col1, col2);

        grid.add(createLabeledField(I18n.get("man.editor.name.required"), nameField), 0, 0, 2, 1);
        grid.add(createLabeledField(I18n.get("man.editor.country"), countryField), 0, 1);
        grid.add(createLabeledField(I18n.get("man.editor.city"), cityField), 1, 1);
        grid.add(createLabeledField(I18n.get("man.editor.timezone"), timeZoneField), 0, 2);
        grid.add(createLabeledField(I18n.get("man.editor.tags"), tagsField), 1, 2);

        VBox descriptionBox = createLabeledArea(I18n.get("man.editor.description"), descriptionArea);

        statusLabel.getStyleClass().add("editor-status");
        statusLabel.setVisible(false);
        statusLabel.setManaged(false);

        card.getChildren().addAll(title, grid, descriptionBox, statusLabel);
        return card;
    }

    private VBox createActionsCard() {
        VBox card = new VBox(12);
        card.getStyleClass().add("card");
        card.setPadding(new Insets(16));

        Label title = new Label(I18n.get("man.editor.actions"));
        title.getStyleClass().add("card-title");

        Button saveButton = new Button(I18n.get("man.editor.save"));
        saveButton.getStyleClass().add("primary-button");
        saveButton.setOnAction(e -> saveMan());

        Button deleteButton = new Button(I18n.get("man.editor.delete"));
        deleteButton.getStyleClass().add("ghost-button");
        deleteButton.setOnAction(e -> deleteMan());

        HBox actions = new HBox(10, deleteButton, saveButton);
        actions.setAlignment(Pos.CENTER_RIGHT);

        card.getChildren().addAll(title, actions);
        return card;
    }

    private VBox createLabeledField(String labelText, TextField field) {
        Label label = new Label(labelText);
        label.getStyleClass().add("card-title");
        label.setStyle("-fx-font-size: 12px; -fx-font-weight: normal;");

        VBox box = new VBox(6, label, field);
        VBox.setVgrow(field, Priority.NEVER);
        return box;
    }

    private VBox createLabeledArea(String labelText, TextArea area) {
        Label label = new Label(labelText);
        label.getStyleClass().add("card-title");
        label.setStyle("-fx-font-size: 12px; -fx-font-weight: normal;");

        VBox box = new VBox(6, label, area);
        VBox.setVgrow(area, Priority.ALWAYS);
        return box;
    }

    public void setMan(Man man) {
        if (man == null) {
            resetForNewMan();
            return;
        }

        currentManId = man.getId();

        nameField.setText(safe(man.getName()));
        countryField.setText(safe(man.getCountry()));
        cityField.setText(safe(man.getCity()));
        timeZoneField.setText(safe(man.getTimeZone()));
        descriptionArea.setText(safe(man.getDescription()));
        tagsField.setText(buildTagsString(man.getTags()));

        hideStatus();
    }

    private void resetForNewMan() {
        currentManId = null;

        nameField.clear();
        countryField.setText("--");
        cityField.setText("--");
        timeZoneField.setText("--");
        tagsField.clear();
        descriptionArea.setText("--");

        setStatus(I18n.get("man.editor.newDraft"), false);
    }

    private void saveMan() {
        String name = safeInput(nameField.getText());
        String country = safeInput(countryField.getText(), "--");
        String city = safeInput(cityField.getText(), "--");
        String timeZone = safeInput(timeZoneField.getText(), "--");
        String description = safeInput(descriptionArea.getText(), "--");
        Set<String> tags = parseTags(tagsField.getText());

        if (name.isBlank() || name.equals("--")) {
            setStatus(I18n.get("man.editor.nameRequired"), false);
            return;
        }

        if (currentManId == null) {
            Man created = appFacade.saveMan(name, country, city, description, timeZone, tags);
            currentManId = created.getId();
            setStatus(I18n.get("man.editor.saved"), true);
            return;
        }

        appFacade.updateMan(currentManId, name, country, city, description, timeZone, tags);
        setStatus(I18n.get("man.editor.updated"), true);
    }

    private void deleteMan() {
        if (currentManId == null) {
            setStatus(I18n.get("man.editor.nothingToDelete"), false);
            return;
        }

        appFacade.deleteMan(currentManId);
        resetForNewMan();
        setStatus(I18n.get("man.editor.deleted"), true);
    }

    private Set<String> parseTags(String raw) {
        if (raw == null || raw.isBlank()) {
            return new LinkedHashSet<>();
        }

        return Arrays.stream(raw.split(","))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private String buildTagsString(Set<String> tags) {
        if (tags == null || tags.isEmpty()) {
            return "";
        }
        return String.join(", ", tags);
    }

    private String safe(String value) {
        return value == null || value.isBlank() ? "--" : value;
    }

    private String safeInput(String value) {
        return value == null ? "" : value.trim();
    }

    private String safeInput(String value, String fallback) {
        String normalized = value == null ? "" : value.trim();
        return normalized.isBlank() ? fallback : normalized;
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

    private void hideStatus() {
        statusLabel.setVisible(false);
        statusLabel.setManaged(false);
        statusLabel.getStyleClass().remove("saved");
    }
}