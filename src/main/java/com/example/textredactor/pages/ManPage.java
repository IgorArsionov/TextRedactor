package com.example.textredactor.pages;

import com.example.textredactor.HelloApplication;
import com.example.textredactor.engine.data.Data;
import com.example.textredactor.engine.file.ManFileManager;
import com.example.textredactor.engine.model.Man;
import com.example.textredactor.engine.service.ManService;
import com.example.textredactor.engine.service.impl.ManServiceImpl;
import com.example.textredactor.ui.MainMenu;
import com.example.textredactor.ui.ManItem;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

public class ManPage extends HBox {

    private final VBox left = new VBox();
    private final VBox right = new VBox();
    private VBox listBox;

    private static final ManService manService = new ManServiceImpl();

    public ManPage() {
        setSpacing(0);

        HBox.setHgrow(this, Priority.ALWAYS);
        VBox.setVgrow(this, Priority.ALWAYS);

        getChildren().addAll(left, right);
        HBox.setHgrow(right, Priority.ALWAYS);

        // ЛЕВОЕ МЕНЮ
        MainMenu menu = new MainMenu(12);
        VBox.setVgrow(menu, Priority.ALWAYS);
        left.getChildren().add(menu);

        ManFileManager manFileManager = ManFileManager.init();
        manFileManager.readFileContent(Man.class);

        initLayout();
        initMenList();
    }

    private void initLayout() {
        VBox card = createCard();

        // Верхняя панель
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(0, 0, 10, 0));

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
     * Заполняем список мужчин
     */
    private void initMenList() {
        listBox.getChildren().clear();

        for (Man man : manService.getManList()) {
            ManItem item = new ManItem(man);

            item.getOpenBtn().setOnAction(e -> {
                ManViewPage card = (ManViewPage) HelloApplication.getCard(Data.pageManView);
                card.setMan(man);
                HelloApplication.showCard(Data.pageManView);
            });

            listBox.getChildren().add(item);
        }
    }

    /**
     * Метод обновления списка
     */
    public void refreshList() {
        initMenList();
    }
}