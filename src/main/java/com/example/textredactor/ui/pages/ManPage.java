package com.example.textredactor.ui.pages;

import com.example.textredactor.engine.AppFacade;
import com.example.textredactor.engine.model.Man;
import com.example.textredactor.ui.i18n.I18n;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ManPage extends VBox {

    private final AppFacade appFacade = new AppFacade();

    private final VBox listBox = new VBox(8);
    private final TextField searchField = new TextField();

    private VBox selectedItem;
    private Integer currentManId = null;

    public ManPage() {
        getStyleClass().add("page");
        setSpacing(18);
        setPadding(new Insets(24));
        setFillWidth(true);

        HBox header = createHeader();
        VBox content = createContent();

        VBox.setVgrow(content, Priority.ALWAYS);

        getChildren().addAll(header, content);

        loadMansFromFacade();
    }

    private HBox createHeader() {
        Label title = new Label(I18n.get("mans.title"));
        title.getStyleClass().add("page-title");

        Label subtitle = new Label(I18n.get("mans.subtitle"));
        subtitle.getStyleClass().add("page-subtitle");

        VBox titleBox = new VBox(4, title, subtitle);

        searchField.setPromptText(I18n.get("mans.search"));
        searchField.getStyleClass().add("search-field");
        searchField.setMaxWidth(260);

        searchField.textProperty().addListener((obs, oldValue, newValue) -> {
            filterMans(newValue);
        });

        Button newButton = new Button(I18n.get("mans.new"));
        newButton.getStyleClass().add("primary-button");
        newButton.setOnAction(e -> {
            // TODO: потом открыть ManEditorPage
            // pageManager.showPage(...)
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox header = new HBox(16, titleBox, spacer, searchField, newButton);
        header.setAlignment(Pos.CENTER_LEFT);

        return header;
    }

    private VBox createContent() {
        VBox panel = new VBox(12);
        panel.getStyleClass().add("card");
        panel.setPadding(new Insets(16));
        VBox.setVgrow(panel, Priority.ALWAYS);

        Label listTitle = new Label(I18n.get("mans.list.title"));
        listTitle.getStyleClass().add("card-title");

        ScrollPaneWrapper wrapper = new ScrollPaneWrapper(listBox);
        VBox.setVgrow(wrapper, Priority.ALWAYS);

        panel.getChildren().addAll(listTitle, wrapper);
        return panel;
    }

    private void loadMansFromFacade() {
        filterMans(searchField.getText());
    }

    private void filterMans(String query) {
        List<Man> allMans = getAllMansSafe();

        if (query == null || query.isBlank()) {
            renderMans(allMans);
            return;
        }

        String normalized = query.trim().toLowerCase();

        List<Man> filtered = allMans.stream()
                .filter(man ->
                        safe(man.getName()).toLowerCase().contains(normalized)
                                || safe(man.getCountry()).toLowerCase().contains(normalized)
                                || safe(man.getCity()).toLowerCase().contains(normalized)
                                || safe(man.getDescription()).toLowerCase().contains(normalized)
                                || buildTags(man).toLowerCase().contains(normalized)
                )
                .toList();

        renderMans(filtered);
    }

    private void renderMans(List<Man> mans) {
        listBox.getChildren().clear();

        if (mans.isEmpty()) {
            Label emptyLabel = new Label(I18n.get("mans.list.notFound"));
            emptyLabel.getStyleClass().add("empty-state-label");
            listBox.getChildren().add(emptyLabel);
            return;
        }

        for (Man man : mans) {
            listBox.getChildren().add(createManItem(man));
        }
    }

    private VBox createManItem(Man man) {
        Label nameLabel = new Label(safeOrDefault(man.getName(), I18n.get("mans.noName")));
        nameLabel.getStyleClass().add("man-item-name");

        Label metaLabel = new Label(buildMeta(man));
        metaLabel.getStyleClass().add("man-item-meta");

        Label descriptionLabel = new Label(buildDescriptionPreview(man));
        descriptionLabel.getStyleClass().add("man-item-description");
        descriptionLabel.setWrapText(true);

        FlowPane tagsPane = new FlowPane();
        tagsPane.setHgap(6);
        tagsPane.setVgap(6);
        tagsPane.getStyleClass().add("man-tags-pane");

        Set<String> tags = getTagsSafe(man);
        if (!tags.isEmpty()) {
            for (String tag : tags) {
                Label tagLabel = new Label(tag);
                tagLabel.getStyleClass().add("man-tag");
                tagsPane.getChildren().add(tagLabel);
            }
        }

        VBox infoBox = new VBox(5, nameLabel, metaLabel, descriptionLabel, tagsPane);

        Label timeZoneLabel = new Label(buildTimeZone(man));
        timeZoneLabel.getStyleClass().add("man-timezone-label");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button openButton = new Button(I18n.get("mans.open"));
        openButton.getStyleClass().add("ghost-button");
        openButton.setOnAction(e -> {
            currentManId = man.getId();
            // TODO: потом открыть ManDetailsPage
        });

        HBox topRow = new HBox(10, timeZoneLabel, spacer, openButton);
        topRow.setAlignment(Pos.CENTER_LEFT);

        VBox item = new VBox(8, topRow, infoBox);
        item.getStyleClass().add("man-item");
        item.setPadding(new Insets(12, 14, 12, 14));

        if (currentManId != null && currentManId == man.getId()) {
            item.getStyleClass().add("active");
            selectedItem = item;
        }

        item.setOnMouseClicked(e -> {
            selectItem(item);
            currentManId = man.getId();
        });

        return item;
    }

    private void selectItem(VBox item) {
        if (selectedItem != null) {
            selectedItem.getStyleClass().remove("active");
        }

        selectedItem = item;

        if (!selectedItem.getStyleClass().contains("active")) {
            selectedItem.getStyleClass().add("active");
        }
    }

    private List<Man> getAllMansSafe() {
        try {
            return appFacade.getAllMans();
        } catch (Exception e) {
            return List.of();
        }
    }

    private String buildMeta(Man man) {
        String country = safe(man.getCountry());
        String city = safe(man.getCity());

        if (!country.isBlank() && !city.isBlank()) {
            return country + " • " + city;
        }

        if (!country.isBlank()) {
            return country;
        }

        if (!city.isBlank()) {
            return city;
        }

        return I18n.get("mans.meta.empty");
    }

    private String buildDescriptionPreview(Man man) {
        String description = safe(man.getDescription());

        if (description.isBlank()) {
            return I18n.get("mans.description.empty");
        }

        String normalized = description.replace("\n", " ").replace("\r", " ").trim();
        return normalized.length() > 90
                ? normalized.substring(0, 90) + "..."
                : normalized;
    }

    private String buildTimeZone(Man man) {
        String zone = safe(getTimeZoneSafe(man));
        if (zone.isBlank()) {
            return I18n.get("mans.timezone.unknown");
        }
        return I18n.get("mans.timezone.prefix") + zone;
    }

    private String buildTags(Man man) {
        return getTagsSafe(man).stream().collect(Collectors.joining(" "));
    }

    private Set<String> getTagsSafe(Man man) {
        try {
            Set<String> tags = man.getTags();
            return tags == null ? Set.of() : tags;
        } catch (Exception e) {
            return Set.of();
        }
    }

    private String getTimeZoneSafe(Man man) {
        try {
            return man.getTimeZone();
        } catch (Exception e) {
            return "";
        }
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }

    private String safeOrDefault(String value, String fallback) {
        return (value == null || value.isBlank()) ? fallback : value;
    }
}