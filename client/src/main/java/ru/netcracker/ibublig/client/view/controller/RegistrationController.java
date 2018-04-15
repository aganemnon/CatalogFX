package ru.netcracker.ibublig.client.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ru.netcracker.ibublig.client.FXMain;
import ru.netcracker.ibublig.client.view.model.User;

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
            //fxMain.getUserData().add(new User(firstName.getText(),lastName.getText(),true,login.getText(),truePassword));
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
