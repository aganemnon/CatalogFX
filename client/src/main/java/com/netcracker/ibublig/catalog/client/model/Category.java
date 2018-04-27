package com.netcracker.ibublig.catalog.client.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Category {
    private StringProperty name;
    private ObservableList<Item> items;

    public Category() {
        name = new SimpleStringProperty("test");
        ObservableList<Item> newItem = FXCollections.observableArrayList();
        items = newItem;
    }

    public Category(String name) {
        items = FXCollections.observableArrayList();
        this.name = new SimpleStringProperty(name);
    }

    public Category(String name, ObservableList<Item> items) {
        this.name = new SimpleStringProperty(name);
        this.items = items;
    }

    public Category(String name, Item items) {
        this.items = FXCollections.observableArrayList();
        this.name = new SimpleStringProperty(name);
        this.items.add(items);
    }

    public int getSize() {
        return items.size();
    }

    public ObservableValue<String> getNameItem(int i) {
        int size = items.size();
        ObservableValue<String> test;
        if (i < size) {
            test = new SimpleStringProperty(items.get(i).getName());
        } else
            return null;
        return test;
    }

    public ObservableValue<String> getDescriptionItem(int i) {
        int size = items.size();
        ObservableValue<String> test;
        if (i < size) {
            test = new SimpleStringProperty(items.get(i).getDescription());
        } else
            return null;
        return test;
    }

    public ObservableValue<String> getCostItem(int i) {
        int size = items.size();
        ObservableValue<String> test;
        if (i < size) {
            test = new SimpleStringProperty(items.get(i).getCost());
        } else
            return null;
        return test;
    }

    public ObservableValue<String> getCountItem(int i) {
        int size = items.size();
        ObservableValue<String> test;
        if (i < size) {
            test = new SimpleStringProperty(items.get(i).getCount());
        } else
            return null;
        return test;
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

    public void addItems(Item item) {
        items.add(item);
    }
}
