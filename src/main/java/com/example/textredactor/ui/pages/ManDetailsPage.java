package com.example.textredactor.ui.pages;

import com.example.textredactor.engine.model.Man;
import com.example.textredactor.ui.i18n.I18n;
import com.example.textredactor.ui.layout.MainLayout;
import com.example.textredactor.engine.AppFacade;
import com.example.textredactor.engine.model.ManRecord;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;

import java.util.Set;

public class ManDetailsPage extends VBox {

    private final AppFacade appFacade = new AppFacade();

    private final Label idValue = new Label("--");
    private final Label nameValue = new Label("--");
    private final Label countryValue = new Label("--");
    private final Label cityValue = new Label("--");
    private final Label timeZoneValue = new Label("--");
    private final Label descriptionValue = new Label("--");

    private final FlowPane tagsPane = new FlowPane();
    private final TextArea noteArea = new TextArea();
    private final VBox notesBox = new VBox(8);
    private final Label statusLabel = new Label();

    private Man currentMan;

    public ManDetailsPage() {
        getStyleClass().add("page");
        setSpacing(18);
        setPadding(new Insets(24));
        setFillWidth(true);

        HBox header = createHeader();
        VBox infoCard = createInfoCard();
        SplitPane notesSection = createNotesSection();

        VBox.setVgrow(notesSection, Priority.ALWAYS);

        getChildren().addAll(header, infoCard, notesSection);
    }

