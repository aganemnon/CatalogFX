package ru.netcracker.ibublig.client.view.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import ru.netcracker.ibublig.client.FXMain;
import ru.netcracker.ibublig.client.util.MD5;
import ru.netcracker.ibublig.client.view.model.User;
import ru.netcracker.ibublig.network.TCPConnection;
import ru.netcracker.ibublig.network.TCPConnectionListener;

import java.io.IOException;
import java.io.InputStream;

public class AuthorizationController implements TCPConnectionListener {

    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorPassword;

    private AnchorPane registrationLayout;

    private FXMain fxMain;
    private TCPConnection tcpConnection;

    private ObservableList<User> userData = FXCollections.observableArrayList();


    public AuthorizationController() {

    }

    @FXML
    private void authorization() {
        tcpConnection.sendAuthorization(loginField.getText(), MD5.md5Custom(loginField.getText(), passwordField.getText()));
        tcpConnection.sendTestMessage();

        userData.add(new User("s", "d", true, "admin", "admin"));
        for (int i = 0; i < userData.size(); i++) {
            if (userData.get(i).getLogin().equals(loginField.getText())) {
                if (userData.get(i).getPassword().equals(passwordField.getText())) {
                    try {
                        System.out.println("Успешная авторизация!!!");
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(fxMain.getClass().getResource("view/view/CatalogLayout.fxml"));
                        Scene scene = new Scene((AnchorPane) loader.load());
                        CatalogController controller = loader.getController();
                        controller.setMainApp(fxMain);
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
    private void registration() {

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
        System.out.println("Авторизация");
    }

    public void setMainApp(FXMain fxMain) {
        this.fxMain = fxMain;
        tcpConnection = fxMain.getTcpConnection();
    }

    @Override
    public void onConnectionReady(TCPConnection tcpConnection) {

    }

    @Override
    public void onReceiveByte(TCPConnection tcpConnection, InputStream value) {
        System.out.println("TEST");
        try {
            System.out.println("Успешная авторизация!!!");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(fxMain.getClass().getResource("view/view/CatalogLayout.fxml"));
            Scene scene = new Scene((AnchorPane) loader.load());
            fxMain.getPrimaryStage().setScene(scene);
            fxMain.getPrimaryStage().show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisconnect(TCPConnection tcpConnection) {

    }

    @Override
    public void onException(TCPConnection tcpConnection, Exception e) {

    }
}
