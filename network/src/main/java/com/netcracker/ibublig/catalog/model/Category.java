package com.netcracker.ibublig.catalog.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Category implements Serializable {
    private ArrayList<Item> items;
    private String nameCategory;

    public Category(ArrayList<Item> items, String nameCategory) {
        this.items = items;
        this.nameCategory = nameCategory;
    }
    public Category(){
        nameCategory = "";
        items = new ArrayList<>();
    }

    public Category(Item item, String nameCategory) {
        items = new ArrayList<>();
        items.add(item);
        this.nameCategory = nameCategory;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void remodeItem(int index) {
        items.remove(index);
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    @Override
    public String toString() {
        return "Category{}";
    }
}
