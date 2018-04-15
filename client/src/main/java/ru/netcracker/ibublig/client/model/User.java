package ru.netcracker.ibublig.client.model;

import java.io.Serializable;

public class User implements Serializable {
    private final String firstName;
    private final String lastName;
    private final String login;
    private final String password;
    private final boolean admin;
    private final boolean reg;

    public User(String firstName, String lastName, String login, String password, boolean admin, boolean reg) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.admin = admin;
        this.reg = reg;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;

        firstName = "Auth";
        lastName = "Auth";
        admin = false;
        reg = false;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public boolean isReg() {
        return reg;
    }
}
