package ru.netcracker.ibublig.client.view.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ru.netcracker.ibublig.client.FXMain;
import ru.netcracker.ibublig.client.view.model.Category;
import ru.netcracker.ibublig.client.view.model.Item;

import java.io.File;

public class AdminController {
    private ObservableList<Category> categories = FXCollections.observableArrayList();
    private ObservableList<String> test = FXCollections.observableArrayList();

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
        XML xml = new XML(categories);
        xml.loadPersonDataFromFile(new File("C:\\Data", "Catalog.xml"));

        listView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                int i;
                categoryNameTableColumn.setCellValueFactory(param -> param.getValue().nameProperty());
                categoryDescriptionTableColumn.setCellValueFactory(param -> param.getValue().descriptionProperty());
                categoryCostTableColumn.setCellValueFactory(param -> param.getValue().costProperty());
                categoryCountTableColumn.setCellValueFactory(param -> param.getValue().countProperty());
                if (!categories.isEmpty() && (categories.get(newValue.intValue()).getSize() != 0)) {
                    categoryTableView.setItems(categories.get(newValue.intValue()).getItems());
                }
            }
        });

        for (int i = 0; i < categories.size(); i++) {
            test.add(categories.get(i).getName());
            //System.out.println(test.get(i));
        }

        listView.setItems(test);
    }

    @FXML
    private void addCategory() {

    }

    @FXML
    private void editCategory() {
    }

    @FXML
    private void deleteCategory() {
        categories.remove(listView.getSelectionModel().getSelectedIndex());
        test.clear();
        for (int i = 0; i < categories.size(); i++)
            test.add(categories.get(i).getName());
        listView.setItems(test);
    }

    @FXML
    private void addItem() {
        Item item = new Item();
        boolean okClicked = fxMain.showItemEditDialog(item);
        if (okClicked) {
            categories.get(listView.getSelectionModel().getSelectedIndex()).addItems(item);
        }
    }

    @FXML
    private void editItem() {
        Item selectedItem = categories.get(listView.getSelectionModel().getSelectedIndex()).getItems().get(categoryTableView.getSelectionModel().getSelectedIndex());
        if (selectedItem != null) {
            boolean okClicked = fxMain.showItemEditDialog(selectedItem);
            if (okClicked) {
                fxMain.showItemEditDialog(selectedItem);
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

    public void getMain(FXMain fxMain) {
        this.fxMain = fxMain;
    }


}
