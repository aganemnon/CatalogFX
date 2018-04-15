package ru.netcracker.ibublig.client.view.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import ru.netcracker.ibublig.client.FXMain;
import ru.netcracker.ibublig.client.view.model.Category;
import ru.netcracker.ibublig.client.view.model.Item;

import java.io.File;
import java.io.IOException;

public class CatalogController {

    private ObservableList<Category> categories = FXCollections.observableArrayList();
    FXMain fxMain;

    @FXML
    private ListView<String> listView = new ListView<>();
    @FXML
    private TextField serchField;
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
    private void initialize() {
        //TODO Добавить метод который обращается к серверу за XML
        XML xml = new XML(categories);
        xml.loadPersonDataFromFile(new File("C:\\Data", "Catalog.xml"));

        //testData();

        //xml.savePersonDataToFile(new File("C:\\Data","Catalog.xml"));

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


        //Зачем то создавал, трогать не буду
        final ObservableList<String> test = FXCollections.observableArrayList();

        for (int i = 0; i < categories.size(); i++) {
            test.add(categories.get(i).getName());
            //System.out.println(test.get(i));
        }

        listView.setItems(test);

    }

    @FXML
    private void admin() {
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
    }

    public void setMainApp(FXMain fxMain) {
        this.fxMain = fxMain;
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
