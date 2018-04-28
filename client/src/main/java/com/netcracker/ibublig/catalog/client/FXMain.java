package com.netcracker.ibublig.catalog.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import com.netcracker.ibublig.catalog.client.controller.AdminEditCategoryController;
import com.netcracker.ibublig.catalog.client.controller.AdminEditController;
import com.netcracker.ibublig.catalog.client.controller.AuthorizationController;
import com.netcracker.ibublig.catalog.client.controller.CatalogController;
import com.netcracker.ibublig.catalog.client.model.Item;
import com.netcracker.ibublig.catalog.model.Category;
import com.netcracker.ibublig.catalog.model.User;
import com.netcracker.ibublig.catalog.network.TCPConnection;
import com.netcracker.ibublig.catalog.network.TCPConnectionListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;

public class FXMain extends Application implements TCPConnectionListener {

    public static final Logger rootLogger = LogManager.getRootLogger();
    static final Logger userLogger = LogManager.getLogger(FXMain.class);

    private Stage primaryStage;

    private static final String IP_ADDRESS = "127.0.0.1";
    private static final int PORT = 8189;

    private TCPConnection tcpConnection;
    private ArrayList<Category> category;
    private CatalogController catalogController;
    private AuthorizationController authorizationController;
    private User user;

    public ArrayList<Category> getCategory() {
        return category;
    }

    public FXMain() {
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("CatalogFX");
        this.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.exit();
                System.exit(0);
            }
        });

        try {
            tcpConnection = new TCPConnection(this, IP_ADDRESS, PORT);
            initAuthorization();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public User getUser() {
        return user;
    }

    public void initAuthorization() {
        try {
            // Загружаем стартовый макет
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(FXMain.class.getResource("view/AuthorizationLayout.fxml"));
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
            loader.setLocation(FXMain.class.getResource("view/AdminEditItemLayout.fxml"));
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

    public CatalogController getCatalogController() {
        return catalogController;
    }


    public boolean showCategoryEditDialog(com.netcracker.ibublig.catalog.client.model.Category category) {
        try {
            // Загружаем fxml-файл и создаём новую сцену
            // для всплывающего диалогового окна.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(FXMain.class.getResource("view/AdminEditCategoryLayout.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Создаём диалоговое окно Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit category");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Передаём адресата в контроллер.
            AdminEditCategoryController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setCategory(category);

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
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(FXMain.class.getResource("view/CatalogLayout.fxml"));
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
        rootLogger.info("Connection ready...");
    }

    @Override
    public void onReceiveByte(TCPConnection tcpConnection, Object object) {
        if (object instanceof User){
            user = (User) object;
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
                        rootLogger.info("успешная авторизация");
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
        rootLogger.info("Connection disconnect...");
    }

    @Override
    public void onException(TCPConnection tcpConnection, Exception e) {
        System.out.println(e);
    }
}
