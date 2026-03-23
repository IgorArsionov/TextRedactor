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

    private final TextField searchField = new TextField();
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

        searchField.setPromptText(I18n.get("texts.search"));
        searchField.getStyleClass().add("search-field");
        searchField.setMaxWidth(260);

        searchField.textProperty().addListener((obs, oldValue, newValue) -> {
            filterTexts(newValue);
        });

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

        Button deleteButton = new Button(I18n.get("texts.editor.delete"));
        deleteButton.getStyleClass().add("ghost-button");

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

        deleteButton.setOnAction(e -> {
            if (currentTextId == null) {
                return;
            }

            appFacade.deleteText(currentTextId);
            clearSelection();
            showEmptyState();
            loadTextsFromFacade();

            editorStatus.setText(I18n.get("texts.editor.deleted"));
            editorStatus.getStyleClass().remove("saved");
            showStatus();
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
                appFacade.createText(title, text);
                loadTextsFromFacade();
                resetEditorForNewText(false);

                editorStatus.setText(I18n.get("texts.editor.saved"));
                if (!editorStatus.getStyleClass().contains("saved")) {
                    editorStatus.getStyleClass().add("saved");
                }
                showStatus();
                return;
            }

            Text updated = appFacade.updateText(currentTextId, title, text);
            if (updated != null) {
                currentTextId = updated.getId();
            }

            setStatusSaved();
            loadTextsFromFacade();
        });

        HBox leftActions = new HBox(8, processButton, copyButton, deleteButton);

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
        filterTexts(searchField.getText());
    }

    private void filterTexts(String query) {
        List<Text> allTexts = appFacade.getAllTexts();

        if (query == null || query.isBlank()) {
            renderTexts(allTexts);
            return;
        }

        String normalized = query.trim().toLowerCase();

        List<Text> filtered = allTexts.stream()
                .filter(text ->
                        safe(text.getTitle()).toLowerCase().contains(normalized)
                                || safe(text.getText()).toLowerCase().contains(normalized)
                )
                .toList();

        renderTexts(filtered);
    }

    private void renderTexts(List<Text> texts) {
        listBox.getChildren().clear();

        if (texts.isEmpty()) {
            Label emptyLabel = new Label(I18n.get("texts.list.notFound"));
            emptyLabel.getStyleClass().add("empty-state-label");
            listBox.getChildren().add(emptyLabel);
            return;
        }

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

        if (currentTextId != null && currentTextId == text.getId()) {
            item.getStyleClass().add("active");
            selectedItem = item;
        }

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
        resetEditorForNewText(true);
    }

    private void resetEditorForNewText(boolean showDraftStatus) {
        loadingText = true;
        creatingNew = true;
        currentTextId = null;

        clearSelection();

        titleField.setDisable(false);
        titleField.clear();
        editorArea.clear();

        showEditor();

        if (showDraftStatus) {
            setStatusNewDraft();
        } else {
            hideStatus();
        }

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

    private String safe(String value) {
        return value == null ? "" : value;
    }
}