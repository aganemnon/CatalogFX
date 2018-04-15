import ru.netcracker.ibublig.model.Category;
import ru.netcracker.ibublig.network.TCPConnection;
import ru.netcracker.ibublig.network.TCPConnectionListener;
import ru.netcracker.ibublig.model.User;
import ru.netcracker.ibublig.server.contrller.ServerController;

import java.io.*;
import java.net.ServerSocket;
import java.util.ArrayList;

public class MainServer implements TCPConnectionListener {
    private final ArrayList<TCPConnection> connections = new ArrayList<>();
    private ServerController serverController = new ServerController();

    public static void main(String[] args) {
        new MainServer();
    }

    private MainServer() {
        System.out.println("Server running...");
        try (ServerSocket serverSocket = new ServerSocket(8189)) {
            while (true) {
                try {
                    new TCPConnection(this, serverSocket.accept());
                } catch (IOException e) {
                    System.out.println("TCPConnection exception: " + e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onConnectionReady(TCPConnection tcpConnection) {
        connections.add(tcpConnection);
        System.out.println("Connect " + tcpConnection);
    }

    @Override
    public void onReceiveByte(TCPConnection tcpConnection, Object object) {
        if (object instanceof User) {
            User user = (User) object;
            if (user.isReg()) {
                //Зарегистрировать
                serverController.registrationUser(user);
            } else {
                //авторизовать
                for (int i = 0; i < connections.size(); i++) {
                    if (connections.get(i).equals(tcpConnection)) {
                        int id = i;
                        connections.get(id).sendObject(serverController.authorizationUser(user));
                        if(!serverController.authorizationUser(user).getLogin().equals("")){
                            connections.get(id).sendObject(serverController.getCategories());
                        }
                    }
                }
            }
        } else if (object instanceof ArrayList) {
            ArrayList<Category> category = new ArrayList();
            category = (ArrayList<Category>) object;
            serverController.setCategories(category);
        }
    }

    @Override
    public void onDisconnect(TCPConnection tcpConnection) {
        connections.remove(tcpConnection);
        System.out.println("Disconnect: " + tcpConnection);
    }

    @Override
    public void onException(TCPConnection tcpConnection, Exception e) {
        System.out.println("TCPConnection exception: " + e);
    }
}
