package com.example.textredactor.pages;

import com.example.textredactor.HelloApplication;
import com.example.textredactor.engine.model.Man;
import com.example.textredactor.engine.service.ManRecordService;
import com.example.textredactor.engine.service.impl.ManRecordServiceImpl;
import com.example.textredactor.ui.InlineInfoItem;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Region;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;

public class ManViewPage extends VBox {

    private final ManRecordService manRecordService = new ManRecordServiceImpl();

    private final InlineInfoItem idItem = new InlineInfoItem("ID", "-");
    private final InlineInfoItem nameItem = new InlineInfoItem("Name", "-");
    private final InlineInfoItem countryItem = new InlineInfoItem("Country", "-");
    private final InlineInfoItem cityItem = new InlineInfoItem("City", "-");
    private final InlineInfoItem timeItem = new InlineInfoItem("Time", "--:--");

    private final Label descriptionValue = new Label("-");
    private final TextArea noteArea = new TextArea();
    private final VBox recordsBox = new VBox(6);

    private Man currentMan;

    public ManViewPage() {
        setSpacing(10);
        setPadding(new Insets(12));
        setFillWidth(true);
        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        HBox topBar = createTopBar();
        VBox infoSection = createInfoSection();
        VBox inputSection = createInputSection();
        VBox recordsSection = createRecordsSection();

        VBox.setVgrow(recordsSection, Priority.ALWAYS);

        getChildren().addAll(topBar, infoSection, inputSection, recordsSection);
    }

    public void setMan(Man man) {
        this.currentMan = man;

        idItem.setValue(String.valueOf(man.getId()));
        nameItem.setValue(safe(man.getName()));
        countryItem.setValue(safe(man.getCountry()));
        cityItem.setValue(safe(man.getCity()));
        timeItem.setValue("--:--");
        descriptionValue.setText(safe(man.getDescription()));
        recordsBox.getChildren().clear();
        currentMan.getManRecordList().clear();
        manRecordService.getRecordList(currentMan);
        man.getManRecordList().stream().forEach(record -> {
            createRecordItem(record.getText());
        });
    }

    private HBox createTopBar() {
        Button backBtn = new Button("← Back");
        backBtn.getStyleClass().add("small-btn");
        backBtn.setOnAction(e -> HelloApplication.showCard("ManPage"));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox topBar = new HBox(8, backBtn, spacer);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.getStyleClass().add("top-bar");

        return topBar;
    }

    private VBox createInfoSection() {
        VBox box = new VBox(6);
        box.getStyleClass().add("man-info-panel");
        box.setPadding(new Insets(8, 10, 8, 10));

        FlowPane inlineRow = new FlowPane();
        inlineRow.setHgap(14);
        inlineRow.setVgap(6);

        inlineRow.getChildren().addAll(
                idItem,
                nameItem,
                countryItem,
                cityItem,
                timeItem
        );

        Label descTitle = new Label("Description:");
        descTitle.getStyleClass().add("man-inline-desc-title");

        descriptionValue.getStyleClass().add("man-inline-desc-value");
        descriptionValue.setWrapText(true);

        VBox descBox = new VBox(3, descTitle, descriptionValue);
        descBox.getStyleClass().add("man-inline-desc-box");

        box.getChildren().addAll(inlineRow, descBox);
        return box;
    }

    private VBox createInputSection() {
        VBox box = new VBox(6);
        box.getStyleClass().add("content-card");
        box.setPadding(new Insets(10));

        Label title = new Label("Today note");
        title.getStyleClass().add("mini-section-title");

        noteArea.setPromptText("Write what happened today...");
        noteArea.setWrapText(true);
        noteArea.setPrefHeight(90);
        noteArea.setMinHeight(90);
        noteArea.getStyleClass().add("editor-text");

        Button addBtn = new Button("Add record");
        addBtn.getStyleClass().add("small-btn");

        addBtn.setOnAction(e -> {
            manRecordService.addRecord(noteArea.getText(), currentMan.getId(), currentMan);
            createRecordItem(noteArea.getText());
            noteArea.clear();
        });

        HBox btnBar = new HBox();
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        btnBar.getChildren().addAll(spacer, addBtn);

        box.getChildren().addAll(title, noteArea, btnBar);
        return box;
    }

    private VBox createRecordsSection() {
        VBox box = new VBox(6);
        box.getStyleClass().add("content-card");
        box.setPadding(new Insets(10));

        Label title = new Label("Records");
        title.getStyleClass().add("mini-section-title");

        ScrollPane scrollPane = new ScrollPane(recordsBox);
        scrollPane.getStyleClass().add("scroll-pane");
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        VBox.setVgrow(scrollPane, Priority.ALWAYS);

//        recordsBox.getChildren().addAll(
//                createRecordItem("Talked about delivery delays."),
//                createRecordItem("Promised to send details tomorrow."),
//                createRecordItem("Interested in cooperation.")
//        );

        box.getChildren().addAll(title, scrollPane);
        VBox.setVgrow(box, Priority.ALWAYS);

        return box;
    }

    public void createRecordItem(String text) {
        Label label = new Label(text);
        label.setWrapText(true);
        label.getStyleClass().add("record-text");
        label.setMaxWidth(Double.MAX_VALUE);

        Button deleteBtn = new Button("✕");
        deleteBtn.getStyleClass().add("record-delete-btn");
        deleteBtn.setVisible(false);
        deleteBtn.setManaged(false);
        deleteBtn.setFocusTraversable(false);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox item = new HBox(8, label, spacer, deleteBtn);
        item.getStyleClass().add("record-item");
        item.setAlignment(Pos.CENTER_LEFT);
        item.setPadding(new Insets(6, 6, 6, 6));
        item.setFillHeight(true);

        HBox.setHgrow(label, Priority.ALWAYS);

        item.setOnMouseEntered(e -> {
            deleteBtn.setVisible(true);
            deleteBtn.setManaged(true);
        });

        item.setOnMouseExited(e -> {
            deleteBtn.setVisible(false);
            deleteBtn.setManaged(false);
        });

        deleteBtn.setOnAction(e -> {
            recordsBox.getChildren().remove(item);
        });

        recordsBox.getChildren().add(0, item);
    }

    private String safe(String value) {
        return value == null || value.isBlank() ? "-" : value;
    }
}