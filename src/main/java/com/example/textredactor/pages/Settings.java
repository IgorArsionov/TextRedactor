package com.example.textredactor.pages;

import com.example.textredactor.CreateFile;
import com.example.textredactor.HelloApplication;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Settings extends VBox {
    private CreateFile createFile = CreateFile.init();
    private List<Block> blockList = new ArrayList<>();
    private VBox vBox;

    public Settings() {
        setStyle("-fx-padding: 10px;");

        // Блок СкроллПейн
        try {
            setSettings();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        // конец Блок СкроллПейн

        VBox bot = new VBox(10);
        Button saveSettings = new Button("Save and exit");
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
        });

        Button add = new Button("Add++");
        add.setOnAction(e -> {
            blockList.add(new Block(new String[]{"", " ; "}));
            vBox.getChildren().clear();
            vBox.getChildren().addAll(blockList);
        });

        bot.getChildren().addAll(saveSettings, add);

        getChildren().addAll(bot);
    }

    private class Block extends HBox {
        private TextField wordField;
        private TextField positionField;
        private TextField letterField;

        private Block(String[] data) {
            super(10);
            wordField = new TextField();
            wordField.setPromptText("Слово");
            wordField.setPrefColumnCount(15);
            wordField.setText(data[0]);
            wordField.setPrefWidth(100);

            positionField = new TextField();
            positionField.setPromptText("Позиция");
            positionField.setPrefColumnCount(3);
            positionField.setText(data[1].split(";")[0]);

            letterField = new TextField();
            letterField.setPromptText("Буква");
            letterField.setPrefColumnCount(15);
            letterField.setText(data[1].split(";")[1]);
            letterField.setPrefWidth(100);

            CheckBox autoPositionCheckBox = new CheckBox("All");

            autoPositionCheckBox
                    .selectedProperty()
                    .addListener((obs, oldVal, newVal) -> {
                if (newVal) {
                    positionField.setText("0");
                    positionField.setDisable(true);
                } else {
                    positionField.setDisable(false);
                }
            });

            getChildren().addAll(wordField, positionField, letterField, autoPositionCheckBox);
        }

        public String getWordField() {
            return wordField.getText();
        }

        public String getPositionField() {
            return positionField.getText();
        }

        public String getLetterField() {
            return letterField.getText();
        }

        public String toString() {
            return wordField.getText() + "=" + positionField.getText() + ";" + letterField.getText();
        }
    }

    private void setSettings() throws FileNotFoundException {
        String fileName = "words.txt";
        File file = new File(fileName);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.getStyleClass().add("scroll-pane");
        vBox = new VBox(10);
        vBox.setStyle("-fx-padding: 5px;");

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.length() != 0) {
                    String[] strings = line.split("=");
                    blockList.add(new Block(strings));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read file words: " + fileName, e);
        }
        scrollPane.setContent(vBox);
        vBox.getChildren().addAll(blockList);
        getChildren().add(scrollPane);
    }

    private boolean findEmpty(Block block) {
        String a1 = block.getLetterField().trim();
        String a2 = block.getPositionField().trim();
        String a3 = block.getWordField().trim();

        return a1.isEmpty() || a2.isEmpty() || a3.isEmpty();
    }
}
