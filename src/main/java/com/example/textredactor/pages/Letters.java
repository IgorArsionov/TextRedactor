package com.example.textredactor.pages;

import com.example.textredactor.HelloApplication;
import com.example.textredactor.engine.Engine;
import com.example.textredactor.engine.model.Letter;
import com.example.textredactor.ui.LetterItem;
import com.example.textredactor.ui.MainMenu;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class Letters extends HBox {

    private final VBox left = new VBox();
    private final VBox right = new VBox();
    private VBox listBox;

    public Letters() {
        HBox.setHgrow(this, Priority.ALWAYS);
        VBox.setVgrow(this, Priority.ALWAYS);
        getChildren().addAll(left, right);
        HBox.setHgrow(right, Priority.ALWAYS);

        // ЛЕВОЕ МЕНЮ
        MainMenu menu = new MainMenu(12);
        menu.getLibraryBtn().getStyleClass().add("active");
        VBox.setVgrow(menu, Priority.ALWAYS);
        left.getChildren().add(menu);

        initLayout();
        initLettersItem();
    }

    private void initLayout() {
        VBox card = createCard();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.getStyleClass().add("scroll-pane");
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        listBox = new VBox(10);
        listBox.setPadding(new Insets(5));

        scrollPane.setContent(listBox);

        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        card.getChildren().add(scrollPane);

        right.getChildren().add(card);
    }

    private VBox createCard() {
        VBox card = new VBox(15);
        card.getStyleClass().add("content-card");
        card.setPadding(new Insets(15));
        VBox.setVgrow(card, Priority.ALWAYS);
        return card;
    }

    private void initLettersItem() {
        listBox.getChildren().clear();
        for (int i = 0; i < Engine.getLettersList().size(); i++) {
            LetterItem letterItem = new LetterItem(Engine.getLettersList().get(i));
            int step = i;
            Letter letterStep = Engine.getLettersList().get(step);
            letterItem.getDelete().setOnAction(event -> {
                Engine.deleteLetter(letterStep.getFile());
                Engine.getLettersList().remove(letterStep);
                initLettersItem();
            });

            letterItem.getOpen().setOnAction(event -> {
                Text text = (Text) HelloApplication.getCard("Text");
                text.setLetter(letterStep);
                HelloApplication.showCard("Text");
            });
            listBox.getChildren().add(letterItem);

        }
    }
}
