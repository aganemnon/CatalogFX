package ru.netcracker.ibublig.client.view.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Item {

    private StringProperty name;
    private StringProperty description;
    private StringProperty cost;
    private StringProperty count;

    public Item() {
        name = new SimpleStringProperty("test");
        description = new SimpleStringProperty("test");
        cost = new SimpleStringProperty("1");
        count = new SimpleStringProperty("2");
    }

    public Item(String name, String description, String cost, String count) {
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.cost = new SimpleStringProperty(cost);
        this.count = new SimpleStringProperty(count);
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

    public String getCost() {
        return cost.get();
    }

    public StringProperty costProperty() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost.set(cost);
    }

    public String getCount() {
        return count.get();
    }

    public StringProperty countProperty() {
        return count;
    }

    public void setCount(String count) {
        this.count.set(count);
    }
}
