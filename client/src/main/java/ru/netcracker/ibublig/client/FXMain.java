package ru.netcracker.ibublig.client;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ru.netcracker.ibublig.client.view.controller.AuthorizationController;
import ru.netcracker.ibublig.client.view.controller.RegistrationController;
import ru.netcracker.ibublig.client.view.model.CatalogListWrapper;
import ru.netcracker.ibublig.client.view.model.Category;
import ru.netcracker.ibublig.client.view.model.Item;
import ru.netcracker.ibublig.client.view.model.User;
import ru.netcracker.ibublig.client.util.MD5;
import ru.netcracker.ibublig.network.TCPConnection;
import ru.netcracker.ibublig.network.TCPConnectionListener;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;

public class FXMain extends Application implements TCPConnectionListener {

    private Stage primaryStage;
    private ObservableList<User> userData = FXCollections.observableArrayList();
    private ObservableList<Category> categories = FXCollections.observableArrayList();
    private RegistrationController registrationController;
    private static final String IP_ADDR = "127.0.0.1";
    private static final int PORT = 8189;
    private File file = new File("C:\\Data", "test.xml");

    private InputStream in;

    private TCPConnection tcpConnection;

    public FXMain(){
        userData.add(new User("Илья","Сиротин", true, "admin", "1234"));
        userData.add(new User("Не Илья","Сиротин", false, "noadmin","3214"));
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("CatalogFX");

        in = new FileInputStream(file);

        initAuthorization();

        try{
           tcpConnection = new TCPConnection(this, IP_ADDR, PORT);
        } catch (IOException e) {
            System.out.println("fail" + e);
        }
        in = new FileInputStream(file);


        //sendXML();
        //tcpConnection.sendString(MD5.md5Custom(userData.get(0).getLogin(), userData.get(0).getPassword()));
        tcpConnection.sendString("TEST_SOUT Привет всем");
    }

    public void loadPersonDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(CatalogListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Чтение XML из файла и демаршализация.
            CatalogListWrapper wrapper = (CatalogListWrapper) um.unmarshal(file);

//            personData.clear();
//            personData.addAll(wrapper.getPersons());

            // Сохраняем путь к файлу в реестре.
//            setPersonFilePath(file);

        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
            alert.setContentText("Could not load data from file:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    public void savePersonDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(CatalogListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Обёртываем наши данные об адресатах.
            CatalogListWrapper wrapper = new CatalogListWrapper();
            wrapper.setCategories(categories);

            // Маршаллируем и сохраняем XML в файл.
            m.marshal(wrapper, file);

            // Сохраняем путь к файлу в реестре.
//            setPersonFilePath(file);
        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.setContentText("Could not save data to file:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    public void sendXML(){

        tcpConnection.getTestOutPut();
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

    public void setUserData(ObservableList<User> userData) {
        this.userData = userData;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void onConnectionReady(TCPConnection tcpConnection) {
        printMessage("Connection ready...");
    }

    @Override
    public void onReceiveString(TCPConnection tcpConnection, String value) {
        printMessage(value);
    }
    @Override
    public void onDisconnect(TCPConnection tcpConnection) {
        printMessage("Connection close");
    }
    private void printMessage(String message){
        System.out.println(message);
    }

    @Override
    public void onException(TCPConnection tcpConnection, Exception e) {

    }
}
