package com.netcracker.ibublig.catalog.client.controller;

import com.netcracker.ibublig.catalog.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import com.netcracker.ibublig.catalog.client.FXMain;

public class RegistrationController {

    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField login;
    @FXML
    private TextField password;
    @FXML
    private TextField confirmPassword;
    @FXML
    private TextField email;
    @FXML
    private Label errorPassword;

    private FXMain fxMain = new FXMain();

    public RegistrationController() {

    }

    @FXML
    private void registration() {
        if (password.getText().equals(confirmPassword.getText())) {
            String truePassword;
            truePassword = password.getText();
            fxMain.getTcpConnection().sendObject(new User(firstName.getText(),lastName.getText(),login.getText(),truePassword,false));
            fxMain.initAuthorization();
        } else {
            errorPassword.setText("Введеный вами пароль не совпадает!!!");
        }
    }

    @FXML
    private void cancel() {
        fxMain.initAuthorization();
    }

    @FXML
    private void initialize() {
    }

    public void setMainApp(FXMain fxMain) {
        this.fxMain = fxMain;
    }
}
