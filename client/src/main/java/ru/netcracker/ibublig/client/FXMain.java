package ru.netcracker.ibublig.client;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ru.netcracker.ibublig.client.view.controller.AuthorizationController;
import ru.netcracker.ibublig.client.view.model.User;
import ru.netcracker.ibublig.network.TCPConnection;
import ru.netcracker.ibublig.network.TCPConnectionListener;

import java.io.*;

public class FXMain extends Application implements TCPConnectionListener {

    private Stage primaryStage;

    private ObservableList<User> userData = FXCollections.observableArrayList();

    private static final String IP_ADDR = "127.0.0.1";
    private static final int PORT = 8189;

    private TCPConnection tcpConnection;



    public FXMain(){
        userData.add(new User("Илья","Сиротин", true, "admin", "1234"));
        userData.add(new User("Не Илья","Сиротин", false, "noadmin","3214"));
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("CatalogFX");

        initAuthorization();
        try {
            tcpConnection = new TCPConnection(this, IP_ADDR, PORT);
        } catch (IOException e) {
            System.out.println(e);
        }
        //tcpConnection.sendTestMessage();
        tcpConnection.sendFile(new File("C:\\Data","Catalog.xml"));
    }



    public void initAuthorization() {
        try {
            // Загружаем стартовый макет
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(FXMain.class.getResource("view/view/AuthorizationLayout.fxml"));

            AnchorPane root = (AnchorPane) loader.load();


            AuthorizationController controller = loader.getController();
            controller.setMainApp(this);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage(){
        return primaryStage;
    }

    public ObservableList<User> getUserData() {
        return userData;
    }

    @Override
    public void onConnectionReady(TCPConnection tcpConnection) {
        System.out.println("Connection ready...");
    }

    @Override
    public void onReceiveByte(TCPConnection tcpConnection, InputStream value) {
        System.out.println("!!!");
            //System.out.println("TEST");
            System.out.println(value);
    }

    @Override
    public void onDisconnect(TCPConnection tcpConnection) {
        System.out.println("Connection disconnect...");
    }

    @Override
    public void onException(TCPConnection tcpConnection, Exception e) {
        System.out.println(e);
    }
}
