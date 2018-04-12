package ru.netcracker.ibublig.client.view.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Item {

    private StringProperty name;
    private StringProperty description;
    private IntegerProperty cost;
    private IntegerProperty count;
    int i = 0;

    public Item(){
        name = new SimpleStringProperty("test" + i);
        description = new SimpleStringProperty("test");
        cost = new SimpleIntegerProperty(1);
        count = new SimpleIntegerProperty(2);
        i++;
    }

    public Item(String name, String description, int cost, int count) {
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.cost = new SimpleIntegerProperty(cost);
        this.count = new SimpleIntegerProperty(count);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public Integer getCost() {
        return cost.get();
    }

    public IntegerProperty costProperty() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost.set(cost);
    }

    public Integer getCount() {
        return count.get();
    }

    public IntegerProperty countProperty() {
        return count;
    }

    public void setCount(int count) {
        this.count.set(count);
    }
}
