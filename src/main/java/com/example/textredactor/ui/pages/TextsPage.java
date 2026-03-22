package com.example.textredactor.ui.pages;

import com.example.textredactor.engine.AppFacade;
import com.example.textredactor.engine.mapper.TextFormatResult;
import com.example.textredactor.engine.model.Text;
import com.example.textredactor.ui.i18n.I18n;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.*;

import java.util.List;

public class TextsPage extends VBox {

    private final AppFacade appFacade = new AppFacade();

    private final VBox listBox = new VBox(6);

    private final TextField titleField = new TextField();
    private final Label editorStatus = new Label();
    private final TextArea editorArea = new TextArea();
    private final Label emptyStateLabel = new Label(I18n.get("texts.editor.emptyState"));

    private VBox selectedItem;
    private boolean loadingText = false;
    private boolean creatingNew = false;
    private Integer currentTextId = null;

    public TextsPage() {
        getStyleClass().add("page");
        setSpacing(18);
        setPadding(new Insets(24));
        setFillWidth(true);

        HBox header = createHeader();
        SplitPane content = createContent();

        VBox.setVgrow(content, Priority.ALWAYS);

        initEditorListeners();
        showEmptyState();
        loadTextsFromFacade();

        getChildren().addAll(header, content);
    }

    private HBox createHeader() {
        Label title = new Label(I18n.get("texts.title"));
        title.getStyleClass().add("page-title");

        Label subtitle = new Label(I18n.get("texts.subtitle"));
        subtitle.getStyleClass().add("page-subtitle");

        VBox titleBox = new VBox(4, title, subtitle);

        TextField searchField = new TextField();
        searchField.setPromptText(I18n.get("texts.search"));
        searchField.getStyleClass().add("search-field");
        searchField.setMaxWidth(260);

        Button newButton = new Button(I18n.get("texts.new"));
        newButton.getStyleClass().add("primary-button");
        newButton.setOnAction(e -> resetEditorForNewText());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox header = new HBox(16, titleBox, spacer, searchField, newButton);
        header.setAlignment(Pos.CENTER_LEFT);

        return header;
    }

    private SplitPane createContent() {
        VBox listPanel = createListPanel();
        VBox editorPanel = createEditorPanel();

        SplitPane splitPane = new SplitPane(listPanel, editorPanel);
        splitPane.getStyleClass().add("texts-split-pane");
        splitPane.setDividerPositions(0.32);

        return splitPane;
    }

    private VBox createListPanel() {
        VBox panel = new VBox(12);
        panel.getStyleClass().add("card");
        panel.setPadding(new Insets(16));

        Label title = new Label(I18n.get("texts.list.title"));
        title.getStyleClass().add("card-title");

        ScrollPaneWrapper wrapper = new ScrollPaneWrapper(listBox);
        VBox.setVgrow(wrapper, Priority.ALWAYS);

        panel.getChildren().addAll(title, wrapper);
        return panel;
    }

    private VBox createEditorPanel() {
        VBox panel = new VBox(12);
        panel.getStyleClass().add("card");
        panel.setPadding(new Insets(16));

        titleField.setPromptText(I18n.get("texts.editor.titlePrompt"));
        titleField.getStyleClass().add("search-field");

        editorStatus.getStyleClass().add("editor-status");
        hideStatus();

        VBox titleBlock = new VBox(4, titleField, editorStatus);

        StackPane editorContainer = new StackPane();
        VBox.setVgrow(editorContainer, Priority.ALWAYS);

        editorArea.getStyleClass().add("editor-area");
        editorArea.setWrapText(true);
        editorArea.setVisible(false);
        editorArea.setManaged(false);

        emptyStateLabel.getStyleClass().add("empty-state-label");

        editorContainer.getChildren().addAll(emptyStateLabel, editorArea);

        Button processButton = new Button(I18n.get("texts.editor.edit"));
        processButton.getStyleClass().add("ghost-button");

        Button copyButton = new Button(I18n.get("texts.editor.copy"));
        copyButton.getStyleClass().add("ghost-button");

        Button saveButton = new Button(I18n.get("texts.editor.save"));
        saveButton.getStyleClass().add("primary-button");

        processButton.setOnAction(e -> {
            if (!editorArea.isVisible()) {
                return;
            }

            TextFormatResult result = appFacade.processText(editorArea.getText());

            loadingText = true;
            editorArea.setText(result.text());
            loadingText = false;

            setStatusUnsavedWithCount(result.replacedCount());
        });

        copyButton.setOnAction(e -> {
            if (!editorArea.isVisible()) {
                return;
            }

            ClipboardContent content = new ClipboardContent();
            content.putString(editorArea.getText());
            Clipboard.getSystemClipboard().setContent(content);

            setStatusCopied();
        });

        saveButton.setOnAction(e -> {
            if (!editorArea.isVisible()) {
                return;
            }

            String title = titleField.getText() == null ? "" : titleField.getText().trim();
            String text = editorArea.getText() == null ? "" : editorArea.getText();

            if (title.isEmpty()) {
                editorStatus.setText(I18n.get("texts.editor.titleRequired"));
                editorStatus.getStyleClass().remove("saved");
                showStatus();
                return;
            }

            if (creatingNew || currentTextId == null) {
                Text created = appFacade.createText(title, text);
                currentTextId = created.getId();
                creatingNew = false;
                setStatusSaved();
                loadTextsFromFacade();
                return;
            }

            // updateText ещё не готов в engine
            setStatusPendingUpdate();
        });

        HBox leftActions = new HBox(8, processButton, copyButton);

        HBox actionBar = new HBox();
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        actionBar.getChildren().addAll(leftActions, spacer, saveButton);

        panel.getChildren().addAll(titleBlock, editorContainer, actionBar);

        return panel;
    }

