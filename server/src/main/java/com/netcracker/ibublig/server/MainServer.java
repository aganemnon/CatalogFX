package com.netcracker.ibublig.server;

import com.netcracker.ibublig.catalog.model.Category;
import com.netcracker.ibublig.catalog.model.User;
import com.netcracker.ibublig.catalog.network.TCPConnection;
import com.netcracker.ibublig.catalog.network.TCPConnectionListener;
import com.netcracker.ibublig.server.controller.ServerController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class MainServer implements TCPConnectionListener {
    public static final Logger rootLogger = LogManager.getRootLogger();

    private final ArrayList<TCPConnection> connections = new ArrayList<>();
    private ServerController serverController = new ServerController();

    public static void main(String[] args) {
        new MainServer();
    }

    private MainServer() {
        rootLogger.info("Server running...");
        try (ServerSocket serverSocket = new ServerSocket(8189)) {
            while (true) {
                try {
                    new TCPConnection(this, serverSocket.accept());
                } catch (IOException e) {
                    rootLogger.error("TCPConnection exception: " + e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onConnectionReady(TCPConnection tcpConnection) {
        connections.add(tcpConnection);
        rootLogger.info("User connect: " + tcpConnection);
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
        rootLogger.info("User disconnect: " + tcpConnection);
    }

    @Override
    public void onException(TCPConnection tcpConnection, Exception e) {
        rootLogger.info("TCPConnection exception: " + e);
    }
}
