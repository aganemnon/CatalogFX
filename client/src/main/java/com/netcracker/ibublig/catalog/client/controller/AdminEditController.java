package com.netcracker.ibublig.catalog.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.netcracker.ibublig.catalog.client.model.Item;

public class AdminEditController {
    @FXML
    private TextField name;
    @FXML
    private TextField description;
    @FXML
    private TextField cost;
    @FXML
    private TextField count;

    private Stage dialogStage;
    private Item item;
    private boolean okClicked = false;

    @FXML
    private void initialize() {

    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setItem(Item item) {
        this.item = item;
        name.setText(item.getName());
        description.setText(item.getDescription());
        cost.setText(item.getCost());
        count.setText(item.getCount());
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    @FXML
    private void handleOk() {
        if (name.getText().isEmpty() || description.getText().isEmpty() || cost.getText().isEmpty() || count.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Имеются пустые плоля");
            alert.setContentText("Так делать нельзя");

            alert.showAndWait();
        } else {
            if (isInteger(cost.getText()) && isInteger(count.getText())){
                item.setName(name.getText());
                item.setDescription(description.getText());
                item.setCost(cost.getText());
                item.setCount(count.getText());

                okClicked = true;
                dialogStage.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText("Цена или колличество неправильны");
                alert.setContentText("Они должны содердать только цифры!!!");

                alert.showAndWait();
            }

        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
}
