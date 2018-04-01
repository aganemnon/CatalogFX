package ru.netcracker.ibublig.client.view.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.netcracker.ibublig.client.view.model.Category;
import ru.netcracker.ibublig.client.view.model.Item;

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
    private TableColumn<Category, Integer> categoryCostTableColumn;
    @FXML
    private TableColumn<Category, Integer> categoryCountTableColumn;

    @FXML
    private void initialize(){
        testData();
        categoryNameTableColumn.setCellValueFactory(new PropertyValueFactory<Category, String>("nameItem"));
        categoryDescriptionTableColumn.setCellValueFactory(new PropertyValueFactory<Category, String>("descriptionItem"));
        categoryCostTableColumn.setCellValueFactory(new PropertyValueFactory<Category, Integer>("costItem"));
        categoryCountTableColumn.setCellValueFactory(new PropertyValueFactory<Category, Integer>("countItem"));
        final ObservableList<String> test = FXCollections.observableArrayList("test", "test2");
        for (int i = 0; i < categories.size(); i++) {
            test.add(categories.get(i).getName());
            System.out.println(test.get(i));
        }
        categoryTableView.setItems(categories);
        listView.setItems(test);
    }
    private void testData(){
        categories.add(new Category("Category", new Item("Test","Oist1",10,111)));
        categories.add(new Category("Category", new Item("Tes2t","Oist2",1212,15)));
        categories.add(new Category("Category", new Item("T4est","Oist3",145,10)));
        categories.add(new Category("Category", new Item("Te4st","Oist4",1479,11)));
        categories.add(new Category());
        categories.add(new Category());
    }
}
