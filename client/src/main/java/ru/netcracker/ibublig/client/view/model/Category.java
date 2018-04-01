package ru.netcracker.ibublig.client.view.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Category {
    private StringProperty name;
    private ObservableList<Item> items = FXCollections.observableArrayList();

    public Category() {
        name = new SimpleStringProperty("test");
        items.add(new Item());
    }
    public Category(String name, ObservableList<Item> items){
        this.name = new SimpleStringProperty(name);
        this.items = items;
    }

    public Category(String name, Item items) {
        this.name = new SimpleStringProperty(name);
        this.items.add(items);
    }

    public String getNameItem(){
        return items.get(0).getName();
    }
    public String getDescriptionItem(){
        return items.get(0).getDescription();
    }
    public Integer getCostItem(){
        return items.get(0).getCost();
    }
    public Integer getCountItem(){
        return items.get(0).getCount();
    }

    public String getName() {
        return name.getValue();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public ObservableList<Item> getItems() {
        return items;
    }

    public void setItems(ObservableList<Item> items) {
        this.items = items;
    }
}