    private HBox createHeader() {
        Button backButton = new Button(I18n.get("man.details.back"));
        backButton.getStyleClass().add("ghost-button");
        backButton.setOnAction(e -> MainLayout.showPage("mans"));

        Button editButton = new Button(I18n.get("man.details.edit"));
        editButton.getStyleClass().add("ghost-button");
        editButton.setOnAction(e -> {
            if (currentMan == null) {
                return;
            }
            MainLayout.getManEditorPage().setMan(currentMan);
            MainLayout.showPage("manEditor");
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox header = new HBox(10, backButton, spacer, editButton);
        header.setAlignment(Pos.CENTER_LEFT);

        return header;
    }

    private VBox createInfoCard() {
        VBox card = new VBox(10);
        card.getStyleClass().add("card");
        card.setPadding(new Insets(14));

        Label title = new Label(I18n.get("man.details.info"));
        title.getStyleClass().add("card-title");

        GridPane grid = new GridPane();
        grid.setHgap(14);
        grid.setVgap(8);

        ColumnConstraints c1 = new ColumnConstraints();
        c1.setHgrow(Priority.ALWAYS);
        c1.setFillWidth(true);

        ColumnConstraints c2 = new ColumnConstraints();
        c2.setHgrow(Priority.ALWAYS);
        c2.setFillWidth(true);

        grid.getColumnConstraints().addAll(c1, c2);

        grid.add(createInfoRow(I18n.get("man.details.id"), idValue), 0, 0);
        grid.add(createInfoRow(I18n.get("man.details.name"), nameValue), 1, 0);
        grid.add(createInfoRow(I18n.get("man.details.country"), countryValue), 0, 1);
        grid.add(createInfoRow(I18n.get("man.details.city"), cityValue), 1, 1);
        grid.add(createInfoRow(I18n.get("man.details.timezone"), timeZoneValue), 0, 2, 2, 1);

        VBox descriptionBox = new VBox(4);
        Label descriptionTitle = new Label(I18n.get("man.details.description"));
        descriptionTitle.getStyleClass().add("card-title");
        descriptionTitle.setStyle("-fx-font-size: 12px; -fx-font-weight: normal;");
        descriptionValue.getStyleClass().add("man-detail-description");
        descriptionValue.setWrapText(true);
        descriptionBox.getChildren().addAll(descriptionTitle, descriptionValue);

        VBox tagsBox = new VBox(4);
        Label tagsTitle = new Label(I18n.get("man.details.tags"));
        tagsTitle.getStyleClass().add("card-title");
        tagsTitle.setStyle("-fx-font-size: 12px; -fx-font-weight: normal;");
        tagsPane.setHgap(6);
        tagsPane.setVgap(6);
        tagsBox.getChildren().addAll(tagsTitle, tagsPane);

        card.getChildren().addAll(title, grid, descriptionBox, tagsBox);
        return card;
    }

    private SplitPane createNotesSection() {
        VBox inputCard = new VBox(12);
        inputCard.getStyleClass().add("card");
        inputCard.setPadding(new Insets(16));
        inputCard.setMinWidth(0);
        inputCard.setPrefWidth(0);
        inputCard.setMaxWidth(Double.MAX_VALUE);

        notesBox.setFillWidth(true);
        notesBox.setMaxWidth(Double.MAX_VALUE);

        Label inputTitle = new Label(I18n.get("man.details.notes.input"));
        inputTitle.getStyleClass().add("card-title");

        noteArea.setPromptText(I18n.get("man.details.notePrompt"));
        noteArea.getStyleClass().add("editor-area");
        noteArea.getStyleClass().add("notes-input-area");
        noteArea.setWrapText(true);
        VBox.setVgrow(noteArea, Priority.ALWAYS);

        statusLabel.getStyleClass().add("editor-status");
        statusLabel.setVisible(false);
        statusLabel.setManaged(false);

        Button addNoteButton = new Button(I18n.get("man.details.addNote"));
        addNoteButton.getStyleClass().add("primary-button");
        addNoteButton.setOnAction(e -> {
            String text = noteArea.getText() == null ? "" : noteArea.getText().trim();
            if (text.isEmpty()) {
                setStatus(I18n.get("man.details.noteEmpty"), false);
                return;
            }

            if (currentMan == null) {
                return;
            }

            appFacade.addRecord(currentMan.getId(), text);
            noteArea.clear();
            setStatus(I18n.get("man.details.noteAdded"), true);
            loadRecords();
        });

        HBox inputActions = new HBox();
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        inputActions.getChildren().addAll(spacer, addNoteButton);

        inputCard.getChildren().addAll(inputTitle, noteArea, inputActions, statusLabel);
        VBox.setVgrow(inputCard, Priority.ALWAYS);

        VBox listCard = new VBox(12);
        listCard.getStyleClass().add("card");
        listCard.setPadding(new Insets(16));
        listCard.setMinWidth(0);
        listCard.setPrefWidth(0);
        listCard.setMaxWidth(Double.MAX_VALUE);

        Label listTitle = new Label(I18n.get("man.details.notes.list"));
        listTitle.getStyleClass().add("card-title");

        ScrollPaneWrapper wrapper = new ScrollPaneWrapper(notesBox);
        VBox.setVgrow(wrapper, Priority.ALWAYS);

        listCard.getChildren().addAll(listTitle, wrapper);
        VBox.setVgrow(listCard, Priority.ALWAYS);

        SplitPane splitPane = new SplitPane(inputCard, listCard);
        splitPane.getStyleClass().add("texts-split-pane");
        splitPane.setDividerPositions(0.42);

        return splitPane;
    }

    private HBox createInfoRow(String title, Label valueLabel) {
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("man-detail-label");

        valueLabel.getStyleClass().add("man-detail-value");

        HBox row = new HBox(8, titleLabel, valueLabel);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    private VBox createNoteItem(ManRecord record) {
        Label noteText = new Label(record.getText());
        noteText.getStyleClass().add("record-text");
        noteText.setWrapText(true);

        noteText.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(noteText, Priority.NEVER);

        Button deleteButton = new Button("✕");
        deleteButton.getStyleClass().add("record-delete-btn");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox topRow = new HBox(8, spacer, deleteButton);
        topRow.setAlignment(Pos.CENTER_LEFT);

        VBox item = new VBox(6, topRow, noteText);
        item.getStyleClass().add("record-item");
        item.setPadding(new Insets(8));
        item.setFillWidth(true);
        item.setMaxWidth(Double.MAX_VALUE);

        deleteButton.setOnAction(e -> {
            appFacade.deleteRecord(record.getId());
            loadRecords();
        });

        return item;
    }

    public void setMan(Man man) {
        if (man == null) {
            return;
        }

        currentMan = man;

        idValue.setText(String.valueOf(man.getId()));
        nameValue.setText(safe(man.getName()));
        countryValue.setText(safe(man.getCountry()));
        cityValue.setText(safe(man.getCity()));
        timeZoneValue.setText(safe(man.getTimeZone()));
        descriptionValue.setText(safe(man.getDescription()));

        tagsPane.getChildren().clear();
        Set<String> tags = man.getTags();

        if (tags != null && !tags.isEmpty()) {
            for (String tag : tags) {
                Label tagLabel = new Label(tag);
                tagLabel.getStyleClass().add("man-tag");
                tagsPane.getChildren().add(tagLabel);
            }
        }

        hideStatus();
        loadRecords();
    }

    private String safe(String value) {
        return value == null || value.isBlank() ? "--" : value;
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

    private void loadRecords() {
        notesBox.getChildren().clear();

        if (currentMan == null) {
            return;
        }

        for (ManRecord record : appFacade.getRecordsByManId(currentMan.getId())) {
            notesBox.getChildren().add(createNoteItem(record));
        }
    }
}