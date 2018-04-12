package ru.netcracker.ibublig.client.view.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import ru.netcracker.ibublig.client.view.model.Category;
import ru.netcracker.ibublig.client.view.model.Item;
import java.io.File;
import java.util.Locale;

public class CatalogController {

    private ObservableList<Category> categories = FXCollections.observableArrayList();

    @FXML
    private ListView<String> listView = new ListView<>();
    @FXML
    private TextField serchField;
    @FXML
    private TableView categoryTableView;
    @FXML
    private TableColumn<Category, String> categoryNameTableColumn;
    @FXML
    private TableColumn<Category, String> categoryDescriptionTableColumn;
    @FXML
    private TableColumn<Category, String> categoryCostTableColumn;
    @FXML
    private TableColumn<Category, String> categoryCountTableColumn;

    @FXML
    private void initialize(){
        //TODO Добавить метод который обращается к серверу за XML
        XML xml = new XML(categories);
        //xml.loadPersonDataFromFile(new File("C:\\Data","Catalog.xml"));

        testData();
        for (int i = 0; i < categories.size(); i++) {
            for (int j = 0; j < categories.get(i).getSize(); j++) {
                int finalJ = j;
                System.out.println(finalJ);
                System.out.println(categories.get(i).getNameItem(j));
                categoryNameTableColumn.setCellValueFactory(param -> param.getValue().getNameItem(finalJ));
                categoryDescriptionTableColumn.setCellValueFactory(param -> param.getValue().getDescriptionItem(finalJ));
                categoryCostTableColumn.setCellValueFactory(param -> param.getValue().getCostItem(finalJ));
                categoryCountTableColumn.setCellValueFactory(param -> param.getValue().getCountItem(finalJ));
            }
            categoryTableView.setItems(categories);
        }
//        //Зачем то создавал, трогать не буду
//        final ObservableList<String> test = FXCollections.observableArrayList();
//
//        for (int i = 0; i < categories.size(); i++) {
//            test.add(categories.get(i).getName());
//            //System.out.println(test.get(i));
//        }
//
//        listView.setItems(test);
    }
    private void testData(){
        Category telephone = new Category("Телефон");
        telephone.addItems(new Item("Samsung Galaxy J1","Супер телефон 1",1000,5));
        telephone.addItems(new Item("Samsung Galaxy J3","Супер телефон 2",1000,5));
        telephone.addItems(new Item("Samsung Galaxy J5","Супер телефон 3",1000,5));
        telephone.addItems(new Item("Samsung Galaxy J7","Супер телефон 4",1000,5));
        telephone.addItems(new Item("Ифон С15","Какое то описание",1000000,5));
        categories.add(telephone);

        Category notebook = new Category("Ноутбук");
        notebook.addItems(new Item());
        categories.add(notebook);
        }
}
