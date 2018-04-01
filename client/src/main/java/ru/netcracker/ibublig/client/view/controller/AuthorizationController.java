package ru.netcracker.ibublig.client.view.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import ru.netcracker.ibublig.client.FXMain;

import java.io.IOException;

public class AuthorizationController {

    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorPassword;

    private AnchorPane registrationLayout;

    private FXMain fxMain;

    public AuthorizationController(){

    }

    @FXML
    private void authorization(){
        System.out.println("Авторизация");
        for (int i = 0; i < fxMain.getUserData().size(); i++) {
            if (fxMain.getUserData().get(i).getLogin().equals(loginField.getText())){
                if (fxMain.getUserData().get(i).getPassword().equals(passwordField.getText())){
                    try {
                        System.out.println("Успешная авторизация!!!");
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(fxMain.getClass().getResource("view/view/CatalogLayout.fxml"));
                        Scene scene = new Scene((AnchorPane)loader.load());
                        fxMain.getPrimaryStage().setScene(scene);
                        fxMain.getPrimaryStage().show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            } else {
                errorPassword.setText("Неправильный логин или пароль!!!");
                System.out.println("Неправильный логин или пароль!!!");
            }
        }
    }
    @FXML
    private void registration(){

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(fxMain.getClass().getResource("view/view/RegistrationLayout.fxml"));
            registrationLayout = (AnchorPane) loader.load();

            RegistrationController controller = loader.getController();
            controller.setMainApp(fxMain);

            Scene scene = new Scene(registrationLayout);
            fxMain.getPrimaryStage().setScene(scene);
            fxMain.getPrimaryStage().show();

        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("Регистрация");
    }
    /**
     * Инициализирует класс-контроллер. Этот метод вызывается автоматически
     * после того, как fxml-файл будет загружен.
     */
    @FXML
    private void initialize() {
    }

    public void setMainApp(FXMain fxMain) {
        this.fxMain = fxMain;
    }
}
