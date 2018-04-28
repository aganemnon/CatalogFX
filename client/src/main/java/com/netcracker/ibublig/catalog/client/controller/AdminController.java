package com.netcracker.ibublig.catalog.client.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import com.netcracker.ibublig.catalog.client.FXMain;
import com.netcracker.ibublig.catalog.client.model.Category;
import com.netcracker.ibublig.catalog.client.model.Item;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;

public class AdminController {
    private ObservableList<Category> categories = FXCollections.observableArrayList();
    private ObservableList<String> categoryName = FXCollections.observableArrayList();

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

    private FXMain fxMain;

    @FXML
    public void initialize() {

    }

    @FXML
    private void addCategory() {
        Category category = new Category();
        boolean okClicked = fxMain.showCategoryEditDialog(category);
        if (okClicked) {
            categories.add(category);
            refreshCategory();
        }
    }

    @FXML
    private void editCategory() {
        Category selectedCategory = categories.get(listView.getSelectionModel().getSelectedIndex());
        if (selectedCategory != null) {
            boolean okClicked = fxMain.showCategoryEditDialog(selectedCategory);
            if (okClicked) {
                //categoryName.clear();
                refreshCategory();
            }
        } else {
            // Ничего не выбрано.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(fxMain.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Item Selected");
            alert.setContentText("Please select a item in the table.");

            alert.showAndWait();
        }
    }

    public void refreshCategory() {
        try {
            categoryName.clear();
        } catch (ArrayIndexOutOfBoundsException e) {

        }
        for (int i = 0; i < categories.size(); i++)
            categoryName.add(categories.get(i).getName());
        listView.setItems(categoryName);
    }

    @FXML
    private void deleteCategory() {
        categories.remove(listView.getSelectionModel().getSelectedIndex());
        refreshCategory();
    }

    @FXML
    private void addItem() {
        Item item = new Item();
        boolean okClicked = fxMain.showItemEditDialog(item);
        if (okClicked) {
            categories.get(listView.getSelectionModel().getSelectedIndex()).addItems(item);
            refreshCategory();
        }
    }

    @FXML
    private void editItem() {
        Item selectedItem = categories.get(listView.getSelectionModel().getSelectedIndex()).getItems().get(categoryTableView.getSelectionModel().getSelectedIndex());
        if (selectedItem != null) {
            boolean okClicked = fxMain.showItemEditDialog(selectedItem);
            if (!okClicked) {
                //fxMain.showItemEditDialog(selectedItem);
            }
        } else {
            // Ничего не выбрано.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(fxMain.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Item Selected");
            alert.setContentText("Please select a item in the table.");

            alert.showAndWait();
        }
    }

    @FXML
    private void deletedItem() {
        categories.get(listView.getSelectionModel().getSelectedIndex()).getItems().remove(categoryTableView.getSelectionModel().getSelectedIndex());
    }

    @FXML
    private void saveOnServer() {
        ArrayList<com.netcracker.ibublig.catalog.model.Category> categories = new ArrayList<>();
        for (Category category : this.categories) {
            ArrayList<com.netcracker.ibublig.catalog.model.Item> items = new ArrayList<>();
            for (int j = 0; j < category.getItems().size(); j++) {
                Item newItem = category.getItems().get(j);
                items.add(new com.netcracker.ibublig.catalog.model.Item(
                        newItem.getName(),
                        newItem.getDescription(),
                        newItem.getCost(),
                        newItem.getCount()));
            }
            categories.add(new com.netcracker.ibublig.catalog.model.Category(items, category.getName()));
        }
        fxMain.getTcpConnection().sendObject(categories);
    }

    public void setCategories(ObservableList<Category> categories) {
        this.categories = categories;
        listView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                categoryNameTableColumn.setCellValueFactory(param -> param.getValue().nameProperty());
                categoryDescriptionTableColumn.setCellValueFactory(param -> param.getValue().descriptionProperty());
                categoryCostTableColumn.setCellValueFactory(param -> param.getValue().costProperty());
                categoryCountTableColumn.setCellValueFactory(param -> param.getValue().countProperty());
                try {
                    if (newValue.intValue() >= 0)
                        categoryTableView.setItems(categories.get(newValue.intValue()).getItems());
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println(newValue.intValue());
                    System.out.println(categories.size());
                }
            }
        });
        refreshCategory();
    }
    @FXML
    private void buttonExit(){
        fxMain.showCatalog(fxMain.getUser());
        fxMain.getCatalogController().setCategories(fxMain.getCategory());
    }

    @FXML
    private void handleAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("JFXCatalog");
        alert.setHeaderText("About");
        alert.setContentText("Author: Ilya Sirotin\nWebsite: https://google.com/");

        alert.showAndWait();
    }


    public void setMain(FXMain fxMain) {
        this.fxMain = fxMain;
    }


}
