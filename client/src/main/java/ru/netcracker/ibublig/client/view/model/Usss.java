package ru.netcracker.ibublig.client.view.model;

import java.io.Serializable;

public class Usss implements Serializable {
    int i = 15;
    String message = "Message";

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
