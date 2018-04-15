package ru.netcracker.ibublig.client.view.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import ru.netcracker.ibublig.client.FXMain;
import ru.netcracker.ibublig.client.view.model.Category;
import ru.netcracker.ibublig.client.view.model.Item;
import ru.netcracker.ibublig.model.User;

import java.io.IOException;
import java.util.ArrayList;

public class CatalogController {

    private ObservableList<Category> categories = FXCollections.observableArrayList();
    private FXMain fxMain;
    private User user;

    @FXML
    private ListView<String> listView = new ListView<>();
    @FXML
    private TableView categoryTableView;
    @FXML
    private TableColumn<Item, String> categoryNameTableColumn;
    @FXML
    private TableColumn<Item, String> categoryDescriptionTableColumn;
    @FXML
    private TableColumn<Item, String> categoryCostTableColumn;
    @FXML
    private TableColumn<Item, String> categoryCountTableColumn;
    @FXML
    private Label welcome;

    public CatalogController() {

    }

    @FXML
    private void initialize() {
        listView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                categoryNameTableColumn.setCellValueFactory(param -> param.getValue().nameProperty());
                categoryDescriptionTableColumn.setCellValueFactory(param -> param.getValue().descriptionProperty());
                categoryCostTableColumn.setCellValueFactory(param -> param.getValue().costProperty());
                categoryCountTableColumn.setCellValueFactory(param -> param.getValue().countProperty());
                if (!categories.isEmpty() && (categories.get(newValue.intValue()).getSize() != 0)) {
                    categoryTableView.setItems(categories.get(newValue.intValue()).getItems());
                }
            }
        });

        final ObservableList<String> listViewNameCategory = FXCollections.observableArrayList();

        for (int i = 0; i < categories.size(); i++) {
            listViewNameCategory.add(categories.get(i).getName());
        }

        listView.setItems(listViewNameCategory);
    }

    @FXML
    private void admin() {
        if (user.isAdmin()) {
            try {
                System.out.println("Вход в админ панель");
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(fxMain.getClass().getResource("view/view/AdminLayout.fxml"));
                Scene scene = new Scene((AnchorPane) loader.load());
                AdminController controller = loader.getController();
                controller.getMain(fxMain);
                fxMain.getPrimaryStage().setScene(scene);
                fxMain.getPrimaryStage().show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(fxMain.getPrimaryStage());
            alert.setTitle("Доступ запрещен");
            alert.setHeaderText("Вы не имеете права администратора");
            alert.setContentText("Закройте и не приходите сюда больше");

            alert.showAndWait();
        }
    }

    public void setMainApp(FXMain fxMain) {
        this.fxMain = fxMain;
    }

    public void setUser(User user) {
        this.user = user;
        welcome.setText("Добро пожаловать, " + user.getFirstName() + " " + user.getLastName());
    }

    public void setCategories(ArrayList<ru.netcracker.ibublig.model.Category> category) {
        ObservableList<Item> items = FXCollections.observableArrayList();
        for (int i = 0; i < category.size(); i++) {
            for (int j = 0; j < category.get(i).getItems().size(); j++) {
                items.add(new Item(category.get(i).getItems().get(j).getName(),
                        category.get(i).getItems().get(j).getDescription(),
                        category.get(i).getItems().get(j).getCost(),
                        category.get(i).getItems().get(j).getCount()));
            }
            categories.add(new Category(category.get(i).getNameCategory(),items));
        }
        initialize();
    }

    private void testData() {
        Category telephone = new Category("Телефон");
        telephone.addItems(new Item("Samsung Galaxy J1", "Супер телефон 1", "1000", "5"));
        telephone.addItems(new Item("Samsung Galaxy J3", "Супер телефон 2", "1000", "5"));
        telephone.addItems(new Item("Samsung Galaxy J5", "Супер телефон 3", "1000", "5"));
        telephone.addItems(new Item("Samsung Galaxy J7", "Супер телефон 4", "1000", "5"));
        telephone.addItems(new Item("Ифон С15", "Какое то описание", "1000000", "5"));
        categories.add(telephone);

        Category notebook = new Category("Ноутбук");
        notebook.addItems(new Item("Acer", "laptop", "15", "35"));
        categories.add(notebook);
    }
}
