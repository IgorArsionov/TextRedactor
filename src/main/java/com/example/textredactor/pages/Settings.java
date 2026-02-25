package com.example.textredactor.pages;

import com.example.textredactor.CreateFile;
import com.example.textredactor.HelloApplication;
import com.example.textredactor.ui.Block;
import com.example.textredactor.ui.MainMenu;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Settings extends HBox {

    private final CreateFile createFile = CreateFile.init();
    private final List<Block> blockList = new ArrayList<>();

    private final VBox left = new VBox();
    private final VBox right = new VBox();
    private final MainMenu mainMenu = new MainMenu(12);

    private VBox vBox;
    private VBox card;

    public Settings() {
        HBox.setHgrow(this, Priority.ALWAYS);
        VBox.setVgrow(this, Priority.ALWAYS);
        setStyle("-fx-padding: 0px;");
        getChildren().addAll(left, right);
        HBox.setHgrow(right, Priority.ALWAYS);

        initSidebar();
        initContent();
    }

    private void initSidebar() {
        mainMenu.getSettingsBtn().getStyleClass().add("active");
        VBox.setVgrow(mainMenu, Priority.ALWAYS);
        left.getChildren().add(mainMenu);
    }

    private void initContent() {
        card = createCard();

        initScrollPane();
        initWords();
        renderBlocks();

        HBox bottomPanelMenu = new HBox(10);
        bottomPanelMenu.setPadding(new Insets(10, 0, 0, 0));

        Button saveSettings = initSaveBtn();
        Button addNewWord = initAddBtn();

        bottomPanelMenu.getChildren().addAll(saveSettings, addNewWord);

        card.getChildren().add(bottomPanelMenu);

        right.getChildren().add(card);
    }

    private void initScrollPane() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.getStyleClass().add("scroll-pane");

        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        vBox = new VBox(10);
        vBox.setStyle("-fx-padding: 5px;");

        scrollPane.setContent(vBox);

        card.getChildren().add(scrollPane);
    }

    private void initWords() {
        String fileName = "words.txt";
        File file = new File(fileName);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();

                if (!line.isEmpty()) {
                    String[] strings = line.split("=");
                    blockList.add(new Block(strings));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read file words: " + fileName, e);
        }
    }

    private void renderBlocks() {
        vBox.getChildren().addAll(blockList);
    }

    private Button initAddBtn() {
        Button buttonAddNewBlock = new Button("Add++");

        buttonAddNewBlock.setOnAction(e -> {
            Block newBlock = new Block(new String[]{"", " ; "});
            blockList.add(newBlock);
            vBox.getChildren().add(newBlock);
        });

        return buttonAddNewBlock;
    }

    private Button initSaveBtn() {
        Button saveSettings = new Button("Save and exit");
        saveSettings.getStyleClass().add("primary");

        saveSettings.setOnAction(e -> {
            StringBuilder builder = new StringBuilder();

            for (Block block : blockList) {
                if (findEmpty(block)) {
                    continue;
                }

                builder.append(block.toString());
                builder.append("\n");
            }

            createFile.writeFile(builder.toString());
            HelloApplication.showCard("General");
            General general = (General) HelloApplication.getCard("General");
            general.getMenu().showSuccess("Настройки сохранены");
        });

        return saveSettings;
    }

    private boolean findEmpty(Block block) {
        String a1 = block.getLetterField().trim();
        String a2 = block.getPositionField().trim();
        String a3 = block.getWordField().trim();

        return a1.isEmpty() || a2.isEmpty() || a3.isEmpty();
    }

    private VBox createCard() {
        VBox card = new VBox(15);
        card.getStyleClass().add("content-card");
        card.setPadding(new Insets(15));

        VBox.setVgrow(card, Priority.ALWAYS);

        return card;
    }
}