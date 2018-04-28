package com.netcracker.ibublig.catalog.client.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import com.netcracker.ibublig.catalog.client.FXMain;
import com.netcracker.ibublig.catalog.model.User;
import com.netcracker.ibublig.catalog.network.TCPConnection;

import java.io.IOException;

public class AuthorizationController {

    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorPassword;
    @FXML
    private Button auth;

    private AnchorPane registrationLayout;
    private Scene scene;

    private FXMain fxMain;
    private TCPConnection tcpConnection;


    public AuthorizationController() {

    }

    @FXML
    private void authorization() {
        tcpConnection.sendObject(new User(loginField.getText(),passwordField.getText()));
    }

    @FXML
    private void registration() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(fxMain.getClass().getResource("view/RegistrationLayout.fxml"));
            registrationLayout = (AnchorPane) loader.load();

            RegistrationController controller = loader.getController();
            controller.setMainApp(fxMain);

            Scene scene = new Scene(registrationLayout);
            fxMain.getPrimaryStage().setScene(scene);
            fxMain.getPrimaryStage().show();

        } catch (IOException e) {
            e.printStackTrace();
        }

        fxMain.rootLogger.info("Регистрация");
    }

    public void setErrorPassword() {
        errorPassword.setText("Неправильный логин и/или пароль");
    }

    /**
     * Инициализирует класс-контроллер. Этот метод вызывается автоматически
     * после того, как fxml-файл будет загружен.
     */
    @FXML
    private void initialize() {
        fxMain.rootLogger.info("Авторизация");
    }

    public void setMainApp(FXMain fxMain) {
        this.fxMain = fxMain;
        tcpConnection = fxMain.getTcpConnection();
    }
}
