package com.example.textredactor.ui.pages;

import com.example.textredactor.ui.i18n.I18n;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.*;

public class TextsPage extends VBox {

    private final VBox listBox = new VBox(6);

    private final TextField titleField = new TextField();
    private final Label editorStatus = new Label();
    private final TextArea editorArea = new TextArea();
    private final Label emptyStateLabel = new Label(I18n.get("texts.editor.emptyState"));

    private VBox selectedItem;
    private boolean loadingText = false;
    private boolean creatingNew = false;

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
        loadDemoTexts();

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

            // TODO: подключить facade
            // String processedText = textFacade.process(editorArea.getText());
            // loadingText = true;
            // editorArea.setText(processedText);
            // loadingText = false;

            setStatusUnsaved();
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

            // TODO: подключить facade
            // textFacade.save(...)

            setStatusSaved();
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

    private void loadDemoTexts() {
        listBox.getChildren().clear();

        listBox.getChildren().addAll(
                createTextItem(
                        1L,
                        "Partnership Reply",
                        "Client communication",
                        """
                        Hello,

                        Thank you for your message.
                        We reviewed your proposal and would like to continue the discussion.

                        Best regards,
                        Igor
                        """
                ),
                createTextItem(
                        2L,
                        "Delivery Follow-up",
                        "Important draft",
                        """
                        Good afternoon,

                        I wanted to check if there are any updates regarding the shipment.
                        Please let me know the expected delivery window.

                        Thank you.
                        """
                ),
                createTextItem(
                        3L,
                        "Short Note",
                        "Personal draft",
                        """
                        Reminder:
                        update internal notes
                        and check the man records later.
                        """
                )
        );
    }

    private VBox createTextItem(Long id, String title, String meta, String content) {
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("text-item-title");

        Label metaLabel = new Label(meta);
        metaLabel.getStyleClass().add("text-item-meta");

        VBox item = new VBox(3, titleLabel, metaLabel);
        item.getStyleClass().add("text-item");
        item.setPadding(new Insets(10, 12, 10, 12));

        item.setOnMouseClicked(e -> {
            selectItem(item);
            openText(id, title, content);
        });

        return item;
    }

    private void openText(Long id, String title, String content) {
        loadingText = true;
        creatingNew = false;

        titleField.setDisable(false);
        titleField.setText(title);
        editorArea.setText(content);

        showEditor();
        hideStatus();

        loadingText = false;
    }

    private void resetEditorForNewText() {
        loadingText = true;
        creatingNew = true;

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
}