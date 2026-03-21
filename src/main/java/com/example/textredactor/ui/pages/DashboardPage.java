package com.example.textredactor.ui.pages;

import com.example.textredactor.ui.i18n.I18n;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

public class DashboardPage extends VBox {

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

        TextField searchField = new TextField();
        searchField.setPromptText(I18n.get("dashboard.search"));
        searchField.getStyleClass().add("search-field");
        searchField.setMaxWidth(280);

        Button newButton = new Button(I18n.get("dashboard.new"));
        newButton.getStyleClass().add("primary-button");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox header = new HBox(16, titleBox, spacer, searchField, newButton);
        header.setAlignment(Pos.CENTER_LEFT);

        return header;
    }

    private HBox createContent() {
        VBox mainColumn = createMainColumn();
        VBox sideColumn = createSideColumn();

        HBox.setHgrow(mainColumn, Priority.ALWAYS);
        sideColumn.setPrefWidth(280);

        HBox content = new HBox(16, mainColumn, sideColumn);
        HBox.setHgrow(mainColumn, Priority.ALWAYS);

        return content;
    }

    private VBox createMainColumn() {
        VBox column = new VBox(16);
        VBox.setVgrow(column, Priority.ALWAYS);

        VBox recentCard = createCard(I18n.get("dashboard.recent"));
        recentCard.getChildren().addAll(
                createSimpleRow(I18n.get("dashboard.activity.opened")),
                createSimpleRow(I18n.get("dashboard.activity.saved")),
                createSimpleRow(I18n.get("dashboard.activity.man")),
                createSimpleRow(I18n.get("dashboard.activity.updated")),
                createSimpleRow(I18n.get("dashboard.activity.record"))
        );

        VBox quickCard = createCard(I18n.get("dashboard.quick"));
        HBox actions = new HBox(10,
                createGhostButton(I18n.get("dashboard.quick.newText")),
                createGhostButton(I18n.get("dashboard.quick.library")),
                createGhostButton(I18n.get("dashboard.quick.addMan"))
        );
        quickCard.getChildren().add(actions);

        VBox.setVgrow(recentCard, Priority.ALWAYS);

        column.getChildren().addAll(recentCard, quickCard);
        return column;
    }

    private VBox createSideColumn() {
        VBox column = new VBox(16);

        VBox statsCard = createCard(I18n.get("dashboard.overview"));
        statsCard.getChildren().addAll(
                createStatRow(I18n.get("dashboard.stats.texts"), "24"),
                createStatRow(I18n.get("dashboard.stats.mans"), "8"),
                createStatRow(I18n.get("dashboard.stats.records"), "51")
        );

        VBox notesCard = createCard(I18n.get("dashboard.notes"));
        notesCard.getChildren().addAll(
                createSimpleRow(I18n.get("dashboard.note.one")),
                createSimpleRow(I18n.get("dashboard.note.two")),
                createSimpleRow(I18n.get("dashboard.note.three"))
        );

        column.getChildren().addAll(statsCard, notesCard);
        return column;
    }

    private VBox createCard(String titleText) {
        Label title = new Label(titleText);
        title.getStyleClass().add("card-title");

        VBox card = new VBox(12, title);
        card.getStyleClass().add("card");
        card.setPadding(new Insets(16));

        return card;
    }

    private HBox createSimpleRow(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("row-text");

        HBox row = new HBox(label);
        row.getStyleClass().add("simple-row");
        row.setPadding(new Insets(10, 12, 10, 12));

        return row;
    }

    private HBox createStatRow(String title, String value) {
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("stat-title");

        Label valueLabel = new Label(value);
        valueLabel.getStyleClass().add("stat-value");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox row = new HBox(titleLabel, spacer, valueLabel);
        row.getStyleClass().add("simple-row");
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(10, 12, 10, 12));

        return row;
    }

    private Button createGhostButton(String text) {
        Button button = new Button(text);
        button.getStyleClass().add("ghost-button");
        return button;
    }
}