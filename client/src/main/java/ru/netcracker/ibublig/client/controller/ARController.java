package ru.netcracker.ibublig.client.controller;

import ru.netcracker.ibublig.model.User;
import ru.netcracker.ibublig.network.TCPConnection;

public class ARController {
    private User user;
    private TCPConnection connection;

    public ARController(User user, TCPConnection connection) {
        this.user = user;
        this.connection = connection;
    }

    public void sendUserAR() {
        connection.sendObject(user);
    }
}
