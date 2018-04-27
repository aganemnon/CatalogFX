package com.netcracker.ibublig.catalog.model;

import java.io.Serializable;

public class Item implements Serializable{
    private String name;
    private String description;
    private String cost;
    private String count;

    public Item(String name, String description, String cost, String count) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.count = count;
    }

    public Item() {
        name = "Название товара";
        description = "Описание товара";
        cost = "Цена товара";
        count = "Колличество товара";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
