package ru.netcracker.ibublig.client.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.netcracker.ibublig.client.view.model.Category;

public class AdminEditCategoryController {
    @FXML
    private TextField name;

    private Stage dialogStage;
    private Category category;
    private boolean okClicked = false;

    @FXML
    private void initialize() {

    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setCategory(Category category) {
        this.category = category;
        name.setText(category.getName());
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() {
        category.setName(name.getText());
        okClicked = true;
        dialogStage.close();
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
}
