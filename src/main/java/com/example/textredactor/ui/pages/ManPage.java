package com.example.textredactor.ui.pages;

import com.example.textredactor.engine.AppFacade;
import com.example.textredactor.engine.model.Man;
import com.example.textredactor.ui.i18n.I18n;
import com.example.textredactor.ui.layout.MainLayout;
import javafx.geometry.Insets;
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

        searchField.textProperty().addListener((obs, oldValue, newValue) -> filterMans(newValue));

        javafx.scene.control.Button newButton = new javafx.scene.control.Button(I18n.get("mans.new"));
        newButton.getStyleClass().add("primary-button");
        newButton.setOnAction(e -> {
            MainLayout.getManEditorPage().setMan(null);
            MainLayout.showPage("manEditor");
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox header = new HBox(16, titleBox, spacer, searchField, newButton);

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

    public void reload() {
        loadMansFromFacade();
    }

    private void loadMansFromFacade() {
        filterMans(searchField.getText());
    }

    private void filterMans(String query) {
        List<Man> allMans = appFacade.getAllMans();

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

        Set<String> tags = man.getTags() == null ? Set.of() : man.getTags();
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

        VBox item = new VBox(8, timeZoneLabel, infoBox);
        item.getStyleClass().add("man-item");
        item.setPadding(new Insets(12, 14, 12, 14));

        if (currentManId != null && currentManId == man.getId()) {
            item.getStyleClass().add("active");
            selectedItem = item;
        }

        item.setOnMouseClicked(e -> {
            selectItem(item);
            currentManId = man.getId();
            MainLayout.getManDetailsPage().setMan(man);
            MainLayout.showPage("manDetails");
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

    private String buildMeta(Man man) {
        String country = safe(man.getCountry());
        String city = safe(man.getCity());

        if (!country.isBlank() && !city.isBlank() && !country.equals("--") && !city.equals("--")) {
            return country + " • " + city;
        }

        if (!country.isBlank() && !country.equals("--")) {
            return country;
        }

        if (!city.isBlank() && !city.equals("--")) {
            return city;
        }

        return I18n.get("mans.meta.empty");
    }

    private String buildDescriptionPreview(Man man) {
        String description = safe(man.getDescription());

        if (description.isBlank() || description.equals("--")) {
            return I18n.get("mans.description.empty");
        }

        String normalized = description.replace("\n", " ").replace("\r", " ").trim();
        return normalized.length() > 90 ? normalized.substring(0, 90) + "..." : normalized;
    }

    private String buildTimeZone(Man man) {
        String zone = safe(man.getTimeZone());
        if (zone.isBlank() || zone.equals("--")) {
            return I18n.get("mans.timezone.unknown");
        }
        return I18n.get("mans.timezone.prefix") + zone;
    }

    private String buildTags(Man man) {
        Set<String> tags = man.getTags() == null ? Set.of() : man.getTags();
        return tags.stream().collect(Collectors.joining(" "));
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }

    private String safeOrDefault(String value, String fallback) {
        return (value == null || value.isBlank() || value.equals("--")) ? fallback : value;
    }
}