package com.netcracker.ibublig.catalog.client.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import com.netcracker.ibublig.catalog.client.FXMain;
import com.netcracker.ibublig.catalog.client.model.Category;
import com.netcracker.ibublig.catalog.client.model.Item;
import com.netcracker.ibublig.catalog.model.User;

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
    }

    @FXML
    private void admin() {
        if (user.isAdmin()) {
            try {
                fxMain.rootLogger.info("Вход в админ панель");
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(fxMain.getClass().getResource("view/AdminLayout.fxml"));
                Scene scene = new Scene((AnchorPane) loader.load());
                AdminController controller = loader.getController();
                controller.setMain(fxMain);
                controller.setCategories(categories);
                fxMain.getPrimaryStage().setScene(scene);
                fxMain.getPrimaryStage().show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(fxMain.getPrimaryStage());
            fxMain.rootLogger.error("Доступ запрещен");
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
    public void showCatalog(){
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
    public void setCategories(ObservableList<Category> categories){
        this.categories = categories;
        showCatalog();

    }
    public void setCategories(ArrayList<com.netcracker.ibublig.catalog.model.Category> category) {
        for (int i = 0; i < category.size(); i++) {
            ObservableList<Item> items = FXCollections.observableArrayList();
            for (int j = 0; j < category.get(i).getItems().size(); j++) {
                items.add(new Item(
                        category.get(i).getItems().get(j).getName(),
                        category.get(i).getItems().get(j).getDescription(),
                        category.get(i).getItems().get(j).getCost(),
                        category.get(i).getItems().get(j).getCount()
                ));
            }
            categories.add(new Category(category.get(i).getNameCategory(),items));
        }
        showCatalog();
    }
}
