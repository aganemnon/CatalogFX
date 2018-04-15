package ru.netcracker.ibublig.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.netcracker.ibublig.client.controller.ARController;
import ru.netcracker.ibublig.client.view.controller.CatalogController;
import ru.netcracker.ibublig.model.Category;
import ru.netcracker.ibublig.model.User;
import ru.netcracker.ibublig.client.view.controller.AdminEditController;
import ru.netcracker.ibublig.client.view.controller.AuthorizationController;
import ru.netcracker.ibublig.client.view.model.Item;
import ru.netcracker.ibublig.network.TCPConnection;
import ru.netcracker.ibublig.network.TCPConnectionListener;

import java.io.*;
import java.util.ArrayList;

public class FXMain extends Application implements TCPConnectionListener {

    private Stage primaryStage;

    private static final String IP_ADDRESS = "127.0.0.1";
    private static final int PORT = 8189;

    private TCPConnection tcpConnection;
    private ARController arController;
    private ArrayList<Category> category;
    private CatalogController catalogController;
    private AuthorizationController authorizationController;

    public ArrayList<Category> getCategory() {
        return category;
    }

    public FXMain() {
        //userData.add(new User("Илья","Сиротин", true, "admin", "1234"));
        //userData.add(new User("Не Илья","Сиротин", false, "noadmin","3214"));
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("CatalogFX");

        try {
            tcpConnection = new TCPConnection(this, IP_ADDRESS, PORT);
            initAuthorization();
        } catch (IOException e) {
            System.out.println(e);
        }
    }


    public void initAuthorization() {
        try {
            // Загружаем стартовый макет
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(FXMain.class.getResource("view/view/AuthorizationLayout.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            authorizationController = loader.getController();
            authorizationController.setMainApp(this);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showItemEditDialog(Item item) {
        try {
            // Загружаем fxml-файл и создаём новую сцену
            // для всплывающего диалогового окна.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(FXMain.class.getResource("view/view/AdminEditItemLayout.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Создаём диалоговое окно Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Item");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Передаём адресата в контроллер.
            AdminEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setItem(item);

            // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public void showCatalog(User user){
        try {
            System.out.println("Успешная авторизация!!!");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(FXMain.class.getResource("view/view/CatalogLayout.fxml"));
            Scene scene = new Scene((AnchorPane) loader.load());
            catalogController = loader.getController();

            catalogController.setMainApp(this);
            catalogController.setUser(user);

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public TCPConnection getTcpConnection() {
        return tcpConnection;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    @Override
    public void onConnectionReady(TCPConnection tcpConnection) {
        System.out.println("Connection ready...");
    }

    @Override
    public void onReceiveByte(TCPConnection tcpConnection, Object object) {
        if (object instanceof User){
            User user = (User) object;
            if(user.getLogin().equals("")){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        authorizationController.setErrorPassword();
                    }
                });
            }else {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        showCatalog(user);
                    }
                });
            }
        } else if(object instanceof ArrayList){
            category = (ArrayList<Category>) object;
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    catalogController.setCategories(category);
                }
            });

        }
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