    private void initEditorListeners() {
        titleField.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!loadingText && editorArea.isVisible()) {
                setStatusUnsaved();
            }
        });

        editorArea.textProperty().addListener((obs, oldText, newText) -> {
            if (!loadingText && editorArea.isVisible()) {
                setStatusUnsaved();
            }
        });
    }

    private void loadTextsFromFacade() {
        listBox.getChildren().clear();

        List<Text> texts = appFacade.getAllTexts();

        for (Text text : texts) {
            listBox.getChildren().add(createTextItem(text));
        }
    }

    private VBox createTextItem(Text text) {
        Label titleLabel = new Label(text.getTitle());
        titleLabel.getStyleClass().add("text-item-title");

        String preview = buildPreview(text.getText());
        Label metaLabel = new Label(preview);
        metaLabel.getStyleClass().add("text-item-meta");

        VBox item = new VBox(3, titleLabel, metaLabel);
        item.getStyleClass().add("text-item");
        item.setPadding(new Insets(10, 12, 10, 12));

        item.setOnMouseClicked(e -> {
            selectItem(item);
            openTextById(text.getId());
        });

        return item;
    }

    private void openTextById(int id) {
        Text text = appFacade.getTextById(id);
        if (text == null) {
            return;
        }

        loadingText = true;
        creatingNew = false;
        currentTextId = text.getId();

        titleField.setDisable(false);
        titleField.setText(text.getTitle());
        editorArea.setText(text.getText());

        showEditor();
        hideStatus();

        loadingText = false;
    }

    private void resetEditorForNewText() {
        loadingText = true;
        creatingNew = true;
        currentTextId = null;

        clearSelection();

        titleField.setDisable(false);
        titleField.clear();
        editorArea.clear();

        showEditor();
        setStatusNewDraft();

        loadingText = false;
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

    private void clearSelection() {
        if (selectedItem != null) {
            selectedItem.getStyleClass().remove("active");
            selectedItem = null;
        }
    }

    private void showEmptyState() {
        editorArea.setVisible(false);
        editorArea.setManaged(false);

        emptyStateLabel.setVisible(true);
        emptyStateLabel.setManaged(true);

        titleField.clear();
        titleField.setDisable(true);

        currentTextId = null;
        creatingNew = false;

        hideStatus();
    }

    private void showEditor() {
        editorArea.setVisible(true);
        editorArea.setManaged(true);

        emptyStateLabel.setVisible(false);
        emptyStateLabel.setManaged(false);
    }

    private void setStatusUnsaved() {
        editorStatus.setText(I18n.get("texts.editor.unsaved"));
        editorStatus.getStyleClass().remove("saved");
        showStatus();
    }

    private void setStatusUnsavedWithCount(int count) {
        editorStatus.setText(count + " " + I18n.get("texts.editor.changedWords"));
        editorStatus.getStyleClass().remove("saved");
        showStatus();
    }

    private void setStatusSaved() {
        editorStatus.setText(I18n.get("texts.editor.saved"));
        if (!editorStatus.getStyleClass().contains("saved")) {
            editorStatus.getStyleClass().add("saved");
        }
        showStatus();
    }

    private void setStatusCopied() {
        editorStatus.setText(I18n.get("texts.editor.copied"));
        editorStatus.getStyleClass().remove("saved");
        showStatus();
    }

    private void setStatusNewDraft() {
        editorStatus.setText(I18n.get("texts.editor.newDraft"));
        editorStatus.getStyleClass().remove("saved");
        showStatus();
    }

    private void setStatusPendingUpdate() {
        editorStatus.setText(I18n.get("texts.editor.updatePending"));
        editorStatus.getStyleClass().remove("saved");
        showStatus();
    }

    private void showStatus() {
        editorStatus.setVisible(true);
        editorStatus.setManaged(true);
    }

    private void hideStatus() {
        editorStatus.setVisible(false);
        editorStatus.setManaged(false);
        editorStatus.getStyleClass().remove("saved");
    }

    private String buildPreview(String text) {
        if (text == null || text.isBlank()) {
            return I18n.get("texts.list.emptyPreview");
        }

        String normalized = text.replace("\n", " ").replace("\r", " ").trim();
        return normalized.length() > 42
                ? normalized.substring(0, 42) + "..."
                : normalized;
    }
}