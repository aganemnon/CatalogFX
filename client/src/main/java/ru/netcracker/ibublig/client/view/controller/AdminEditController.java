package ru.netcracker.ibublig.client.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.netcracker.ibublig.client.view.model.Item;

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
    private void initialize(){

    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    public void setItem(Item item){
        this.item = item;
        name.setText(item.getName());
        description.setText(item.getDescription());
        cost.setText(item.getCost());
        count.setText(item.getCount());
    }
    public boolean isOkClicked() {
        return okClicked;
    }
    @FXML
    private void handleOk(){
        item.setName(name.getText());
        item.setDescription(description.getText());
        item.setCost(cost.getText());
        item.setCount(count.getText());

        okClicked = true;
        dialogStage.close();
    }
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
}
