package com.example.textredactor.pages;

import com.example.textredactor.HelloApplication;
import com.example.textredactor.engine.model.Man;
import com.example.textredactor.engine.service.ManService;
import com.example.textredactor.engine.service.impl.ManServiceImpl;
import com.example.textredactor.engine.data.Data;
import com.example.textredactor.ui.MainMenu;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

import java.util.List;

public class ManPage extends HBox {

    private final VBox left = new VBox();
    private final VBox right = new VBox();
    private static VBox listBox;

    private static final ManService manService = new ManServiceImpl();

    public ManPage() {
        HBox.setHgrow(this, Priority.ALWAYS);
        VBox.setVgrow(this, Priority.ALWAYS);

        getChildren().addAll(left, right);
        HBox.setHgrow(right, Priority.ALWAYS);

        // ЛЕВОЕ МЕНЮ
        MainMenu menu = new MainMenu(12);
        VBox.setVgrow(menu, Priority.ALWAYS);
        left.getChildren().add(menu);

        initLayout();
        refreshList();
    }

    private void initLayout() {
        VBox card = createCard();

        // Верхняя панель
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(5));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button createBtn = new Button("Create new");
        createBtn.getStyleClass().add("primary-btn");

        createBtn.setOnAction(e -> {
            HelloApplication.showCard(Data.pageAddManPage);
        });

        topBar.getChildren().addAll(spacer, createBtn);

        // Список
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.getStyleClass().add("scroll-pane");
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        listBox = new VBox(6);
        listBox.setPadding(new Insets(5));

        scrollPane.setContent(listBox);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        card.getChildren().addAll(topBar, scrollPane);
        right.getChildren().add(card);
    }

    private VBox createCard() {
        VBox card = new VBox(10);
        card.getStyleClass().add("content-card");
        card.setPadding(new Insets(15));
        VBox.setVgrow(card, Priority.ALWAYS);
        return card;
    }

    /**
     * Обновление списка Man
     */
    public static void refreshList() {
        if (listBox == null) return;

        listBox.getChildren().clear();

        List<Man> manList = manService.getManList();

        for (Man man : manList) {
            HBox item = createManItem(man);
            listBox.getChildren().add(item);
        }
    }

    private static HBox createManItem(Man man) {
        HBox item = new HBox(10);
        item.getStyleClass().add("man-item");
        item.setPadding(new Insets(8));

        VBox info = new VBox(2);

        javafx.scene.control.Label nameLabel =
                new javafx.scene.control.Label(man.getName());
        nameLabel.getStyleClass().add("man-name");

        javafx.scene.control.Label meta =
                new javafx.scene.control.Label(man.getCountry() + " • " + man.getCity());
        meta.getStyleClass().add("man-meta");

        info.getChildren().addAll(nameLabel, meta);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button open = new Button("Open");
        open.getStyleClass().add("small-btn");

        item.getChildren().addAll(info, spacer, open);

        return item;
    }
}